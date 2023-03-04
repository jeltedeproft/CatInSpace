package com.jelte.myGames.Utility;

import java.util.Comparator;

import com.badlogic.gdx.math.Polygon;

public class MyPolygon extends Polygon implements Comparable<MyPolygon> {

	Comparator<MyPolygon> polygonComparator = Comparator.comparingDouble(MyPolygon::area);

	public MyPolygon(float[] vertices) {
		super(vertices);
	}

	@Override
	public int compareTo(MyPolygon o) {
		return polygonComparator.compare(o, this);
	}

	@Override
	public String toString() {
		return "(" + super.getX() + "," + super.getY() + ")   :  " + super.getBoundingRectangle().getWidth() + "," + super.getBoundingRectangle().getHeight();
	}

	public void setPosition(Position pos) {
		super.setPosition(pos.getX(), pos.getY());
	}

}
