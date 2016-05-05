package me.bvisiongames.bubblefish.settings;

/**
 * Created by ahzji_000 on 2/20/2016.
 */
public class GameInfo {

    //pixels to world box2d
    public static final float BOX2D_TO_PIXELS = 20;

    //current score of the player in the current round of the game
    public static int CURRENT_GAME_SCORE = 0,
    //highest score of the player in the game
                        HIGHEST_GAME_SCORE = 0;

    //total number of bullets
    public static int TOTAL_BULLETS = 10;

    //total deaths
    public static int TOTAL_DEATHS = 0;

    //game sound
    public static boolean GAME_SOUND = true;

}
