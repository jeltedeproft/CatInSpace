package com.jelte.myGames.Screen.shaders;

/*******************************************************************************
 * Copyright 2019 metaphore
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import com.badlogic.gdx.Gdx;
import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;
import com.crashinvaders.vfx.gl.VfxGLUtils;
import com.jelte.myGames.Utility.Variables;

public class AllBlackEffect extends ShaderVfxEffect implements ChainVfxEffect {

	private static final String U_TEXTURE0 = "u_texture";

	public AllBlackEffect() {
		super(VfxGLUtils.compileShader(Gdx.files.classpath(Variables.SHADER_VERTEX_ALL_BLACK), Gdx.files.classpath(Variables.SHADER_FRAG_ALL_BLACK)));
		rebind();
	}

	@Override
	public void resize(int width, int height) {
		// Do nothing.
	}

	@Override
	public void rebind() {
		super.rebind();
		program.begin();
		program.setUniformi(U_TEXTURE0, TEXTURE_HANDLE0);
		program.end();
	}

	@Override
	public void render(VfxRenderContext context, VfxPingPongWrapper buffers) {
		render(context, buffers.getSrcBuffer(), buffers.getDstBuffer());
	}

	public void render(VfxRenderContext context, VfxFrameBuffer src, VfxFrameBuffer dst) {
		// Bind src buffer's texture as a primary one.
		src.getTexture().bind(TEXTURE_HANDLE0);
		// Apply shader effect and render result to dst buffer.
		renderShader(context, dst);
	}
}
