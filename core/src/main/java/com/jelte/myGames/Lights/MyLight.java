package com.jelte.myGames.Lights;

import com.aliasifkhan.hackLights.HackLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.jelte.myGames.Utility.AssetManagerUtility;
import com.jelte.myGames.Utility.Position;
import com.jelte.myGames.Utility.Variables;
import com.jelte.myGames.shared.entities.Direction;

import lombok.ToString;

@ToString
public class MyLight implements Poolable {
	private static final LightsEnum DEFAULT_LIGHT = LightsEnum.MEDIUM_ROUND_LIGHT;
	private LightsEnum type = LightsEnum.MEDIUM_ROUND_LIGHT;
	private HackLight light;

	public HackLight init(AtlasRegion region, Position pos, Color color) {
		light = new HackLight(region, color.r, color.g, color.b, color.a, Variables.DEFAULT_SCALE);
		light.setOriginBasedPosition(pos.getX(), pos.getY());
		return light;
	}

	public HackLight init(LightsEnum type, AtlasRegion region, Position pos, Color color, float scale) {
		this.type = type;
		light = new HackLight(region, color.r, color.g, color.b, color.a, scale);
		light.setOriginBasedPosition(pos.getX(), pos.getY());
		return light;
	}

	public HackLight init(LightsEnum type, Position pos, Color color) {
		light = new HackLight(AssetManagerUtility.getAtlasRegion(type.getLightName()), color.r, color.g, color.b, color.a, Variables.DEFAULT_SCALE);
		light.setOriginBasedPosition(pos.getX(), pos.getY());
		this.type = type;
		return light;
	}

	public HackLight init(LightsEnum type) {
		this.type = type;
		light = new HackLight(AssetManagerUtility.getAtlasRegion(type.getLightName()), Variables.DEFAULT_RED, Variables.DEFAULT_GREEN, Variables.DEFAULT_BLUE, Variables.DEFAULT_ALPHA, Variables.DEFAULT_SCALE);
		return light;
	}

	public HackLight init(Position pos) {
		light = new HackLight(AssetManagerUtility.getAtlasRegion(DEFAULT_LIGHT.getLightName()), Variables.DEFAULT_RED, Variables.DEFAULT_GREEN, Variables.DEFAULT_BLUE, Variables.DEFAULT_ALPHA,
				Variables.DEFAULT_SCALE);
		light.setOriginBasedPosition(pos.getX(), pos.getY());
		return light;
	}

	public HackLight init(Color color) {
		light = new HackLight(AssetManagerUtility.getAtlasRegion(DEFAULT_LIGHT.getLightName()), color.r, color.g, color.b, color.a);
		return light;
	}

	public HackLight init(TextureRegion region, float r, float g, float b, float a) {
		light = new HackLight(region, r, g, b, a);
		return light;
	}

	public HackLight getLight() {
		return light;
	}

	public LightsEnum getType() {
		return type;
	}

	public MyLight() {
		// use init
	}

	@Override
	public void reset() {
		light = null;
	}

	public void setPositionShifted(float x, float y, Direction direction) {
		switch (direction) {
		case bottom:
			light.setOriginBasedPosition(x, y - (light.getHeight() / 2));
			break;
		case bottomleft:
			light.setOriginBasedPosition(x - (light.getWidth() / 2), y - (light.getHeight() / 2));
			break;
		case bottomright:
			light.setOriginBasedPosition(x + (light.getWidth() / 2), y - (light.getHeight() / 2));
			break;
		case left:
			light.setOriginBasedPosition(x - (light.getWidth() / 2), y);
			break;
		case right:
			light.setOriginBasedPosition(x + (light.getWidth() / 2), y);
			break;
		case top:
			light.setOriginBasedPosition(x, y + (light.getHeight() / 2));
			break;
		case topleft:
			light.setOriginBasedPosition(x - (light.getWidth() / 2), y + (light.getHeight() / 2));
			break;
		case topright:
			light.setOriginBasedPosition(x + (light.getWidth() / 2), y + (light.getHeight() / 2));
			break;
		default:
			break;

		}
	}

	public void setPosition(float x, float y) {
		light.setOriginBasedPosition(x, y);
	}

	public void setPosition(Position positionToDraw) {
		light.setOriginBasedPosition(positionToDraw.getX(), positionToDraw.getY());
	}

	public void setDirection(Direction direction) {
		switch (direction) {
		case bottom:
			light.setRotation(180);
			break;
		case top:
			light.setRotation(0);
			break;
		case bottomleft:
			light.setRotation(135);
			break;
		case topright:
			light.setRotation(315);
			break;
		case bottomright:
			light.setRotation(225);
			break;
		case topleft:
			light.setRotation(45);
			break;
		case left:
			light.setRotation(90);
			break;
		case right:
			light.setRotation(270);
			break;
		default:
			break;

		}
	}

}
