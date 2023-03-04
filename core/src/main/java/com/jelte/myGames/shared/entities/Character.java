package com.jelte.myGames.shared.entities;

import java.util.ArrayList;
import java.util.UUID;

import com.badlogic.gdx.graphics.Color;
import com.jelte.myGames.Utility.MyPolygon;
import com.jelte.myGames.Utility.Position;
import com.jelte.myGames.shared.entities.ActionManager.Action;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Character {
	@EqualsAndHashCode.Exclude
	private float currentHp;
	@EqualsAndHashCode.Exclude
	private float currentMovementSpeed;
	@EqualsAndHashCode.Exclude
	private CharacterData data;
	@Setter(AccessLevel.NONE)
	@EqualsAndHashCode.Exclude
	private Position currentPosition;
	private UUID id;
	@EqualsAndHashCode.Exclude
	private boolean moving;
	@EqualsAndHashCode.Exclude
	private boolean dead;
	@EqualsAndHashCode.Exclude
	private boolean shielded;
	@EqualsAndHashCode.Exclude
	private boolean movable;
	@EqualsAndHashCode.Exclude
	private ActionManager actionManager;
	@EqualsAndHashCode.Exclude
	private Direction currentDirection;
	@EqualsAndHashCode.Exclude
	private MyPolygon wizardArea;
	@EqualsAndHashCode.Exclude
	private Color color;
	@EqualsAndHashCode.Exclude
	private TeamsEnum team;

	public Character(CharacterData data, UUID id) {
		this.id = id;
		this.data = data;
		actionManager = new ActionManager(this);
		currentDirection = Direction.right;
		moving = false;
		movable = true;
		currentMovementSpeed = data.getMovementSpeed();
		currentHp = data.getMaxHP();
		currentPosition = new Position(0, 0);
		wizardArea = new MyPolygon(new float[] { 0, 0, getBoundingBoxWidth(), 0, getBoundingBoxWidth(), getBoundingBoxHeight(), 0, getBoundingBoxHeight() });
	}

	public Character(CharacterData data, UUID randomUUID, ArrayList<Integer> spells) {
		this(data, randomUUID);
	}

	public void update(float delta) {
		actionManager.update(delta);
	}

	public boolean damage(float amount) {
		if (currentHp <= amount) {
			currentHp = 0;
			dead = true;
			actionManager.setCurrentActionForcefully(Action.die);
			return true;
		} else {
			currentHp -= amount;
			return false;

		}
	}

	public void heal(float amount) {
		if (data.getMaxHP() <= (currentHp + amount)) {
			currentHp = data.getMaxHP();
		} else {
			currentHp += amount;
		}
	}

	public int getBoundingBoxWidth() {
		return data.getBoundingBoxWidth();
	}

	public int getBoundingBoxHeight() {
		return data.getBoundingBoxHeight();
	}

	public String getSpriteName() {
		return data.getEntitySpriteName();
	}

	public String getName() {
		return data.getName();
	}

	public void setCurrentPositionX(float x) {
		if (!dead) {
			currentPosition.setX(x);
			wizardArea.setPosition(x, wizardArea.getY());
		}
	}

	public void setCurrentPositionY(float y) {
		if (!dead) {
			currentPosition.setY(y);
			wizardArea.setPosition(wizardArea.getX(), y);
		}
	}

	public void setCurrentPosition(Position pos) {
		if (!dead) {
			currentPosition = pos;
			wizardArea.setPosition(pos.getX(), pos.getY());
		}
	}

	public void setCurrentAction(Action action) {
		actionManager.setCurrentAction(action);
	}

	public void setCurrentActionForcefully(Action action) {
		actionManager.setCurrentActionForcefully(action);
	}

	public Action getCurrentAction() {
		return actionManager.getCurrentAction();
	}

	public void setcurrentmovementspeed(float speed) {
		currentMovementSpeed = speed;
	}

}
