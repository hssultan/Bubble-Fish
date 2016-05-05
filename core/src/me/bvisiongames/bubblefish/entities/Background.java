package me.bvisiongames.bubblefish.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import box2dLight.RayHandler;
import me.bvisiongames.bubblefish.screens.MainScreen;
import me.bvisiongames.bubblefish.settings.Assets;
import me.bvisiongames.bubblefish.settings.GeneralMethods;

/**
 * Created by ahzji_000 on 2/20/2016.
 */
public class Background {

    //visibility
    boolean visibility = true;

    //ground sprite
    Sprite ground1, ground2;
    //ground position
    Vector2 groundPos;

    //background backdrop
    Sprite backgroundBackDrop1,
            backgroundBackDrop2;
    //background backdrop position
    Vector2 backgroundBackDropPos;
    //width and height pixels
    float backgroundWidth = 5785,
            backgroundHeight = 1600;

    //ground decorations
    GroundDecorations groundDecorations;

    //bullet supply manager
    BulletSupplyManager bulletSupplyManager;

    /**
     * initiator.
     * @param assets
     */
    public Background(Assets assets, RayHandler rayHandler, World world){

        //ground sprite
        ground1 = new Sprite(assets.getGroundTexture());
        ground1.setSize(MainScreen.camera.viewportWidth, ground1.getHeight());
        ground2 = new Sprite(assets.getGroundTexture());
        ground2.setSize(MainScreen.camera.viewportWidth, ground2.getHeight());
        groundPos = new Vector2(0, -5);

        //add the background backdrop
        backgroundBackDrop1 = new Sprite(assets.getBackgroundWater());
        float heightRatioResize = MainScreen.camera.viewportHeight/(backgroundHeight - ground2.getHeight()/2);
        backgroundBackDrop1.setSize(backgroundWidth*heightRatioResize,
                                    backgroundHeight*heightRatioResize);
        backgroundBackDrop2 = new Sprite(assets.getBackgroundWater());
        backgroundBackDrop2.setSize(backgroundWidth * heightRatioResize,
                                    backgroundHeight * heightRatioResize);
        backgroundBackDropPos = new Vector2(0, ground2.getHeight()/2);

        //ground decorations
        groundDecorations = new GroundDecorations(assets);

        //initiate the bullet supply manager
        bulletSupplyManager = new BulletSupplyManager(assets, world, rayHandler);

    }

    /**
     * update
     */
    public void update(){

        //move the groundpos
        if(groundPos.x <= 0 && groundPos.x > - ground1.getWidth() ){
            groundPos.sub(1, 0);
        }else
        if(groundPos.x <= -ground1.getWidth()){
            groundPos.x = 0;
        }

        //set the position of the ground1
        ground1.setPosition(groundPos.x, groundPos.y);
        //set the position of ground2
        ground2.setPosition(groundPos.x + ground1.getWidth(), groundPos.y);

        //make the background water move
        if(backgroundBackDropPos.x <= 0 && backgroundBackDropPos.x > - backgroundBackDrop1.getWidth() ){
            backgroundBackDropPos.sub(0.2f, 0);
        }else
        if(backgroundBackDropPos.x <= -backgroundBackDrop1.getWidth()){
            backgroundBackDropPos.x = 0;
        }

        //set the position of the background backdrops;
        backgroundBackDrop1.setPosition(backgroundBackDropPos.x, backgroundBackDropPos.y);
        backgroundBackDrop2.setPosition(backgroundBackDropPos.x + backgroundBackDrop1.getWidth(),
                                        backgroundBackDropPos.y);

        //update the ground decorations
        groundDecorations.update();

        //update the bullet supply manager
        bulletSupplyManager.update();

    }

    /**
     * render
     */
    public void render(SpriteBatch batch){

        if(visibility){
            backgroundBackDrop1.draw(batch);
            backgroundBackDrop2.draw(batch);
            ground1.draw(batch);
            ground2.draw(batch);
            //render the ground decorations
            groundDecorations.render(batch);
            //render the bullet supply manager
            bulletSupplyManager.render(batch);
        }

    }

    /**
     * ground decorations class
     */
    public class GroundDecorations{

        //seaweed 1
        Sprite seaweed11, seaweed12;

        //seaweed 2
        Sprite seaweed21;

        //seaweed 3
        Sprite seaweed31;

        //star
        Sprite star1, star2;

