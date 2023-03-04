package com.jelte.myGames.Screen;

import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;

public class MyManagedGame<S extends ManagedScreen, T extends ScreenTransition> extends ManagedGame<S, T> {

	public MyManagedGame() {
		super();
		screenManager = new MyScreenManager<S, T>();
	}

}
