package com.jelte.myGames.Screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jelte.myGames.Utility.AssetManagerUtility;
import com.jelte.myGames.Utility.Variables;
import com.jelte.myGames.Utility.parallax.ParallaxBackground;
import com.jelte.myGames.Utility.parallax.ParallaxUtils.WH;
import com.jelte.myGames.Utility.parallax.TextureRegionParallaxLayer;

public abstract class MyMenuScreen extends MyScreen {
	private ParallaxBackground parallaxBackground;

	protected MyMenuScreen(SpriteBatch batch) {
		super(batch);
	}

	@Override
	protected void create() {
		super.create();
		createBackground();
		// animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,
		// Gdx.files.internal("sprites/gifs/wizard.gif").read());
	}

	private void createBackground() {
		final TextureAtlas atlas = AssetManagerUtility.getTextureAtlas(Variables.SPRITES_BACKGROUND_ATLAS_PATH);

		final TextureRegion layer0 = atlas.findRegion(Variables.PARALLAX_BG_NAME + "0");
		final TextureRegionParallaxLayer layer0Layer = new TextureRegionParallaxLayer(layer0, Variables.VISIBLE_HEIGHT, Variables.PARALLAX_SCROLL_RATIO_LAYER_0, WH.HEIGHT);

		final TextureRegion layer1 = atlas.findRegion(Variables.PARALLAX_BG_NAME + "1");
		final TextureRegionParallaxLayer layer1Layer = new TextureRegionParallaxLayer(layer1, Variables.VISIBLE_HEIGHT, Variables.PARALLAX_SCROLL_RATIO_LAYER_1, WH.HEIGHT);

		final TextureRegion layer2 = atlas.findRegion(Variables.PARALLAX_BG_NAME + "2");
		final TextureRegionParallaxLayer layer2Layer = new TextureRegionParallaxLayer(layer2, Variables.VISIBLE_HEIGHT, Variables.PARALLAX_SCROLL_RATIO_LAYER_2, WH.HEIGHT);

		final TextureRegion layer3 = atlas.findRegion(Variables.PARALLAX_BG_NAME + "3");
		final TextureRegionParallaxLayer layer3Layer = new TextureRegionParallaxLayer(layer3, Variables.VISIBLE_HEIGHT, Variables.PARALLAX_SCROLL_RATIO_LAYER_3, WH.HEIGHT);

		final TextureRegion layer4 = atlas.findRegion(Variables.PARALLAX_BG_NAME + "4");
		final TextureRegionParallaxLayer layer4Layer = new TextureRegionParallaxLayer(layer4, Variables.VISIBLE_HEIGHT, Variables.PARALLAX_SCROLL_RATIO_LAYER_4, WH.HEIGHT);

		final TextureRegion layer5 = atlas.findRegion(Variables.PARALLAX_BG_NAME + "5");
		final TextureRegionParallaxLayer layer5Layer = new TextureRegionParallaxLayer(layer5, Variables.VISIBLE_HEIGHT, Variables.PARALLAX_SCROLL_RATIO_LAYER_5, WH.HEIGHT);

		final TextureRegion layer6 = atlas.findRegion(Variables.PARALLAX_BG_NAME + "6");
		final TextureRegionParallaxLayer layer6Layer = new TextureRegionParallaxLayer(layer6, Variables.VISIBLE_HEIGHT, Variables.PARALLAX_SCROLL_RATIO_LAYER_6, WH.HEIGHT);

		parallaxBackground = new ParallaxBackground();
		parallaxBackground.addLayers(layer0Layer, layer1Layer, layer2Layer, layer3Layer, layer4Layer, layer5Layer, layer6Layer);
	}

	@Override
	public void render(float delta) {
		batch.begin();
		parallaxBackground.draw(mainCamera, batch);
		batch.end();
		mainCamera.translate(2, 0, 0);

		super.render(delta);
	}

}
