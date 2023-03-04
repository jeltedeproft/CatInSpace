package com.jelte.myGames;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private final String name;
    private int id;

    public Player(String name, int id) {
	this.name = name;
	this.id = id;
    }

    public void setId(int id) {
	this.id = id;
    }

}
