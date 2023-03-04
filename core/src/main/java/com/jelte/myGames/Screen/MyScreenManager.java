package com.jelte.myGames.Screen;

import javax.annotation.Nullable;

import com.jelte.myGames.Audio.MusicManager;
import com.jelte.myGames.Audio.MusicManager.AudioCommand;
import com.jelte.myGames.Audio.MusicManager.AudioEnum;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.ScreenManager;
import de.eskalon.commons.screen.transition.ScreenTransition;

public class MyScreenManager<S extends ManagedScreen, T extends ScreenTransition> extends ScreenManager<S, T> {

	@Override
	public void pushScreen(String name, @Nullable String transitionName, Object... params) {
		MusicManager.getInstance().sendCommand(AudioCommand.SOUND_PLAY_ONCE, AudioEnum.NEXT_SCREEN);
		super.pushScreen(name, transitionName, params);
	}

}
