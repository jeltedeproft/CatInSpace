package com.jelte.myGames.Drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.jelte.myGames.Utility.AssetManagerUtility;

public class SpriteActor extends Actor {

	Sprite sprite;
	float spriteWidth;
	float spriteHeight;
	private Animation<Sprite> animation;
	private float animTime = 0f;

	public SpriteActor(String name, float width, float height) {
		this.spriteWidth = width;
		this.spriteHeight = height;
		animation = AssetManagerUtility.getAnimation(name + "-idle-right", 0.3f, Animation.PlayMode.LOOP);
		sprite = animation.getKeyFrame(0, true);
		sprite.setSize(width, height);

		setWidth(width);
		setHeight(height);
		setBounds(0, 0, getWidth(), getHeight());
		setTouchable(Touchable.enabled);
		setX(0);
		setY(0);
	}

	public SpriteActor(String name) {
		sprite = AssetManagerUtility.getSprite(name);
		this.spriteWidth = sprite.getWidth();
		this.spriteHeight = sprite.getWidth();

		setWidth(spriteWidth);
		setHeight(spriteHeight);
		setBounds(0, 0, getWidth(), getHeight());
		setTouchable(Touchable.enabled);
		setX(0);
		setY(0);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(sprite, getX(), getY(), spriteWidth, spriteHeight);
	}

	public void changeSprite(String name) {
		float x = getX();
		float y = getY();
		animation = AssetManagerUtility.getAnimation(name + "-idle-right", 0.3f, Animation.PlayMode.LOOP);
		animTime = 0f;
		sprite = animation.getKeyFrame(0, true);
		sprite.setSize(spriteWidth, spriteHeight);

		setWidth(spriteWidth);
		setHeight(spriteHeight);
		setBounds(x, y, getWidth(), getHeight());
		setTouchable(Touchable.enabled);
		setX(x);
		setY(y);

	}

	public void update(float delta) {
		animTime += delta;
		sprite = animation.getKeyFrame(animTime, true);
	}

}