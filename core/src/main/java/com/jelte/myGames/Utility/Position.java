package com.jelte.myGames.Utility;

import java.util.Objects;

import com.badlogic.gdx.Gdx;

public class Position {
	private float x;
	private float y;

	public Position() {
		x = 0;
		y = 0;
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Position setPositionFromTiled(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Position setPositionFromScreen(float x, float y) {
		this.x = x;
		this.y = Gdx.graphics.getHeight() - y;
		return this;
	}

	public Position moveX(double amount) {
		x += amount;
		return this;
	}

	public Position moveX(float amount) {
		x += amount;
		return this;
	}

	public Position moveY(float amount) {
		y += amount;
		return this;
	}

	public Position moveY(double amount) {
		y += amount;
		return this;
	}

	public void setX(double d) {
		x = (float) d;
	}

	public void setY(double d) {
		y = (float) d;
	}

	public boolean isWithinMarginOf(Position otherPosition, float margin) {
		final float xDiff = Math.abs(x - otherPosition.x);
		final float yDiff = Math.abs(y - otherPosition.y);
		return (xDiff + yDiff) <= margin;
	}

	public Position moveTowardOther(Position dest, float speed) {
		if (dest == null) {
			return this;
		}
		float deltaX = dest.getX() - this.x;
		float deltaY = dest.getY() - this.y;

		// Compute the distance
		double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));

		// Compute the (normalized) direction
		double dirX = deltaX / distance;
		double dirY = deltaY / distance;

		// (The vector (dirX, dirY) now has length 1.0)

		this.x = (float) (this.x + (speed * dirX));
		this.y = (float) (this.y + (speed * dirY));
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final Position other = (Position) obj;
		return (Float.floatToIntBits(x) == Float.floatToIntBits(other.x)) && (Float.floatToIntBits(y) == Float.floatToIntBits(other.y));
	}

	public Position copy() {
		return new Position(x, y);
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

}
