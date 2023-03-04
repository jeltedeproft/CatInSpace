package com.jelte.myGames.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jelte.myGames.SinglePlayerGameTemplate;
import com.jelte.myGames.Audio.MusicManager;
import com.jelte.myGames.Utility.AssetManagerUtility;
import com.jelte.myGames.Utility.Variables;

import de.eskalon.commons.screen.ManagedScreen;

public abstract class MyScreen extends ManagedScreen {
	protected SpriteBatch batch;
	protected ExtendViewport viewport;
	protected BitmapFont font;
	protected SinglePlayerGameTemplate game;
	protected OrthographicCamera mainCamera;
	protected Table root;

	protected Stage stage;
	protected Skin skin;

	protected MyScreen(SpriteBatch batch) {
		this.game = (SinglePlayerGameTemplate) Gdx.app.getApplicationListener();
		this.batch = batch;
	}

	@Override
	protected void create() {
		font = new BitmapFont();
		skin = AssetManagerUtility.getSkin(Variables.SKIN_FILE_PATH);
		viewport = new ExtendViewport(Variables.VISIBLE_WIDTH, Variables.VISIBLE_HEIGHT);
		stage = new Stage(viewport);
		this.addInputProcessor(stage);

		mainCamera = new OrthographicCamera();
		mainCamera.setToOrtho(false, Variables.VISIBLE_WIDTH, Variables.VISIBLE_HEIGHT);
		mainCamera.update();

		root = new Table();
		root.setFillParent(true);
		stage.addActor(root);
	}

	@Override
	public void render(float delta) {
		viewport.apply();

		Gdx.graphics.setTitle("fps: " + Gdx.graphics.getFramesPerSecond() + "(" + Gdx.graphics.getWidth() + "," + Gdx.graphics.getHeight() + ")");

		stage.act();
		stage.draw();

		MusicManager.getInstance().update(delta, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		stage.getViewport().update(width, height, true);

	}

	@Override
	public void dispose() {
		if (font != null) {
			font.dispose();
		}
		if (stage != null) {
			stage.dispose();
		}
		// batch.dispose(); //already disposed
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
