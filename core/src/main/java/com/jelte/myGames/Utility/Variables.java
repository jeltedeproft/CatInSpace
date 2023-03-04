package com.jelte.myGames.Utility;

import java.text.DecimalFormat;
import java.util.UUID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Variables {

	// TIMES
	public static final float ERROR_MESSAGE_VISIBLE_TIME = 4.0f;
	public static final long MATCHMAKING_THREAD_SLEEP_TIME = 5000L;
	public static final Vector2 PARALLAX_SCROLL_RATIO_LAYER_0 = new Vector2(.2f, .2f);
	public static final Vector2 PARALLAX_SCROLL_RATIO_LAYER_1 = new Vector2(.3f, .3f);
	public static final Vector2 PARALLAX_SCROLL_RATIO_LAYER_2 = new Vector2(.4f, .4f);
	public static final Vector2 PARALLAX_SCROLL_RATIO_LAYER_3 = new Vector2(.5f, .5f);
	public static final Vector2 PARALLAX_SCROLL_RATIO_LAYER_4 = new Vector2(.6f, .6f);
	public static final Vector2 PARALLAX_SCROLL_RATIO_LAYER_5 = new Vector2(.7f, .7f);
	public static final Vector2 PARALLAX_SCROLL_RATIO_LAYER_6 = new Vector2(.8f, .8f);

	// UI
	public static final int KEYBIND_SCREEN_BUTTON_PAD = 5;
	public static final float MAX_WIDTH_HP_BAR = 33;
	public static final float MAX_HEIGHT_HP_BAR = 8;
	public static final float OFFSET_Y_HP_BAR = 20;
	public static final float BORDER_WIDTH_HP_BAR = 2.5f;
	public static final int HUD_BOTTOM_BAR_HEIGHT = 300;
	public static final int HUD_MIDDLE_BAR_HEIGHT = 750;
	public static final int HUD_TOP_BAR_HEIGHT = 150;
	public static final int HUD_BARS_WIDTH = 1000;
	public static final float HP_BAR_FONT_SCALE = 0.5f;
	public static final float WRONG_CREDENTIALS_FONT_SIZE = 75.0f;
	public static final int USERNAME_BOT_PAD = 30;
	public static final int PASSWORD_BOT_PAD = 70;
	public static final int TOOLTIP_MAX_WIDTH = 400;

	// MAPS
	public static final String BASIC_MAP_PATH = "map/basic.tmx";

	// PATHS
	public static final String SPRITES_ATLAS_PATH = "sprites/sprites.atlas";
	public static final String SPRITES_BACKGROUND_ATLAS_PATH = "sprites/backgroundSprites.atlas";
	public static final String SKIN_TEXTURE_ATLAS_PATH = "skins/darkVisUI.atlas";
	public static final String SKIN_FILE_PATH = "skins/darkVisUI.json";
	public static final String INTRO_VIDEO_PATH = "video/main3.ogg";

	// NAMES
	public static final String PARALLAX_BG_NAME = "layer";
	public static final String PREFERENCES = "preferences";
	public static final String PREFERENCES_KEYBINDINGS = "keyBindings";
	public static final String PREFERENCE_WIZARD_NAME = "wizardname";
	public static final String TEAM_RED_HEALTHBAR_SPRITE_NAME = "redpixel";
	public static final String TEAM_BLUE_HEALTHBAR_SPRITE_NAME = "bluepixel";
	public static final String TEAM_GREEN_HEALTHBAR_SPRITE_NAME = "greenpixel";
	public static final String TEAM_YELLOW_HEALTHBAR_SPRITE_NAME = "yellowpixel";
	public static final String TEAM_PURPLE_HEALTHBAR_SPRITE_NAME = "purplepixel";
	public static final String HEALTHBAR_BORDER_SPRITE_NAME = "graypixel";
	public static final String EXIT = "Exit";
	public static final String WRONG_CREDENTIALS = "Wrong Credentials";
	public static final String USERNAME = "Username";
	public static final String PASSWORD = "Password";
	public static final String DEFAULT_USERNAME = "Username";
	public static final String DEFAULT_PASSWORD = "Password";
	public static final String LOGIN = "Login";
	public static final String CREATE_ACCOUNT = "Create New Account";
	public static final String CONNECT_FAILED = "Failed to connect to the server.";
	public static final String ERROR_FONT_NAME = "error";
	public static final String ALREADY_LOGGED_IN = "Already logged in";
	public static final String ALREADY_EXISTS = "Account already exists";
	public static final String CHANGE_KEYBINDINGS = "Change Keybindings";
	public static final String MATCHMAKING = "Matchmaking";
	public static final String FIND_GAME = "Find Game";
	public static final String START_GAME = "Start Game";

	// HOTKEYS
	public static final String RIGHT = "right";
	public static final String LEFT = "left";
	public static final String DOWN = "down";
	public static final String UP = "up";
	public static final String DASH = "dash";
	public static final String ACTION_1 = "action1";
	public static final String ACTION_2 = "action2";

	// SIZES
	public static final int GAMESTATE_BUFFER_MAX_SIZE = 200;
	public static final int MAX_DOWNKEYS = 20;
	public static final int LIGHT_POOL_DEFAULT_INIT_SIZE = 50;
	public static final int LIGHT_POOL_DEFAULT_MAX_SIZE = 200;
	public static final int MAX_CONNECTIONS = 100;

	// TIMES
	public static final float DEFAULT_ANIMATION_SPEED = 0.1f;

	// MOVEMENT
	public static final float DIAGONAL_FACTOR = 1.6f;
	public static final float MOVEMENT_SPEED = 5.0f;
	public static final float DASH_SPEED = 150.0f;

	// OFFSETS
	public static final int HP_X_OFFSET = 100;
	public static final int HP_Y_OFFSET = 100;

	// DIMENSIONS
	public static final float VISIBLE_WIDTH = 1000;
	public static final float VISIBLE_HEIGHT = 1000;
	public static final float VISIBLE_MAP_WIDTH = 600;
	public static final float VISIBLE_MAP_HEIGHT = 600;
	public static final float MAP_UNIT_SCALE = 1.0f;
	public static final float DEFAULT_TEXT_BUTTON_WIDTH = 400.0f;
	public static final float DEFAULT_TEXT_BUTTON_HEIGHT = 80.0f;

	// NETWORK
	public static final int UDP_PORT = 54777;
	public static final int TCP_PORT = 54555;
	public static final float SERVER_CLIENT_MERGING_ALLOWED_ERROR_MARGIN = 0.1f;
	public static final String LOCALHOST = "8.8.8.8";
	public static final int LOCALHOST_PORT = 10002;
	public static final int TIMEOUT = 10000;
	public static final int WRITE_BUFFER_SIZE = 8192;
	public static final int OBJECT_BUFFER_SIZE = 8192;

	// DATABASE
	public static final String CONNECTION_URL = "jdbc:sqlite:server/database/test.db";
	public static final String GET_PLAYER_BY_USERNAME = "select * from player where player_name = ?";
	public static final String GET_PLAYER_BY_ID = "select * from player where player_id = ?";
	public static final String INSERT_PLAYER = "INSERT INTO player (player_name, player_password) VALUES(?, ?);";
	public static final String DATABASE_PREP_STATEMENTS_CACHE_SQL_LIMIT = "2048";
	public static final String DATABASE_PREP_STATEMENTS_CACHE_SIZE = "250";
	public static final String DATABASE_CACHE_PREP_STATEMENTS = "true";
	public static final int DATABASE_MAX_POOL_SIZE = 250;
	public static final boolean DATABASE_AUTO_COMMIT = false;

	// COLORS
	public static final Color COLOR_PLAYER_1 = Color.RED;
	public static final Color COLOR_PLAYER_2 = Color.GREEN;
	public static final Color COLOR_PLAYER_3 = Color.BLUE;
	public static final Color COLOR_PLAYER_4 = Color.YELLOW;
	public static final float AMBIENT_RED = 0.0f;
	public static final float AMBIENT_GREEN = 0.0f;
	public static final float AMBIENT_BLUE = 0.0f;
	public static final float AMBIENT_ALPHA = 0.0f;
	public static final float DEFAULT_RED = 0.8f;
	public static final float DEFAULT_GREEN = 0.25f;
	public static final float DEFAULT_BLUE = 0.1f;
	public static final float DEFAULT_ALPHA = 1.0f;
	public static final float DEFAULT_SCALE = 3.0f;

	// ??
	public static final UUID SEARCH_STRING_UUID = UUID.randomUUID();
	public static final DecimalFormat df = new DecimalFormat("0.00");

	// SHADERS
	public static final String SHADER_VERTEX_ALL_BLACK = "shaders/allblack.vs";
	public static final String SHADER_FRAG_ALL_BLACK = "shaders/allblack.fs";

	public static final String LUMINANCE_SHADER = """
		//direction of movement :  0 up, 1 down
		 uniform int direction = 1;
		 //luminance threshold
		 uniform float l_threshold = 0.8;
		//does the movement takes effect above or below luminance threshold ?
		 uniform bool above = false;


		 //Random function borrowed from everywhere
		 float rand(vec2 co){
			return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
		 }


		 // Simplex noise :
		 // Description : Array and textureless GLSL 2D simplex noise function.

		 vec3 mod289(vec3 x) {
			return x - floor(x * (1.0 / 289.0)) * 289.0;
		 }

		 vec2 mod289(vec2 x) {
		   return x - floor(x * (1.0 / 289.0)) * 289.0;
		 }

		 vec3 permute(vec3 x) {
			return mod289(((x*34.0)+1.0)*x);
		 }

		 float snoise(vec2 v){
			const vec4 C = vec4(0.211324865405187,  // (3.0-sqrt(3.0))/6.0
		                   0.366025403784439,  // 0.5*(sqrt(3.0)-1.0)
		                  -0.577350269189626,  // -1.0 + 2.0 * C.x
		                   0.024390243902439); // 1.0 / 41.0
			// First corner
			  vec2 i  = floor(v + dot(v, C.yy) );
			  vec2 x0 = v -   i + dot(i, C.xx);

			// Other corners
			  vec2 i1;
			  //i1.x = step( x0.y, x0.x ); // x0.x > x0.y ? 1.0 : 0.0
			  //i1.y = 1.0 - i1.x;
			  i1 = (x0.x > x0.y) ? vec2(1.0, 0.0) : vec2(0.0, 1.0);
			  // x0 = x0 - 0.0 + 0.0 * C.xx ;
			  // x1 = x0 - i1 + 1.0 * C.xx ;
			  // x2 = x0 - 1.0 + 2.0 * C.xx ;
			  vec4 x12 = x0.xyxy + C.xxzz;
			  x12.xy -= i1;

			// Permutations
			  i = mod289(i); // Avoid truncation effects in permutation
			  vec3 p = permute( permute( i.y + vec3(0.0, i1.y, 1.0 ))
					+ i.x + vec3(0.0, i1.x, 1.0 ));

			  vec3 m = max(0.5 - vec3(dot(x0,x0), dot(x12.xy,x12.xy), dot(x12.zw,x12.zw)), 0.0);
			  m = m*m ;
			  m = m*m ;

			// Gradients: 41 points uniformly over a line, mapped onto a diamond.
			// The ring size 17*17 = 289 is close to a multiple of 41 (41*7 = 287)

			  vec3 x = 2.0 * fract(p * C.www) - 1.0;
			  vec3 h = abs(x) - 0.5;
			  vec3 ox = floor(x + 0.5);
			  vec3 a0 = x - ox;

			// Normalise gradients implicitly by scaling m
			// Approximation of: m *= inversesqrt( a0*a0 + h*h );
			  m *= 1.79284291400159 - 0.85373472095314 * ( a0*a0 + h*h );

			// Compute final noise value at P
			  vec3 g;
			  g.x  = a0.x  * x0.x  + h.x  * x0.y;
			  g.yz = a0.yz * x12.xz + h.yz * x12.yw;
			  return 130.0 * dot(m, g);
		}

		// Simplex noise -- end

		 float luminance(vec4 color){
		   //(0.299*R + 0.587*G + 0.114*B)
		   return color.r*0.299+color.g*0.587+color.b*0.114;
		 }

		vec2 center = vec2(1.0, direction);

		 vec4 transition(vec2 uv) {
		   vec2 p = uv.xy / vec2(1.0).xy;
		   if (progress == 0.0) {

				return getFromColor(p);

			} else if (progress == 1.0) {

				return getToColor(p);

		   } else {
		     float x = progress;
		     float dist = distance(center, p)- progress*exp(snoise(vec2(p.x, 0.0)));
			 float r = x - rand(vec2(p.x, 0.1));
		     float m;
		     if(above){
				m = dist <= r && luminance(getFromColor(p))>l_threshold ? 1.0 : (progress*progress*progress);
			} else {
			 m=dist <= r && luminance(getFromColor(p))<l_threshold ? 1.0 : (progress*progress*progress);
			}

			return mix(getFromColor(p), getToColor(p), m); } };
			""";
}
