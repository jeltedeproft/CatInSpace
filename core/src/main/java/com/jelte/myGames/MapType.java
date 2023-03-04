package com.jelte.myGames;

import com.jelte.myGames.Utility.Variables;

public enum MapType {
	BASIC {
		@Override
		public String toString() {
			return Variables.BASIC_MAP_PATH;
		}
	}
}
