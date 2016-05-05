package me.bvisiongames.bubblefish.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import net.dermetfan.gdx.physics.box2d.ContactAdapter;
import box2dLight.RayHandler;
import me.bvisiongames.bubblefish.controllers.ControllerManager;
import me.bvisiongames.bubblefish.effects.SoundEffect;
import me.bvisiongames.bubblefish.panels.PanelStyles;
import me.bvisiongames.bubblefish.settings.Assets;
import me.bvisiongames.bubblefish.settings.GameInfo;

/**
 * Created by ahzji_000 on 2/19/2016.
 */
public class EntityManager {

    //main fish
    public MainFish mainFish;

    //background
    private Background background;

    //add the bubbles manager
    private BubblesManager bubblesManager;

    //bullet manager
    public BulletManager bulletManager;

    //controller manager
    ControllerManager controllerManager;

    //sound effect
    SoundEffect soundEffect;

    /**
     * initiator.
     */
    public EntityManager(Assets assets, World world, RayHandler rayHandler, final SoundEffect soundEffect,
                         PanelStyles panelStyles, Stage stage){

        //build a map bounderies
        new MapBounderies(world, stage);

        //initiate the sound effect
        this.soundEffect = soundEffect;

        //initiate the main fish
        mainFish = new MainFish(assets, world, rayHandler);

        //background
        background = new Background(assets, rayHandler, world);

        //initiate the bubble manager
        this.bubblesManager = new BubblesManager(assets, world, soundEffect);
        this.bubblesManager.start();

        //initiate the bullet manager
        this.bulletManager = new BulletManager(assets, world);

        //add a controller manager
        this.controllerManager = new ControllerManager(stage, panelStyles, new ControllerManager.ControllersInterface() {
            @Override
            public void fire(float x, float y) {
                bulletManager.activateBullet(x, y);
                controllerManager.totalBullets.setText("Bullets: "+ GameInfo.TOTAL_BULLETS);
            }
        });

        //add a listener to the fire button
        this.controllerManager.fire.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(GameInfo.TOTAL_BULLETS > 0){
                    controllerManager.controllersInterface.fire(mainFish.body.getPosition().x + 2,
                            mainFish.body.getPosition().y);
                }
            }
        });

        //add the up and down arrow buttons
        this.controllerManager.upArrow.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainFish.jump();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                mainFish.seizeMove();
            }
        });
        this.controllerManager.downArrow.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainFish.jumpDown();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                mainFish.seizeMove();
            }
        });

        //contact listener for bodies
        world.setContactListener(new ContactAdapter(){
            @Override
            public void beginContact(Contact contact) {

                //check collision with the bubbles
                CollisionDetector.checkBubbleMainFishCollision(contact);

                //check scoring
                CollisionDetector.checkBubblePass(contact, soundEffect);

                //check boundery collision with bubbles
                CollisionDetector.checkBubblesRound(contact);

                //check bullet to bubble collision
                CollisionDetector.checkBulletBubble(contact, soundEffect, controllerManager);

                //check bullet supply to main fish collision
                CollisionDetector.checkBulletSupplyEntityMainFishCollision(contact, controllerManager.totalBullets);

                //check collision of bullet supply with the map bounderies
                CollisionDetector.checkBulletSupplyEntityWallEndingCollision(contact);

            }
        });

    }

    /**
     * update the entities
     */
    public void update(){

        //update the background
        background.update();

        //update the main fish
        mainFish.update();

        //update the bubble manager
        bubblesManager.update();

        //update bullet manager
        bulletManager.update();

    }
    /**
     * render the entities.
     */
    public void render(SpriteBatch batch){

        //render the background
        background.render(batch);

        //render the main fish
        mainFish.render(batch);

        //render bullet manager
        bulletManager.render(batch);

        //render the bubble manager
        bubblesManager.render(batch);

    }

    /**
     * start the game
     */
    //tmp variables for the start method
    private boolean BulletDisplaySet = false;
    public void start(){

        //start the bubble manager
        bubblesManager.start();

        //show the controllers
        controllerManager.show();

        //reset the total bullets to 10
        if(!BulletDisplaySet){
            GameInfo.TOTAL_BULLETS = 10;
            controllerManager.totalBullets.setText("Bullets: "+GameInfo.TOTAL_BULLETS);
            BulletDisplaySet = true;
        }

    }

    /**
     * game over game
     */
    public void gameOver(){

        //shutdown the bubbles
        bubblesManager.shutdown();

        //hide the controllers
        controllerManager.hide();
        //reset the score to zero
        controllerManager.score.setText("Score: 0");

        //reset the bullet supply manager
        background.bulletSupplyManager.reset();

        //set the bullet list boolean to false
        BulletDisplaySet = false;

    }

    //listeners
    public void touchDown(float x, float y, int pointer, int button) {

    }
    public void tap(float x, float y, int count, int button) {

    }
    public void longPress(float x, float y) {

    }
    public void fling(float velocityX, float velocityY, int button) {

    }
    public void pan(float x, float y, float deltaX, float deltaY) {

    }
    public void panStop(float x, float y, int pointer, int button) {

    }
    public void zoom(float initialDistance, float distance) {

    }
    public void pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {

    }
    //end of listeners

}
