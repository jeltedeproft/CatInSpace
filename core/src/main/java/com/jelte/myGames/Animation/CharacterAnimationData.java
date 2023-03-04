package com.jelte.myGames.Animation;

import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.jelte.myGames.Utility.AssetManagerUtility;
import com.jelte.myGames.shared.entities.ActionManager.Action;
import com.jelte.myGames.shared.entities.Direction;

import lombok.ToString;

@ToString
public class CharacterAnimationData {
	private final String wizardName;
	private final Map<Action, Array<Direction>> animations;

	// INIT
	public CharacterAnimationData(String wizardName) {
		this.wizardName = wizardName;
		animations = new EnumMap<>(Action.class);
		for (final Action action : Action.values()) {
			animations.put(action, new Array<>());
		}

		initAvailableActionsDirections(wizardName);
	}

	private void initAvailableActionsDirections(String wizardName) {
		final Array<AtlasRegion> regions = AssetManagerUtility.getAllRegionsWhichContainName(wizardName);
		for (final AtlasRegion region : regions) {
			final String[] parts = region.name.split("-");
			if ((parts.length >= 3) && (parts[0].equalsIgnoreCase(wizardName))) {
				final Action action = Action.valueOf(parts[1]);
				final Direction direction = Direction.valueOf(parts[2]);
				animations.get(action).add(direction);
			}
		}
	}

	// GET
	public String getAnimationName(Action action, Direction direction) {
		final String animation = getAnimationIfPresent(action, direction);
		if (animation != null) {
			return animation;
		}
		return getStandardAnimationAsString();
	}

	public String getAnimationIfPresent(Action action, Direction direction) {
		if (has8DirectionalAnimation(action, direction)) {
			return wizardName + "-" + action.name() + "-" + direction.name();
		}
		if (has4DirectionalAnimation(action, direction)) {
			return wizardName + "-" + action.name() + "-" + get4Dimension(direction).name();
		}
		if (has2DirectionalAnimation(action, direction)) {
			return wizardName + "-" + action.name() + "-" + get2Dimension(direction).name();
		}
		return null;
	}

	public boolean has8DirectionalAnimation(Action action, Direction direction) {
		if (hasAnimation(action)) {
			return animations.get(action).contains(direction, false);
		}
		return false;
	}

	public boolean has4DirectionalAnimation(Action action, Direction direction) {
		if (hasAnimation(action)) {
			return animations.get(action).contains(get4Dimension(direction), false);
		}
		return false;
	}

	public boolean has2DirectionalAnimation(Action action, Direction direction) {
		if (hasAnimation(action)) {
			return animations.get(action).contains(get2Dimension(direction), false);
		}
		return false;
	}

	private Direction get4Dimension(Direction direction) {
		switch (direction) {
		case bottom, bottomleft:
			return Direction.bottom;
		case bottomright, right:
			return Direction.right;
		case left, topleft:
			return Direction.left;
		case top, topright:
			return Direction.top;
		default:
			return null;
		}
	}

	private Direction get2Dimension(Direction direction) {
		switch (direction) {
		case bottom, topleft, bottomleft, left:
			return Direction.left;
		case topright, bottomright, right, top:
			return Direction.right;
		default:
			return null;
		}
	}

	public boolean hasAnimation(Action action) {
		return animations.containsKey(action);
	}

	public String getStandardAnimationAsString() {
		return wizardName + "-" + Action.idle.name() + "-" + Direction.right.name();
	}

}
