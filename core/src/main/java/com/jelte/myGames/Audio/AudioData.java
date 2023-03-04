package com.jelte.myGames.Audio;

import lombok.Data;

@Data
public class AudioData {
	private int id;
	private String name;
	private String audioFileName;
	private float volume;
	private float duration;
	private float cooldown;
}
