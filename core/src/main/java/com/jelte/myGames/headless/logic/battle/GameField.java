package com.jelte.myGames.headless.logic.battle;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.jelte.myGames.Utility.MyPolygon;
import com.jelte.myGames.Utility.Position;
import com.jelte.myGames.shared.entities.Character;
import com.jelte.myGames.shared.entities.Direction;
import com.jelte.myGames.shared.serverClientMessages.ThingsToDraw;
import com.jelte.myGames.shared.serverClientMessages.ThingsToDraw.WizardState;

public class GameField {
	private final Array<Character> characters;
	private final ConcurrentHashMap<UUID, Position> mousePositions;
	private final MovementManager movementManager;
	private final CollisionManager collisionManager;
	private float gameTime = 0;
	private final ThingsToDraw stateOfBattle = new ThingsToDraw();
	private final Array<WizardState> wizardStates = new Array<>();
	private final Array<Position> lightPositions;

	public GameField(int width, int height, ConcurrentSkipListSet<MyPolygon> blocks, final List<Position> spawnPositions, Array<Position> lightPositions, Array<Character> characters) {
		movementManager = new MovementManager(width, height, blocks);
		collisionManager = new CollisionManager(width, height);

		this.lightPositions = inverseY(lightPositions, height);
		this.characters = characters;
		mousePositions = new ConcurrentHashMap<>();
		if (spawnPositions.size() < characters.size) {
			Gdx.app.error("BattleField", "map does not have enough spawn points(" + spawnPositions.size() + ") for so many heroes (" + characters.size + ")");
		} else {
			characters.forEach(v -> {
				final float oldHeight = spawnPositions.get(0).getY();
				spawnPositions.get(0).setY(height - oldHeight);
				v.setCurrentPosition(spawnPositions.get(0));
				spawnPositions.remove(0);
			});
		}
	}

	private Array<Position> inverseY(Array<Position> lightPositions, int height) {
		final Array<Position> inversedPositoins = new Array<>();
		for (final Position pos : lightPositions) {
			inversedPositoins.add(new Position(pos.getX(), height - pos.getY()));
		}
		return inversedPositoins;
	}

	public void update(float delta) {
		gameTime += delta;
		final Array<Character> allCharacters = new Array<>();
		characters.forEach(allCharacters::add);
		collisionManager.update(characters);
		movementManager.update(allCharacters);
		characters.forEach(v -> v.update(delta));
	}

	public void moveCharacter(int playerId, Direction direction) {
		final Character character = getWizard(playerId);
		movementManager.prepareMove(character, direction);
	}

	public void stopMoveWizard(int playerId) {
		final Character wizard = getWizard(playerId);
		movementManager.stopMove(wizard);
	}

	public ThingsToDraw getStateOfBattle() {
		wizardStates.clear();
		for (final Character character : characters) {
			final WizardState wizardState = new WizardState();
			wizardState.hp = character.getCurrentHp();
			wizardState.maxHp = character.getData().getMaxHP();
			wizardState.id = character.getId();
			wizardState.x = character.getWizardArea().getX();
			wizardState.y = character.getWizardArea().getY();
			wizardState.direction = character.getCurrentDirection();
			wizardState.action = character.getCurrentAction();
			wizardState.dead = character.isDead();
			wizardState.moving = character.isMoving();
			wizardState.color = character.getColor();
			wizardState.width = character.getWizardArea().getBoundingRectangle().getWidth();
			wizardState.height = character.getWizardArea().getBoundingRectangle().getHeight();
			wizardState.name = character.getSpriteName();
			wizardState.team = character.getTeam();
			wizardStates.add(wizardState);
		}

		stateOfBattle.wizardStates = wizardStates;
		stateOfBattle.lights = lightPositions;
		return stateOfBattle;
	}

	public Array<Character> getWizards() {
		return characters;
	}

	public Character getWizard(UUID wizardId) {
		for (final Character character : characters) {
			if (character.getId().equals(wizardId)) {
				return character;
			}
		}
		return null;
	}

	public MovementManager getMovementManager() {
		return movementManager;
	}

	@Override
	public String toString() {
		return "BattleField [wizards=" + characters + ", gameTime=" + gameTime + "]";
	}

	public void resetTime() {
		gameTime = 0;
	}

}
