package com.jelte.myGames.Screen;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.jelte.myGames.SinglePlayerGameTemplate;
import com.jelte.myGames.Audio.MusicManager;
import com.jelte.myGames.Audio.MusicManager.AudioCommand;
import com.jelte.myGames.Audio.MusicManager.AudioEnum;
import com.jelte.myGames.Utility.AssetManagerUtility;
import com.jelte.myGames.Utility.Variables;
import com.jelte.myGames.input.KeyBindings;
import com.jelte.myGames.input.KeyBindings.MyKeys;

import de.eskalon.commons.screen.ManagedScreen;

public class KeyBindingsScreen extends ManagedScreen {
	private Stage stage;
	private Table table;
	private String changing;
	private InputListener inputListener;

	private SpriteBatch batch;
	private SinglePlayerGameTemplate game;
	private Skin skin;
	private Array<TextButton> textButtons;
	private Map<TextButton, Label> buttonsWithLabel = new HashMap<>();

	public KeyBindingsScreen(SpriteBatch batch) {
		this.game = (SinglePlayerGameTemplate) Gdx.app.getApplicationListener();
		this.batch = batch;
		this.textButtons = new Array<>();
		skin = AssetManagerUtility.getSkin(Variables.SKIN_FILE_PATH);
	}

	@Override
	protected void create() {
		stage = new Stage();
		table = new Table();
		ClickListener listener = createListener();
		this.addInputProcessor(stage);
		table.setFillParent(true);

		createButtonForKey(Variables.LEFT, listener);
		createButtonForKey(Variables.RIGHT, listener);
		createButtonForKey(Variables.UP, listener);
		createButtonForKey(Variables.DOWN, listener);
		createButtonForKey(Variables.DASH, listener);
		createButtonForKey(Variables.ACTION_1, listener);
		createButtonForKey(Variables.ACTION_2, listener);

		table.add(createExitButton());
		stage.addActor(table);
		stage.addListener(createInputListener());
	}

	private ClickListener createListener() {
		return new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changing = ((Label) event.getTarget()).getText().toString().toLowerCase();
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
				MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE_WITH_COOLDOWN, AudioEnum.HOVER);
			}
		};
	}

	private void createButtonForKey(String key, ClickListener listener) {
		final Label label = new Label(key, skin);
		final TextButton textButton = new TextButton(KeyBindings.MyKeys.toString(KeyBindings.getBinding(key)), skin);
		textButton.addListener(listener);
		textButtons.add(textButton);
		buttonsWithLabel.put(textButton, label);
		table.add(label).expandX().pad(Variables.KEYBIND_SCREEN_BUTTON_PAD);
		table.add(textButton).fillX().expandX().pad(Variables.KEYBIND_SCREEN_BUTTON_PAD).colspan(2).row();
	}

	private TextButton createExitButton() {
		final TextButton exit = new TextButton(Variables.EXIT, skin);
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getScreenManager().pushScreen("main", "luminance");
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
				MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE_WITH_COOLDOWN, AudioEnum.HOVER);
			}
		});
		return exit;
	}

	private InputListener createInputListener() {
		inputListener = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				int key = button == Buttons.LEFT	? MyKeys.LEFT_MOUSE
													: MyKeys.RIGHT_MOUSE;
				return keyDown(event, key);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				int key = button == Buttons.LEFT	? MyKeys.LEFT_MOUSE
													: MyKeys.RIGHT_MOUSE;
				keyUp(event, key);
			}

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if (keycode == KeyBindings.MyKeys.ESCAPE) {
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							game.getScreenManager().pushScreen("main", "luminance");
						}
					});
				}

				if (changing == null) {
					return false;
				}

				textButtons.forEach(textButton -> checkTextButton(textButton, keycode));
				changing = null;
				KeyBindings.saveBindings();
				return true;
			}
		};
		return inputListener;
	}

	protected void checkTextButton(TextButton textButton, int keycode) {
		if (changing.equalsIgnoreCase(textButton.getText().toString()) && KeyBindings.setBinding(buttonsWithLabel.get(textButton).getText().toString(), keycode)) {
			textButton.setText(KeyBindings.MyKeys.toString(keycode));
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.15f, .15f, .15f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();

		MusicManager.getInstance().update(delta, Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		if (stage != null) {
			stage.removeListener(inputListener);
			stage.dispose(); // gives error???
		}
		// batch.dispose();

	}

}
