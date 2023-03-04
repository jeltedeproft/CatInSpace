package com.jelte.myGames.shared.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class CharacterFileReader {
	private static final String TAG = CharacterFileReader.class.getSimpleName();
	private static boolean statsLoaded = false;
	private static ObjectMap<Integer, CharacterData> unitData = new ObjectMap<>();
	private static final String UNIT_STATS_FILE_LOCATION = "units/characters.json";
	private static final Array<String> allWizardNames = new Array<>();

	private CharacterFileReader() {

	}

	@SuppressWarnings("unchecked")
	public static void loadUnitStatsInMemory() {
		if (!statsLoaded) {
			final Json json = new Json();
			ArrayList<CharacterData> unitStats;
			try {
				unitStats = (ArrayList<CharacterData>) json.fromJson(ClassReflection.forName("java.util.ArrayList"), ClassReflection.forName("com.jelte.myGames.shared.entities.WizardData"), Gdx.files.internal(UNIT_STATS_FILE_LOCATION));
				for (int i = 0; i < unitStats.size(); i++) {
					final CharacterData data = unitStats.get(i);
					unitData.put(data.getId(), data);
					allWizardNames.add(data.getEntitySpriteName());
				}
			} catch (final ReflectionException e) {
				e.printStackTrace();
			}
		}
	}

	public static ObjectMap<Integer, CharacterData> getUnitData() {
		return unitData;
	}

	public static int getIdByName(String name) {
		for (Entry<Integer, CharacterData> entry : unitData.entries()) {
			if (entry.value.getEntitySpriteName().equals(name)) {
				return entry.key;
			}
		}
		Gdx.app.debug(TAG, "wizard id not found for : " + name);
		return -1;
	}

	public static Array<String> getAllWizardNames() {
		return allWizardNames;
	}
}
