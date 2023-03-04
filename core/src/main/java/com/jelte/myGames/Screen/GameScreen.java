package com.jelte.myGames.Screen;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jelte.myGames.MapType;
import com.jelte.myGames.SinglePlayerGameTemplate;
import com.jelte.myGames.Animation.AnimationManager;
import com.jelte.myGames.Audio.MusicManager;
import com.jelte.myGames.Audio.MusicManager.AudioCommand;
import com.jelte.myGames.Audio.MusicManager.AudioEnum;
import com.jelte.myGames.Drawables.HealthBar;
import com.jelte.myGames.Lights.LightManager;
import com.jelte.myGames.Screen.shaders.ShaderManager;
import com.jelte.myGames.Utility.AssetManagerUtility;
import com.jelte.myGames.Utility.GraphicalUtility;
import com.jelte.myGames.Utility.Position;
import com.jelte.myGames.Utility.Variables;
import com.jelte.myGames.shared.entities.TeamsEnum;
import com.jelte.myGames.shared.serverClientMessages.ThingsToDraw.WizardState;

import de.eskalon.commons.screen.ManagedScreen;

public class GameScreen extends ManagedScreen {
	protected SpriteBatch batch;
	protected SinglePlayerGameTemplate game;
	protected Skin skin;
	protected BitmapFont font;

	protected final AnimationManager animationManager;
	protected LightManager lightManager;
	protected ShaderManager shaderManager;

	protected final Map<UUID, HealthBar> healthBars;

	protected OrthogonalTiledMapRenderer mapRenderer;
	protected ExtendViewport gameViewPort;
	protected Stage uiStage;
	protected TooltipManager tooltipManager;

	protected InputAdapter inputAdapter;

	// UI
	Table root = new Table();
	Table topBar = new Table();
	Table middleBar = new Table();
	Table bottomBar = new Table();

	public GameScreen(SpriteBatch batch) {
		game = (SinglePlayerGameTemplate) Gdx.app.getApplicationListener();
		this.batch = batch;
		font = new BitmapFont();
		font.getData().setScale(Variables.HP_BAR_FONT_SCALE);
		animationManager = new AnimationManager();
		lightManager = new LightManager();
		shaderManager = new ShaderManager();
		healthBars = new HashMap<>();
		skin = AssetManagerUtility.getSkin(Variables.SKIN_FILE_PATH);
		tooltipManager = new TooltipManager();
		tooltipManager.instant();
		tooltipManager.maxWidth = Variables.TOOLTIP_MAX_WIDTH;
	}

	@Override
	protected void create() {
		MusicManager.getInstance().sendCommand(AudioCommand.MUSIC_STOP, AudioEnum.MAIN_MENU);
		MusicManager.getInstance().sendCommand(AudioCommand.MUSIC_PLAY_LOOP, AudioEnum.GAME_MUSIC);

		createMap();

		uiStage = new Stage(new ExtendViewport(Variables.VISIBLE_WIDTH, Variables.VISIBLE_HEIGHT), batch);
		createHud();
		this.addInputProcessor(uiStage);
		this.addInputProcessor(inputAdapter);
	}

	protected void createMap() {
		final MapType mapType = MapType.BASIC;
		AssetManagerUtility.loadMapAsset(mapType.toString());
		mapRenderer = new OrthogonalTiledMapRenderer(AssetManagerUtility.getMapAsset(mapType.toString()), Variables.MAP_UNIT_SCALE, batch);
	}

	protected void createHud() {
		root.setFillParent(true);
		uiStage.addActor(root);

		topBar.left();
		topBar.setTouchable(Touchable.disabled);

		root.add(topBar).size(Variables.HUD_BARS_WIDTH, Variables.HUD_TOP_BAR_HEIGHT).top().left().expand();
		root.row();
		root.add(middleBar).size(Variables.HUD_BARS_WIDTH, Variables.HUD_MIDDLE_BAR_HEIGHT).expand().left();
		root.row();
		root.add(bottomBar).size(Variables.HUD_BARS_WIDTH, Variables.HUD_BOTTOM_BAR_HEIGHT).bottom().center().expand();
	}

	@Override
	public void render(float delta) {
		Gdx.graphics.setTitle("fps: " + Gdx.graphics.getFramesPerSecond() + "(" + Gdx.graphics.getWidth() + "," + Gdx.graphics.getHeight() + ")");
		shaderManager.update(delta);
		shaderManager.preRender();

		updateObjects(delta);

		renderMap();
		gameViewPort.apply();
		batch.setProjectionMatrix(gameViewPort.getCamera().combined);
		batch.begin();
		renderCharacters();
		batch.end();

		shaderManager.afterRender();

		// updateLights();
		// debugSprites();

		renderUI();
	}

	protected void updateObjects(float delta) {
		animationManager.update(delta);
		MusicManager.getInstance().update(delta, gameViewPort.getCamera().position.x, gameViewPort.getCamera().position.y);
	}

	protected void renderMap() {
		gameViewPort.getCamera().position.set(thingsToDrawNextRound.cameraX, thingsToDrawNextRound.cameraY, 0f);
		gameViewPort.getCamera().update();
		final OrthographicCamera oCam = (OrthographicCamera) gameViewPort.getCamera();
		mapRenderer.setView(oCam);
		mapRenderer.render();
	}

	protected void renderCharacters() {
		for (final WizardState wizardState : thingsToDrawNextRound.wizardStates) {
			final Sprite sprite = animationManager.getTextureForWizard(wizardState);
			sprite.setOriginCenter();
			sprite.setOriginBasedPosition(wizardState.x, wizardState.y);
			sprite.draw(batch);
			updateHealthBar(wizardState.id, wizardState.x, wizardState.y, wizardState.maxHp, wizardState.hp, batch, wizardState.team);
		}
	}

	protected void updateLights() {
		lightManager.update(thingsToDrawNextRound);
		lightManager.render(gameViewPort.getCamera().combined);// TODO comment this line to take away darkness
	}

	protected void debugSprites() {
		batch.begin();
		for (final WizardState wizardState : thingsToDrawNextRound.wizardStates) {
			GraphicalUtility.drawDebugRectangle(wizardState.x - wizardState.width / 2, wizardState.y - wizardState.height / 2, wizardState.width, wizardState.height, 3, Color.RED, gameViewPort.getCamera().combined);
		}
		batch.end();
	}

	protected void updateHealthBar(UUID id, float x, float y, float maxHp, float hp, SpriteBatch batch, TeamsEnum team) {
		healthBars.computeIfAbsent(id, k -> new HealthBar(x, y, maxHp, font, team));
		healthBars.get(id).update(x, y, hp);
		healthBars.get(id).draw(batch);
	}

	protected void renderUI() {
		uiStage.getViewport().apply();
		uiStage.act();
		uiStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// lightManager.resize(width, height);
		gameViewPort.update(width, height);
		uiStage.getViewport().update(width, height, true);
		shaderManager.resize(width, height);
	}

	public Position getMousePosition() {
		final Vector3 mouseAtScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		final Vector3 vc = gameViewPort.getCamera().unproject(new Vector3(mouseAtScreen.x, mouseAtScreen.y, mouseAtScreen.z));
		return new Position(vc.x, vc.y);
	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	@Override
	public void dispose() {
		batch.dispose();
		if (uiStage != null) {
			uiStage.dispose();
		}

		font.dispose();
		if (mapRenderer != null) {
			mapRenderer.dispose();
		}

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