        /**
         * initiator
         */
        public GroundDecorations(Assets assets){

            //seaweed 1
            seaweed11 = new Sprite(assets.getBackgroundSeaweed1());
            seaweed11.setPosition(MainScreen.camera.viewportWidth
                                    + GeneralMethods.GenerateRandom(0, (int)(MainScreen.camera.viewportWidth) ),
                    ground1.getHeight()/2 );
            //seaweed 2
            seaweed12 = new Sprite(assets.getBackgroundSeaweed1());
            seaweed12.setPosition(MainScreen.camera.viewportWidth
                            + GeneralMethods.GenerateRandom(0, (int)(MainScreen.camera.viewportWidth) ),
                    ground1.getHeight()/2 );

            //seaweed 2
            seaweed21 = new Sprite(assets.getBackgroundSeaweed2());
            seaweed21.setPosition(MainScreen.camera.viewportWidth
                            + GeneralMethods.GenerateRandom(0, (int) (MainScreen.camera.viewportWidth)),
                    ground1.getHeight() / 2 - 5);
            seaweed21.setSize(seaweed21.getWidth()/2, seaweed21.getHeight()/2);

            //seaweed 3
            seaweed31 = new Sprite(assets.getBackgroundSeaweed3());
            seaweed31.setPosition(MainScreen.camera.viewportWidth
                            + GeneralMethods.GenerateRandom(0, (int) (MainScreen.camera.viewportWidth)),
                    ground1.getHeight() / 2 - 5);

            //star 1
            star1 = new Sprite(assets.getBackgroundStar());
            star1.setPosition(MainScreen.camera.viewportWidth
                            + GeneralMethods.GenerateRandom(0, (int) (MainScreen.camera.viewportWidth)),
                    ground1.getHeight()/4 );
            star1.setSize(star1.getWidth() / 2, star1.getHeight() / 2);

            //star 2
            star2 = new Sprite(assets.getBackgroundStar());
            star2.setPosition(MainScreen.camera.viewportWidth
                            + GeneralMethods.GenerateRandom(0, (int) (MainScreen.camera.viewportWidth)),
                    ground1.getHeight()/4 );
            star2.setSize(star2.getWidth()/2, star2.getHeight()/2);

        }

        /**
         * update
         */
        public void update(){

            //move the seaweed11
            if(seaweed11.getX() < - seaweed11.getWidth() ){
                seaweed11.setPosition(MainScreen.camera.viewportWidth
                                + GeneralMethods.GenerateRandom(0, (int) (MainScreen.camera.viewportWidth)),
                        ground1.getHeight()/2 );
            }else{
                seaweed11.setPosition(seaweed11.getX() - 1, seaweed11.getY());
            }

            //move the seaweed12
            if(seaweed12.getX() < - seaweed12.getWidth() ){
                seaweed12.setPosition(MainScreen.camera.viewportWidth
                                + GeneralMethods.GenerateRandom(0, (int) (MainScreen.camera.viewportWidth)),
                        ground1.getHeight()/2 );
            }else{
                seaweed12.setPosition(seaweed12.getX() - 1, seaweed12.getY());
            }

            //move the seaweed21
            if(seaweed21.getX() < - seaweed21.getWidth() ){
                seaweed21.setPosition(MainScreen.camera.viewportWidth
                                + GeneralMethods.GenerateRandom(0, (int) (MainScreen.camera.viewportWidth)),
                        ground1.getHeight()/2 );
            }else{
                seaweed21.setPosition(seaweed21.getX() - 1, seaweed21.getY());
            }

            //move the seaweed31
            if(seaweed31.getX() < - seaweed31.getWidth() ){
                seaweed31.setPosition(MainScreen.camera.viewportWidth
                                + GeneralMethods.GenerateRandom(0, (int) (MainScreen.camera.viewportWidth)),
                        ground1.getHeight()/2 - 5 );
            }else{
                seaweed31.setPosition(seaweed31.getX() - 1, seaweed31.getY());
            }

            //move the star1
            if(star1.getX() < - star1.getWidth() ){
                star1.setPosition(MainScreen.camera.viewportWidth
                                + GeneralMethods.GenerateRandom(0, (int) (MainScreen.camera.viewportWidth)),
                        ground1.getHeight()/4 );
            }else{
                star1.setPosition(star1.getX() - 1, star1.getY());
            }

            //move the star2
            if(star2.getX() < - star2.getWidth() ){
                star2.setPosition(MainScreen.camera.viewportWidth
                                + GeneralMethods.GenerateRandom(0, (int) (MainScreen.camera.viewportWidth)),
                        ground1.getHeight()/4 );
            }else{
                star2.setPosition(star2.getX() - 1, star2.getY());
            }

        }

        /**
         * render
         */
        public void render(SpriteBatch batch){

            seaweed11.draw(batch);
            seaweed12.draw(batch);
            seaweed21.draw(batch);
            seaweed31.draw(batch);
            star1.draw(batch);
            star2.draw(batch);

        }

    }

}
