package com.jelte.myGames;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.jelte.myGames.Audio.AudioFileReader;
import com.jelte.myGames.Audio.MusicManager;
import com.jelte.myGames.Audio.MusicManager.AudioCommand;
import com.jelte.myGames.Audio.MusicManager.AudioEnum;
import com.jelte.myGames.Screen.GameScreen;
import com.jelte.myGames.Screen.KeyBindingsScreen;
import com.jelte.myGames.Screen.MainMenuScreen;
import com.jelte.myGames.Screen.MyManagedGame;
import com.jelte.myGames.Utility.AssetManagerUtility;
import com.jelte.myGames.Utility.Variables;
import com.jelte.myGames.shared.entities.CharacterFileReader;
import com.kotcrab.vis.ui.VisUI;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.GLTransitionsShaderTransition;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class SinglePlayerGameTemplate extends MyManagedGame<ManagedScreen, ScreenTransition> {
	private Player player;
	private SpriteBatch batch;

	@Override
	public void create() {
		super.create();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		AudioFileReader.loadAudioInMemory();
		AssetManagerUtility.loadTextureAtlas(Variables.SKIN_TEXTURE_ATLAS_PATH);
		AssetManagerUtility.loadTextureAtlas(Variables.SPRITES_ATLAS_PATH);
		AssetManagerUtility.loadTextureAtlas(Variables.SPRITES_BACKGROUND_ATLAS_PATH);
		AssetManagerUtility.loadSkin(Variables.SKIN_FILE_PATH);
		VisUI.load(AssetManagerUtility.getSkin(Variables.SKIN_FILE_PATH));
		CharacterFileReader.loadUnitStatsInMemory();
		for (final AudioEnum audioEnum : AudioEnum.values()) {
			MusicManager.getInstance().sendCommand(AudioCommand.SOUND_LOAD, audioEnum);
		}

		this.batch = new SpriteBatch();
		this.screenManager.addScreen("main", new MainMenuScreen(batch));
		this.screenManager.addScreen("gameScreen", new GameScreen(batch));
		this.screenManager.addScreen("keybindings", new KeyBindingsScreen(batch));

		final GLTransitionsShaderTransition luminanceTransition = new GLTransitionsShaderTransition(0.9F, Interpolation.smooth);
		luminanceTransition.compileGLTransition(Variables.LUMINANCE_SHADER);
		screenManager.addScreenTransition("luminance", luminanceTransition);

		screenManager.pushScreen("login", "luminance");
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (width == 0 || height == 0) {
			return;// cant return 0 0 on windows, crashes
		}
		screenManager.resize(width, height);
	}

	@Override
	public void render() {
		screenManager.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void pause() {
		screenManager.pause();
	}

	@Override
	public void resume() {
		screenManager.resume();
	}

	@Override
	public void dispose() {
		screenManager.dispose();
		VisUI.dispose();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}