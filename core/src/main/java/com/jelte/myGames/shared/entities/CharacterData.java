package com.jelte.myGames.shared.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CharacterData {

	private int id;
	private String name;
	private String entitySpriteName;
	private boolean melee;
	private float maxHP;
	private float movementSpeed;
	private int attackPower;
	private int defense;
	private int boundingBoxWidth;
	private int boundingBoxHeight;
	private String unitExplanation;
	private float idleAnimationSpeed;
	private float hurtAnimationSpeed;
	private float dieAnimationSpeed;
	private float moveAnimationSpeed;
	private float hurtAnimationFullTime;
	private float dieAnimationFullTime;
	private float AppearAnimationFullTime;
	private String basicAttackSound;
}
