package com.jelte.myGames.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Null;
import com.jelte.myGames.Audio.MusicManager;
import com.jelte.myGames.Audio.MusicManager.AudioCommand;
import com.jelte.myGames.Audio.MusicManager.AudioEnum;
import com.jelte.myGames.Utility.Variables;

public class MainMenuScreen extends MyMenuScreen {
	private TextButton gameButton;
	private TextButton keyBindingsButton;
	private TextButton exitButton;

	public MainMenuScreen(SpriteBatch batch) {
		super(batch);
	}

	@Override
	protected void create() {
		super.create();
		createButtons();
		addListeners();
		addButtons();
	}

	private void createButtons() {
		gameButton = new TextButton(Variables.START_GAME, skin);
		keyBindingsButton = new TextButton(Variables.CHANGE_KEYBINDINGS, skin);
		exitButton = new TextButton(Variables.EXIT, skin);
	}

	private void addListeners() {
		gameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getScreenManager().pushScreen("gameScreen", "luminance");
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
				MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE_WITH_COOLDOWN, AudioEnum.HOVER);
			}
		});

		keyBindingsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getScreenManager().pushScreen("keybindings", "luminance");
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
				MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE_WITH_COOLDOWN, AudioEnum.HOVER);
			}
		});

		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
				MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE_WITH_COOLDOWN, AudioEnum.HOVER);
			}
		});
	}

	private void addButtons() {
		root.add(gameButton).center().width(Variables.DEFAULT_TEXT_BUTTON_WIDTH).height(Variables.DEFAULT_TEXT_BUTTON_HEIGHT);
		root.row();
		root.add(keyBindingsButton).center().width(Variables.DEFAULT_TEXT_BUTTON_WIDTH).height(Variables.DEFAULT_TEXT_BUTTON_HEIGHT);
		root.row();
		root.add(exitButton).center().width(Variables.DEFAULT_TEXT_BUTTON_WIDTH).height(Variables.DEFAULT_TEXT_BUTTON_HEIGHT);
	}

}
