package me.bvisiongames.bubblefish.settings;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by ahzji_000 on 2/19/2016.
 */
public class Assets {

    AssetManager assetManager = new AssetManager();

    //this boolean tells whether the assets has been initiated
    private boolean initiated = false;

    //setters
    /**
     * load the main assets for the game.
     */
    public void loadAssets(){

        //load the textures
        assetManager.load(FilesNames.MainFish, Texture.class);
        assetManager.load(FilesNames.BackgroundWater, Texture.class);
        assetManager.load(FilesNames.Ground, Texture.class);
        assetManager.load(FilesNames.Bubble, Texture.class);
        assetManager.load(FilesNames.Grass, Texture.class);
        assetManager.load(FilesNames.SeaWeed1, Texture.class);
        assetManager.load(FilesNames.SeaWeed2, Texture.class);
        assetManager.load(FilesNames.SeaWeed3, Texture.class);
        assetManager.load(FilesNames.Star, Texture.class);

        //load the fonts
        assetManager.load(FilesNames.arialFont, BitmapFont.class);
        assetManager.load(FilesNames.BtnFont, BitmapFont.class);
        assetManager.load(FilesNames.arialBoldFont, BitmapFont.class);

        //load the buttons textures
        assetManager.load(FilesNames.GreenButtonUp, Texture.class);
        assetManager.load(FilesNames.GreenButtonDown, Texture.class);
        assetManager.load(FilesNames.YellowButtonDown, Texture.class);
        assetManager.load(FilesNames.YellowButtonUp, Texture.class);
        assetManager.load(FilesNames.Mute, Texture.class);
        assetManager.load(FilesNames.Volume, Texture.class);
        assetManager.load(FilesNames.OrangeButtonUp, Texture.class);
        assetManager.load(FilesNames.OrangeButtonDown, Texture.class);
        assetManager.load(FilesNames.ArrowDown, Texture.class);
        assetManager.load(FilesNames.ArrowUp, Texture.class);

        //load sounds
        assetManager.load(FilesNames.PopSound, Sound.class);
        assetManager.load(FilesNames.BubbleSound, Sound.class);
        assetManager.load(FilesNames.CoinSound, Sound.class);

        this.initiated = false;

    }
    /**
     * done initiating the assets.
     */
    public void doneInitiating(){
        this.initiated = true;
    }
    //end of setters


