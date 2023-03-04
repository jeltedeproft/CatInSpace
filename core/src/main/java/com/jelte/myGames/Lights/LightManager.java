package com.jelte.myGames.Lights;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.aliasifkhan.hackLights.HackLightEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Matrix4;
import com.jelte.myGames.Utility.AssetManagerUtility;
import com.jelte.myGames.Utility.Position;
import com.jelte.myGames.Utility.Variables;
import com.jelte.myGames.shared.serverClientMessages.ThingsToDraw;
import com.jelte.myGames.shared.serverClientMessages.ThingsToDraw.WizardState;

public class LightManager {
	private HackLightEngine lightEngine;
	private Map<LightsEnum, AtlasRegion> lightTextures;
	private Map<UUID, MyLight> lightsPerSprite;
	private LightPool lightPool;
	private final ConcurrentLinkedQueue<UUID> usedIds;
	private boolean mapLightsInitialized = false;

	public LightManager() {
		usedIds = new ConcurrentLinkedQueue<>();
		lightsPerSprite = new HashMap<>();
		lightTextures = new EnumMap<>(LightsEnum.class);
		lightPool = new LightPool();
		lightEngine = new HackLightEngine();
		lightEngine.setAmbientLightColor(Variables.AMBIENT_RED, Variables.AMBIENT_GREEN, Variables.AMBIENT_BLUE, Variables.AMBIENT_ALPHA);
		initLightTextures();
	}

	private void initLightTextures() {
		for (LightsEnum type : LightsEnum.values()) {
			lightTextures.put(type, AssetManagerUtility.getAtlasRegion(type.getLightName()));
		}
	}

	private void createLight(LightsEnum type, UUID id, Position pos, Color color) {
		MyLight light = lightPool.obtain();
		light.init(type, lightTextures.get(type), pos, color, 0.5f);
		lightsPerSprite.put(id, light);
		lightEngine.addLight(light.getLight());
	}

	private void createLight(LightsEnum type, UUID id, Position pos, Color color, float scale) {
		MyLight light = lightPool.obtain();
		light.init(type, lightTextures.get(type), pos, color, scale);
		lightsPerSprite.put(id, light);
		lightEngine.addLight(light.getLight());
	}

	private void removeLight(UUID id) {
		MyLight light = lightsPerSprite.get(id);
		lightEngine.removeLight(light.getLight());
		lightPool.free(light);
		lightsPerSprite.remove(id);
	}

	public void update(ThingsToDraw things) {
		for (WizardState wizard : things.wizardStates) {
			usedIds.add(wizard.id);
			if (lightExists(wizard.id)) {
				lightsPerSprite.get(wizard.id).setPositionShifted(wizard.x, wizard.y, wizard.direction);
				lightsPerSprite.get(wizard.id).setDirection(wizard.direction);
			} else {
				createLight(LightsEnum.CONE_LIGHT, wizard.id, new Position(wizard.x, wizard.y), Color.WHITE, 2.75f);// TODO use color player here?? or make sprites colored
			}
		}
		if (!mapLightsInitialized) {
			mapLightsInitialized = true;
			if (things.lights != null) {
				for (Position light : things.lights) {
					createLight(LightsEnum.STATIC_LIGHT, UUID.randomUUID(), new Position(light.getX(), light.getY()), Color.WHITE);
				}
			}

		}
		cleanUpOldLights();
	}

	private boolean lightExists(UUID id) {
		return lightsPerSprite.containsKey(id);
	}

	public void cleanUpOldLights() {
		ArrayList<UUID> idsToCleanUp = new ArrayList<>();
		for (Entry<UUID, MyLight> id : lightsPerSprite.entrySet()) {
			if (!usedIds.contains(id.getKey()) && !id.getValue().getType().equals(LightsEnum.STATIC_LIGHT)) {
				idsToCleanUp.add(id.getKey());
			}
		}

		for (UUID id : idsToCleanUp) {
			removeLight(id);
		}
		usedIds.clear();
	}

	public void render(Matrix4 combined) {
		lightEngine.draw(combined);
	}

	public void resize(int width, int height) {
		lightEngine.update(width, height);
	}

}
