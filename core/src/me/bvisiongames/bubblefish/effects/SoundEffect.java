package me.bvisiongames.bubblefish.effects;

import com.badlogic.gdx.audio.Sound;
import me.bvisiongames.bubblefish.settings.Assets;
import me.bvisiongames.bubblefish.settings.GameInfo;

/**
 * Created by ahzji_000 on 2/28/2016.
 */
public class SoundEffect {

    //pop sound
    Sound popSound,
    //bubble sound
            bubbleSound,
    //coin sound
            coinSound
    ;

    /**
     * initiator
     */
    public SoundEffect(Assets assets){

        //initiate pop sound
        popSound = assets.getPopSound();

        //initiate bubble sound
        bubbleSound = assets.getBubbleSound();

        //initiate coin sound
        coinSound = assets.getCoinSound();

    }

    //play methods
    /**
     * play pop sound
     */
    public void playPopSound(){
        popSound.play(GameInfo.GAME_SOUND? 1 : 0);
    }
    /**
     * play bubble sound
     */
    public void playBubbleSound(){
        bubbleSound.play(GameInfo.GAME_SOUND? 1 : 0);
    }
    /**
     * play coin sound
     */
    public void playCoinSound(){
        coinSound.play(GameInfo.GAME_SOUND? 1 : 0);
    }
    //end of play methods

}