    //getters
    /**
     * tells whether the assets has been initiated.
     */
    public boolean wereInitiated(){
        return initiated;
    }
    /**
     * this returns true if the assets are loaded.
     */
    public boolean areLoaded(){
        return assetManager.update()? true : false;
    }
    /**
     * get the main fish texture.
     */
    public Texture getMainFishTexture(){
        return assetManager.get(FilesNames.MainFish, Texture.class);
    }
    /**
     * get arial bitmapFont.
     */
    public BitmapFont getArialBitmapFont(){
        return assetManager.get(FilesNames.arialFont, BitmapFont.class);
    }
    /**
     * get bold arial bold bitmapfont
     */
    public BitmapFont getBoldArialFont(){
        return assetManager.get(FilesNames.arialBoldFont, BitmapFont.class);
    }
    /**
     * get the green button up texture.
     */
    public Texture getGreenButtonUp(){
        return assetManager.get(FilesNames.GreenButtonUp, Texture.class);
    }
    /**
     * get the green button up texture.
     */
    public Texture getGreenButtonDown(){
        return assetManager.get(FilesNames.GreenButtonDown, Texture.class);
    }
    /**
     * get the yellow button up texture.
     */
    public Texture getYellowButtonUp(){
        return assetManager.get(FilesNames.YellowButtonUp, Texture.class);
    }
    /**
     * get the yellow button down texture.
     */
    public Texture getYellowButtonDown(){
        return assetManager.get(FilesNames.YellowButtonDown, Texture.class);
    }
    /**
     * get the orange button down texture.
     */
    public Texture getOrangeButtonDown(){
        return assetManager.get(FilesNames.OrangeButtonDown, Texture.class);
    }
    /**
     * get the orange button up texture.
     */
    public Texture getOrangeButtonUp(){
        return assetManager.get(FilesNames.OrangeButtonUp, Texture.class);
    }
    /**
     * get the arrow button up texture.
     */
    public Texture getArrowButtonUp(){
        return assetManager.get(FilesNames.ArrowUp, Texture.class);
    }
    /**
     * get the arrow button down texture.
     */
    public Texture getArrowButtonDown(){
        return assetManager.get(FilesNames.ArrowDown, Texture.class);
    }
    /**
     * get mute texture button.
     */
    public Texture getMute(){
        return assetManager.get(FilesNames.Mute, Texture.class);
    }
    /**
     * get volume texture button.
     */
    public Texture getVolume(){
        return assetManager.get(FilesNames.Volume, Texture.class);
    }
    /**
     * get arial button bitmapFont
     */
    public BitmapFont getBtnFont(){
        return assetManager.get(FilesNames.BtnFont, BitmapFont.class);
    }
    /**
     * get the background water texture.
     */
    public Texture getBackgroundWater(){
        return assetManager.get(FilesNames.BackgroundWater, Texture.class);
    }
    /**
     * get the ground of the background texture.
     */
    public Texture getGroundTexture(){
        return assetManager.get(FilesNames.Ground, Texture.class);
    }
    /**
     * get the bubble texture
     */
    public Texture getBubbleTexture(){
        return assetManager.get(FilesNames.Bubble, Texture.class);
    }
    /**
     * get the pop sound
     */
    public Sound getPopSound(){
        return assetManager.get(FilesNames.PopSound, Sound.class);
    }
    /**
     * get the bubble sound
     */
    public Sound getBubbleSound(){
        return assetManager.get(FilesNames.BubbleSound, Sound.class);
    }
    /**
     * get the coin sound
     */
    public Sound getCoinSound(){
        return assetManager.get(FilesNames.CoinSound, Sound.class);
    }
    /**
     * get the background grass texture
     */
    public Texture getBackgroundGrass(){
        return assetManager.get(FilesNames.Grass, Texture.class);
    }
    /**
     * get the background seaweed1 texture
     */
    public Texture getBackgroundSeaweed1(){
        return assetManager.get(FilesNames.SeaWeed1, Texture.class);
    }
    /**
     * get the background seaweed2 texture
     */
    public Texture getBackgroundSeaweed2(){
        return assetManager.get(FilesNames.SeaWeed2, Texture.class);
    }
    /**
     * get the background seaweed3 texture
     */
    public Texture getBackgroundSeaweed3(){
        return assetManager.get(FilesNames.SeaWeed3, Texture.class);
    }
    /**
     * get the background star texture
     */
    public Texture getBackgroundStar(){
        return assetManager.get(FilesNames.Star, Texture.class);
    }
    //end of getters

    /**
     * dispose the assets manager.
     */
    public void dispose(){
        assetManager.dispose();
    }

    /**
     * class that contains the files dir.
     */
    public class FilesNames{

        //textures
        public static final String MainFish = "objects/fish.png",
                                    BackgroundWater = "background/bg.png",
                                    Ground = "background/ground.png",
                                    Bubble = "objects/bubble.png",
                                    Grass = "background/grass.png",
                                    SeaWeed1 = "background/seaweedone.png",
                                    SeaWeed2 = "background/seaweedtwo.png",
                                    SeaWeed3 = "background/seaweedthree.png",
                                    Star = "background/star.png";

        //fonts
        public static final String arialFont = "fonts/arial.fnt",
                                    BtnFont = "fonts/normalBtnFont.fnt",
                                    arialBoldFont = "fonts/arialBold.fnt";

        //buttons
        public static final String GreenButtonUp = "buttons/greenup.png",
                                    GreenButtonDown = "buttons/greendown.png",
                                    YellowButtonDown = "buttons/yellowdown.png",
                                    YellowButtonUp = "buttons/yellowup.png",
                                    Mute = "buttons/mute.png",
                                    Volume = "buttons/volume.png",
                                    OrangeButtonDown = "buttons/orangedown.png",
                                    OrangeButtonUp = "buttons/orangeup.png",
                                    ArrowUp = "buttons/up.png",
                                    ArrowDown = "buttons/down.png";

        //sounds
        public static final String PopSound = "sounds/pop.mp3",
                                    BubbleSound = "sounds/bubble.mp3",
                                    CoinSound = "sounds/coin.mp3";

    }

}
