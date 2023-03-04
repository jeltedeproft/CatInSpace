
package com.jelte.myGames.Utility;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public final class Utility {
	public static final Random random = new Random();

	public static int getRandomIntFrom1to(final int to) {
		final int result = random.nextInt(to);
		return result + 1;
	}

	public static float clamp(final float variable, final float max, final float min) {
		if (variable <= min) {
			return min;
		}
		if (variable < max) {
			return variable;
		}
		return max;
	}

	private Utility() {

	}

	public static String arrayOfStringsToString(Array<String> strings) {
		final StringBuilder builder = new StringBuilder();
		for (final String string : strings) {
			builder.append(string);
		}
		return builder.toString();
	}

	public static boolean isBetween(float number, float min, float max) {
		return (min <= number) && (number <= max);
	}

	public static <E> Optional<E> getRandom(Collection<E> e) {
		return e.stream().skip((long) (e.size() * random.nextDouble())).findFirst();
	}

	public static <E> E getRandom(Array<E> e) {
		return e.get(random.nextInt(e.size));
	}

	public static int incrementModulo(int x, int modulo) {
		return (x + 1) == modulo	? 0
									: x + 1;
	}

	public static boolean stringContainsItemFromList(String inputStr, String[] items) {
		return Arrays.stream(items).anyMatch(inputStr::contains);
	}

	public static boolean checkIfArrayOfStringsContainsElementFromOtherArray(Array<String> teamHeroesNames, String[] heroNames) {
		final List<String> heroNamesList = Arrays.asList(heroNames);
		for (final String str : teamHeroesNames) {
			if (heroNamesList.contains(str)) {
				return true;
			}
		}
		return false;
	}

	public static <T, U> List<U> convertStringListToIntList(List<T> listOfString, Function<T, U> function) {
		return listOfString.stream().map(function).collect(Collectors.toList());
	}

	public static String matrix4ToSTring(Matrix4 matrix) {
		return Arrays.toString(matrix.getValues());
	}

	public static Matrix4 stringToMatrix4(String arrayOfFloats) {
		arrayOfFloats = arrayOfFloats.substring(1, arrayOfFloats.length() - 1);
		String[] numbers = arrayOfFloats.split(",");
		Float[] floats = Arrays.stream(numbers).map(Float::valueOf).toArray(Float[]::new);
		float[] newFloats = new float[floats.length];
		int i = 0;
		for (Float f : floats) {
			newFloats[i] = f != null	? f
										: Float.NaN; // Or whatever default you want.
			i++;
		}
		return new Matrix4(newFloats);
	}

	// Check if Polygon intersects Rectangle
	public static boolean isCollision(MyPolygon p, Rectangle r) {
		MyPolygon rPoly = new MyPolygon(new float[] { 0, 0, r.width, 0, r.width, r.height, 0, r.height });
		rPoly.setPosition(r.x, r.y);
		if (Intersector.overlapConvexPolygons(rPoly, p)) {
			return true;
		}
		return false;
	}

	public static boolean isCollision(MyPolygon spellArea, MyPolygon wizardArea) {
		if (Intersector.overlapConvexPolygons(spellArea, wizardArea)) {
			return true;
		}
		return false;
	}
}
