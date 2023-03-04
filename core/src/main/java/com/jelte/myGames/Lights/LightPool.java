package com.jelte.myGames.Lights;

import com.badlogic.gdx.utils.Pool;
import com.jelte.myGames.Utility.Variables;

public class LightPool extends Pool<MyLight> {

    @Override
    protected MyLight newObject() {
	return new MyLight();
    }

    public LightPool() {
	super(Variables.LIGHT_POOL_DEFAULT_INIT_SIZE);
    }

    public LightPool(int init, int max) {
	super(init, max);
    }

}
