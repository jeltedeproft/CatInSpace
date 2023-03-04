package com.jelte.myGames.Screen.shaders;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.AbstractVfxEffect;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.FilmGrainEffect;
import com.crashinvaders.vfx.effects.MotionBlurEffect;
import com.crashinvaders.vfx.effects.OldTvEffect;
import com.crashinvaders.vfx.effects.VignettingEffect;
import com.crashinvaders.vfx.effects.WaterDistortionEffect;
import com.crashinvaders.vfx.effects.util.MixEffect;

public class ShaderManager {
	private static final String TAG = ShaderManager.class.getSimpleName();
	private VfxManager vfxManager;
	private Map<ChainVfxEffect, Float> timers = new ConcurrentHashMap<>();
	private EnumMap<ShaderEffects, AbstractVfxEffect> preLoadedEffects = new EnumMap<>(ShaderEffects.class);

	// TODO put constants in class
	public ShaderManager() {
		vfxManager = new VfxManager(Pixmap.Format.RGBA8888);
		vfxManager.setBlendingEnabled(true);
		// preLoadedEffects.put(ShaderEffects.ALL_BLACK, new AllBlackEffect()); this doesnt compile??
		preLoadedEffects.put(ShaderEffects.BLOOM, new BloomEffect());
		FilmGrainEffect filmGrainEffect = new FilmGrainEffect();
		filmGrainEffect.setNoiseAmount(0.3f);
		preLoadedEffects.put(ShaderEffects.FILM_GRAIN, filmGrainEffect);
		preLoadedEffects.put(ShaderEffects.MOTION_BLUR, new MotionBlurEffect(Pixmap.Format.RGBA8888, MixEffect.Method.MIX, 0.5f));
		preLoadedEffects.put(ShaderEffects.OLD_TV, new OldTvEffect());
		VignettingEffect vignettingEffect = new VignettingEffect(false);
		vignettingEffect.setIntensity(0.8f);
		preLoadedEffects.put(ShaderEffects.VIGNETTE, vignettingEffect);
		preLoadedEffects.put(ShaderEffects.WATER_DISTORTION, new WaterDistortionEffect(0.8f, 0.8f));
	}

	public void activateEffectFor(ShaderEffects effect, Float duration) {
		switch (effect) {
		case BLOOM:
			BloomEffect bloomEffect = (BloomEffect) preLoadedEffects.get(ShaderEffects.BLOOM);
			vfxManager.addEffect(bloomEffect);
			timers.put(bloomEffect, duration);
			break;
		case FILM_GRAIN:
			FilmGrainEffect filmGrainEffect = (FilmGrainEffect) preLoadedEffects.get(ShaderEffects.FILM_GRAIN);
			vfxManager.addEffect(filmGrainEffect);
			timers.put(filmGrainEffect, duration);
			break;
		case MOTION_BLUR:
			MotionBlurEffect motionBlurEffect = (MotionBlurEffect) preLoadedEffects.get(ShaderEffects.MOTION_BLUR);
			vfxManager.addEffect(motionBlurEffect);
			timers.put(motionBlurEffect, duration);
			break;
		case OLD_TV:
			OldTvEffect oldTvEffect = (OldTvEffect) preLoadedEffects.get(ShaderEffects.OLD_TV);
			vfxManager.addEffect(oldTvEffect);
			timers.put(oldTvEffect, duration);
			break;
		case VIGNETTE:
			VignettingEffect vignettingEffect = (VignettingEffect) preLoadedEffects.get(ShaderEffects.VIGNETTE);
			vfxManager.addEffect(vignettingEffect);
			timers.put(vignettingEffect, duration);
			break;
		case WATER_DISTORTION:
			WaterDistortionEffect waterDistortionEffect = (WaterDistortionEffect) preLoadedEffects.get(ShaderEffects.WATER_DISTORTION);
			vfxManager.addEffect(waterDistortionEffect);
			timers.put(waterDistortionEffect, duration);
			break;
		case ALL_BLACK:
			// AllBlackEffect allBlackEffect = (AllBlackEffect) preLoadedEffects.get(ShaderEffects.ALL_BLACK);
			// vfxManager.addEffect(allBlackEffect);
			// timers.put(allBlackEffect, duration);
			break;
		default:
			break;
		}
	}

	public void update(float delta) {
		timers.replaceAll((k, v) -> v = v - delta);

		final Iterator<Entry<ChainVfxEffect, Float>> iterator = timers.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<ChainVfxEffect, Float> currentEffect = iterator.next();
			if (currentEffect.getValue() <= 0) {
				vfxManager.removeEffect(currentEffect.getKey());
				iterator.remove();
			}
		}
	}

	public void resize(int width, int height) {
		vfxManager.resize(width, height);
	}

	public void preRender() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		vfxManager.cleanUpBuffers();
		vfxManager.beginInputCapture();
	}

	public void afterRender() {
		vfxManager.endInputCapture();
		vfxManager.applyEffects();
		vfxManager.renderToScreen();
	}

	public void dispose() {
		vfxManager.dispose();
		for (ChainVfxEffect effect : timers.keySet()) {
			effect.dispose();
		}
	}

}
