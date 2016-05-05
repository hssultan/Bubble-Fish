package me.bvisiongames.bubblefish.settings;

/**
 * Created by ahzji_000 on 2/22/2016.
 */
public class GeneralMethods {

    /**
     * returns a random number with a range.
     * @param min
     * minimum integer number
     * @param max
     * maximum integer number
     */
    public synchronized static int GenerateRandom(int min, int max){
        int generalRandomRange = (max - min) + 1;
        return (int)(Math.random() * generalRandomRange) + min;
    }

}
