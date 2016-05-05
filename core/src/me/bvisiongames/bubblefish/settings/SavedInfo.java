package me.bvisiongames.bubblefish.settings;

import com.badlogic.gdx.Preferences;

/**
 * Created by ahzji_000 on 2/19/2016.
 */
public class SavedInfo {

    //preference
    Preferences preferences;

    /**
     * initiator.
     */
    public SavedInfo(Preferences preferences){
        this.preferences = preferences;
    }

    //setters
    /**
     * save the highest score.
     * @param score
     */
    public void saveHighestScore(int score){
        this.preferences.putInteger("score", score);
        this.preferences.flush();
    }
    //end of setters


    //getters
    /**
     * get the highest score.
     */
    public int getHighestScore(){
        return this.preferences.getInteger("score", 0);
    }
    //end of getters

}
