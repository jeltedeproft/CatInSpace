package com.jelte.myGames.input;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.IntSet;
import com.jelte.myGames.Screen.GameScreen;
import com.jelte.myGames.Utility.Variables;
import com.jelte.myGames.input.KeyBindings.MyKeys;
import com.jelte.myGames.shared.entities.Direction;

public class MyInputAdapter extends InputAdapter {

	private final GameScreen gameScreen;
	private final IntSet downKeys = new IntSet(Variables.MAX_DOWNKEYS);

	public MyInputAdapter(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		final int key = button == Buttons.LEFT ? MyKeys.LEFT_MOUSE : MyKeys.RIGHT_MOUSE;
		return keyDown(key);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		final int key = button == Buttons.LEFT ? MyKeys.LEFT_MOUSE : MyKeys.RIGHT_MOUSE;
		return keyUp(key);
	}

	@Override
	public boolean keyDown(int keycode) {
		downKeys.add(keycode);
		if (downKeys.size >= 2) {
			checkMovementMultipleKeysDown();
		} else {
			checkMovementSingleKeyDown(keycode);
		}

		if (keycode == KeyBindings.getBinding(Variables.ACTION_1)) {
			executeAction(Variables.ACTION_1);
		}

		if (keycode == KeyBindings.getBinding(Variables.ACTION_2)) {
			executeAction(Variables.ACTION_2);
		}

		return true;
	}

	private void executeAction(String action) {
		// do something here
		// send to server?
	}

	private void checkMovementSingleKeyDown(int keyCode) {
		if (keyCode == KeyBindings.getBinding(Variables.LEFT)) {
			client.sendMoveRequest(Direction.left);
		}
		if (keyCode == KeyBindings.getBinding(Variables.RIGHT)) {
			client.sendMoveRequest(Direction.right);
		}
		if (keyCode == KeyBindings.getBinding(Variables.UP)) {
			client.sendMoveRequest(Direction.top);
		}
		if (keyCode == KeyBindings.getBinding(Variables.DOWN)) {
			client.sendMoveRequest(Direction.bottom);
		}
	}

	private void checkMovementMultipleKeysDown() {
		if (downKeys.size == 2 && downKeys.contains(KeyBindings.getBinding(Variables.LEFT)) && downKeys.contains(KeyBindings.getBinding(Variables.UP))) {
			client.sendMoveRequest(Direction.topleft);
		}
		if (downKeys.size == 2 && downKeys.contains(KeyBindings.getBinding(Variables.LEFT)) && downKeys.contains(KeyBindings.getBinding(Variables.DOWN))) {
			client.sendMoveRequest(Direction.bottomleft);
		}
		if (downKeys.size == 2 && downKeys.contains(KeyBindings.getBinding(Variables.RIGHT)) && downKeys.contains(KeyBindings.getBinding(Variables.UP))) {
			client.sendMoveRequest(Direction.topright);
		}
		if (downKeys.size == 2 && downKeys.contains(KeyBindings.getBinding(Variables.RIGHT)) && downKeys.contains(KeyBindings.getBinding(Variables.DOWN))) {
			client.sendMoveRequest(Direction.bottomright);
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		downKeys.remove(keycode);
		if (downKeys.size >= 2) {
			checkMovementMultipleKeysDown();
		} else if (downKeys.size == 1) {
			checkMovementSingleKeyDown(downKeys.first());
		} else {
			client.sendStopMoveRequest();
		}
		return true;
	}

}
