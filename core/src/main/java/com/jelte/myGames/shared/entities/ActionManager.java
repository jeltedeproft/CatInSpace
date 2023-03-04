package com.jelte.myGames.shared.entities;

import java.util.EnumMap;

public class ActionManager {
	private Action currentAction;
	private float timeInCurrentAction = 0f;
	private Character character;
	private EnumMap<Action, Float> actionTimes = new EnumMap<>(Action.class);

	public enum Action {
		die, idle, move, hurt, appear;
	}

	public ActionManager(Character character) {
		this.character = character;
		CharacterData data = character.getData();
		currentAction = Action.appear;
		actionTimes.put(Action.die, data.getDieAnimationFullTime());
		actionTimes.put(Action.hurt, data.getHurtAnimationFullTime());
		actionTimes.put(Action.appear, data.getAppearAnimationFullTime());
		actionTimes.put(Action.idle, 0f);
		actionTimes.put(Action.move, 0f);
	}

	public void update(float delta) {
		timeInCurrentAction += delta;
		Float currentActionFullTime = actionTimes.get(currentAction);
		if ((currentActionFullTime != 0) && (timeInCurrentAction >= currentActionFullTime)) {
			if (currentAction != Action.die) {
				currentAction = Action.idle;
			}
			timeInCurrentAction = 0f;
		}
	}

	public void setCurrentAction(Action action) {
		Float currentActionFullTime = actionTimes.get(currentAction);
		if (timeInCurrentAction >= currentActionFullTime) {
			currentAction = action;
			timeInCurrentAction = 0f;
		}
	}

	public Action getCurrentAction() {
		return currentAction;
	}

	public void setCurrentActionForcefully(Action action) {
		currentAction = action;
		timeInCurrentAction = 0f;
	}

}
