package com.jelte.myGames.headless.logic.battle;

import java.util.concurrent.ConcurrentSkipListSet;

import com.badlogic.gdx.utils.Array;
import com.jelte.myGames.shared.entities.ActionManager.Action;
import com.jelte.myGames.Utility.MyPolygon;
import com.jelte.myGames.Utility.Position;
import com.jelte.myGames.Utility.Variables;
import com.jelte.myGames.shared.entities.Character;
import com.jelte.myGames.shared.entities.Direction;

public class MovementManager {

	private final int width;
	private final int height;
	private final ConcurrentSkipListSet<MyPolygon> blocks;

	public MovementManager(int width, int height, ConcurrentSkipListSet<MyPolygon> blocks) {
		this.width = width;
		this.height = height;
		this.blocks = blocks;
	}

	public void dodgeRollWizard(Character wizard, Direction direction) {
		final Position newPos = wizard.getCurrentPosition().copy();
		switch (direction) {
		case left:
			executeMove(wizard, newPos.moveX(-Variables.DASH_SPEED));
			break;
		case right:
			executeMove(wizard, newPos.moveX(Variables.DASH_SPEED));
			break;
		case top:
			executeMove(wizard, newPos.moveY(Variables.DASH_SPEED));
			break;
		case bottom:
			executeMove(wizard, newPos.moveY(-Variables.DASH_SPEED));
			break;
		case topleft:
			newPos.moveX(-(Variables.DASH_SPEED / Variables.DIAGONAL_FACTOR));
			newPos.moveY(Variables.DASH_SPEED / Variables.DIAGONAL_FACTOR);
			executeMove(wizard, newPos);
			break;
		case topright:
			newPos.moveX(Variables.DASH_SPEED / Variables.DIAGONAL_FACTOR);
			newPos.moveY(Variables.DASH_SPEED / Variables.DIAGONAL_FACTOR);
			executeMove(wizard, newPos);
			break;
		case bottomright:
			newPos.moveX(Variables.DASH_SPEED / Variables.DIAGONAL_FACTOR);
			newPos.moveY(-(Variables.DASH_SPEED / Variables.DIAGONAL_FACTOR));
			executeMove(wizard, newPos);
			break;
		case bottomleft:
			newPos.moveX(-(Variables.DASH_SPEED / Variables.DIAGONAL_FACTOR));
			newPos.moveY(-(Variables.DASH_SPEED / Variables.DIAGONAL_FACTOR));
			executeMove(wizard, newPos);
			break;
		default:
			break;
		}

	}

	public void moveWizard(Character wizard) {
		Direction direction = wizard.getCurrentDirection();
		float speed = wizard.getCurrentMovementSpeed();
		final Position newPos = wizard.getCurrentPosition().copy();
		switch (direction) {
		case left:
			executeMove(wizard, newPos.moveX(-speed));
			break;
		case right:
			executeMove(wizard, newPos.moveX(speed));
			break;
		case top:
			executeMove(wizard, newPos.moveY(speed));
			break;
		case bottom:
			executeMove(wizard, newPos.moveY(-speed));
			break;
		case topleft:
			newPos.moveX(-(speed / Variables.DIAGONAL_FACTOR));
			newPos.moveY(speed / Variables.DIAGONAL_FACTOR);
			executeMove(wizard, newPos);
			break;
		case topright:
			newPos.moveX(speed / Variables.DIAGONAL_FACTOR);
			newPos.moveY(speed / Variables.DIAGONAL_FACTOR);
			executeMove(wizard, newPos);
			break;
		case bottomright:
			newPos.moveX(speed / Variables.DIAGONAL_FACTOR);
			newPos.moveY(-(speed / Variables.DIAGONAL_FACTOR));
			executeMove(wizard, newPos);
			break;
		case bottomleft:
			newPos.moveX(-(speed / Variables.DIAGONAL_FACTOR));
			newPos.moveY(-(speed / Variables.DIAGONAL_FACTOR));
			executeMove(wizard, newPos);
			break;
		default:
			break;
		}
	}

	private void executeMove(Character wizard, Position newPos) {
		if (inBoundsX(newPos.getX(), wizard.getBoundingBoxWidth() / 2) && !checkCollisionX(wizard, newPos.getX())) {
			wizard.setCurrentPositionX(newPos.getX());
		}

		if (inBoundsY(newPos.getY(), wizard.getBoundingBoxHeight() / 2) && !checkCollisionY(wizard, newPos.getY())) {
			wizard.setCurrentPositionY(newPos.getY());
		}
	}

	private boolean inBoundsX(float newX, int spriteWidth) {
		return (newX >= 0) && (newX <= (width - spriteWidth));
	}

	private boolean checkCollisionX(Character wizard, float newX) {
		final Position oldPos = wizard.getCurrentPosition();
		float realY = height - oldPos.getY();
		for (final MyPolygon block : blocks) {
			if (block.contains(newX - (wizard.getBoundingBoxWidth() / 3.0f), realY) || block.contains(newX + (wizard.getBoundingBoxWidth() / 3.0f), realY)) {
				return true;
			}
		}
		return false;
	}

	private boolean inBoundsY(float newY, int spriteHeight) {
		return (newY >= 0) && (newY <= (height - spriteHeight));
	}

	private boolean checkCollisionY(Character wizard, float newY) {
		final Position oldPos = wizard.getCurrentPosition();
		float realY = height - newY;
		for (final MyPolygon block : blocks) {
			if (block.contains(oldPos.getX(), realY - (wizard.getBoundingBoxHeight() / 3.0f)) || block.contains(oldPos.getX(), realY + (wizard.getBoundingBoxHeight() / 3.0f))) {
				return true;
			}
		}
		return false;
	}

	public void update(Array<Character> allWizards) {
		for (Character wizard : allWizards) {
			if (wizard.isMoving()) {
				moveWizard(wizard);
			}
		}
	}

	private Direction reverseDirection(Direction currentDirection) {
		switch (currentDirection) {
		case bottom:
			return Direction.top;
		case bottomleft:
			return Direction.topright;
		case bottomright:
			return Direction.topleft;
		case left:
			return Direction.right;
		case right:
			return Direction.left;
		case top:
			return Direction.bottom;
		case topleft:
			return Direction.bottomright;
		case topright:
			return Direction.bottomleft;
		default:
			return null;

		}
	}

	public ConcurrentSkipListSet<MyPolygon> getBlocks() {
		return blocks;
	}

	public void prepareMove(Character wizard, Direction direction) {
		wizard.setCurrentDirection(direction);
		wizard.setCurrentAction(Action.move);
		wizard.setMoving(true);

	}

	public void stopMove(Character wizard) {
		wizard.setCurrentAction(Action.idle);
		wizard.setMoving(false);
	}

}
