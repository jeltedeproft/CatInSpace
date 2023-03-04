package com.jelte.myGames.Animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jelte.myGames.Utility.AssetManagerUtility;
import com.jelte.myGames.Utility.Variables;
import com.jelte.myGames.shared.entities.CharacterFileReader;

import lombok.ToString;

@ToString
public class AnimationManager {
	private static final String TAG = AnimationManager.class.getSimpleName();

	private final Map<UUID, String> previousAnimations;
	private final Map<UUID, Float> frameTimesPerObject;
	private final Map<String, Animation<Sprite>> loadedAnimations;
	private final Map<String, CharacterAnimationData> localCharacterAnimationData;
	private final ConcurrentLinkedQueue<UUID> usedIds;

	public AnimationManager() {
		previousAnimations = new HashMap<>();
		frameTimesPerObject = new HashMap<>();
		loadedAnimations = new HashMap<>();
		localCharacterAnimationData = new HashMap<>();
		usedIds = new ConcurrentLinkedQueue<>();
		initializeWizards();
	}

	public void initializeWizards() {
		for (final String wizardName : CharacterFileReader.getAllWizardNames()) {
			localCharacterAnimationData.computeIfAbsent(wizardName, CharacterAnimationData::new);
		}
	}

	public Sprite getTextureForWizard(WizardState wizardState) {
		usedIds.add(wizardState.id);
		return stringToTexture(localCharacterAnimationData.get(wizardState.name).getAnimationName(wizardState.action, wizardState.direction), wizardState.id);
	}

	public Sprite stringToTexture(String animationName, UUID id) {
		usedIds.add(id);
		final String previous = previousAnimations.get(id);
		// new animation or changed animation, reset frameTime
		if (previous == null || !previous.equals(animationName)) {
			previousAnimations.put(id, animationName);
			frameTimesPerObject.put(id, 0f);
		}

		// is animation already loaded?
		Animation<Sprite> animation = loadedAnimations.get(animationName);
		if (animation == null) {
			animation = AssetManagerUtility.getAnimation(animationName, getAnimationDurationFromString(animationName), getAnimationTypeFromString(animationName));
			if (animation == null) {
				Gdx.app.debug(TAG, "cannot find animation of this type : " + animationName);
				return null;
			}
			loadedAnimations.put(animationName, animation);
		}

		final float frameTime = frameTimesPerObject.get(id);

		return animation.getKeyFrame(frameTime);
	}

	private float getAnimationDurationFromString(String animationName) {

		for (final String name : localCharacterAnimationData.keySet()) {
			if (animationName.contains(name)) {
				return getAnimationSpeedForWizard(name, animationName);
			}
		}

		return Variables.DEFAULT_ANIMATION_SPEED;
	}

	private float getAnimationSpeedForWizard(String name, String animationName) {
		if (animationName.contains("idle")) {
			final int id = CharacterFileReader.getIdByName(name);
			return CharacterFileReader.getUnitData().get(id).getIdleAnimationSpeed();
		}
		if (animationName.contains("hurt")) {
			final int id = CharacterFileReader.getIdByName(name);
			return CharacterFileReader.getUnitData().get(id).getHurtAnimationSpeed();
		}
		if (animationName.contains("die")) {
			final int id = CharacterFileReader.getIdByName(name);
			return CharacterFileReader.getUnitData().get(id).getDieAnimationSpeed();
		}
		if (animationName.contains("move")) {
			final int id = CharacterFileReader.getIdByName(name);
			return CharacterFileReader.getUnitData().get(id).getMoveAnimationSpeed();
		}
		return Variables.DEFAULT_ANIMATION_SPEED;
	}

	private Animation.PlayMode getAnimationTypeFromString(String animationName) {
		if (animationName.contains("die")) {
			return Animation.PlayMode.NORMAL;
		}
		if (animationName.contains("idle")) {
			return Animation.PlayMode.LOOP;
		}
		if (animationName.contains("move")) {
		}

		return Animation.PlayMode.LOOP;
	}

	public void cleanUpOldAnimations() {
		final ArrayList<UUID> idsToCleanUp = new ArrayList<>();
		for (final UUID id : previousAnimations.keySet()) {
			if (!usedIds.contains(id)) {
				idsToCleanUp.add(id);
			}
		}
		for (final UUID id : frameTimesPerObject.keySet()) {
			if (!usedIds.contains(id)) {
				idsToCleanUp.add(id);
			}
		}

		for (final UUID id : idsToCleanUp) {
			previousAnimations.remove(id);
			frameTimesPerObject.remove(id);
		}
		usedIds.clear();
	}

	public void update(final float delta) {
		frameTimesPerObject.replaceAll((k, v) -> v = v + delta);
		cleanUpOldAnimations();
	}

}
