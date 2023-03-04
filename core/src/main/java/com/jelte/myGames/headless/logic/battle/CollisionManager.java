package com.jelte.myGames.headless.logic.battle;

import com.badlogic.gdx.utils.Array;
import com.jelte.myGames.shared.entities.Character;

public class CollisionManager {
	private static final String TAG = CollisionManager.class.getSimpleName();

	public CollisionManager(int width, int height) {
	}

	public void update(Array<Character> characters) {
		for (final Character character : characters) {
			if (character.isDead()) {
				continue;
			}
			executeCollisionEffect(character);

		}
	}

	private void executeCollisionEffect(final Character character) {
		// collision effects
	}

}
