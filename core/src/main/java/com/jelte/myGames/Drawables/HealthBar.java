package com.jelte.myGames.Drawables;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.jelte.myGames.Utility.AssetManagerUtility;
import com.jelte.myGames.Utility.Variables;
import com.jelte.myGames.shared.entities.TeamsEnum;

public class HealthBar {
	private NinePatch healthBar;
	private NinePatch border;
	private float x;
	private float y;
	private float maxHp;
	private float hp;
	private BitmapFont font;
	private boolean visible = true;

	public HealthBar(float x, float y, float maxHp, BitmapFont font, TeamsEnum team) {
		healthBar = new NinePatch(getColorForTeam(team), 0, 0, 0, 0);
		border = new NinePatch(AssetManagerUtility.getAtlasRegion(Variables.HEALTHBAR_BORDER_SPRITE_NAME), 0, 0, 0, 0);
		this.x = x;
		this.y = y;
		this.maxHp = maxHp;
		this.font = font;
	}

	private AtlasRegion getColorForTeam(TeamsEnum team) {
		switch (team) {
		case TEAM_BLUE:
			return AssetManagerUtility.getAtlasRegion(Variables.TEAM_BLUE_HEALTHBAR_SPRITE_NAME);
		case TEAM_GREEN:
			return AssetManagerUtility.getAtlasRegion(Variables.TEAM_GREEN_HEALTHBAR_SPRITE_NAME);
		case TEAM_PURPLE:
			return AssetManagerUtility.getAtlasRegion(Variables.TEAM_PURPLE_HEALTHBAR_SPRITE_NAME);
		case TEAM_YELLOW:
			return AssetManagerUtility.getAtlasRegion(Variables.TEAM_YELLOW_HEALTHBAR_SPRITE_NAME);
		default:
			return AssetManagerUtility.getAtlasRegion(Variables.TEAM_RED_HEALTHBAR_SPRITE_NAME);

		}

	}

	public void update(float x, float y, float hp) {
		this.x = x;
		this.y = y;
		this.hp = hp;
	}

	public void draw(Batch batch) {// TODO organize this mess
		if (visible) {
			float width = Variables.MAX_WIDTH_HP_BAR * (hp / maxHp);
			border.draw(batch, x - ((Variables.MAX_WIDTH_HP_BAR / 2) + Variables.BORDER_WIDTH_HP_BAR), (y - Variables.BORDER_WIDTH_HP_BAR) + Variables.OFFSET_Y_HP_BAR, width + 5, Variables.MAX_HEIGHT_HP_BAR + 5);
			healthBar.draw(batch, x - (Variables.MAX_WIDTH_HP_BAR / 2), y + Variables.OFFSET_Y_HP_BAR, width, Variables.MAX_HEIGHT_HP_BAR);
			font.draw(batch, Float.toString(hp), x - (Variables.MAX_WIDTH_HP_BAR / 4), y + (Variables.OFFSET_Y_HP_BAR * 1.3f));
		}
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
