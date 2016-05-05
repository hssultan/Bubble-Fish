package me.bvisiongames.bubblefish.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import box2dLight.RayHandler;
import me.bvisiongames.bubblefish.screens.MainScreen;
import me.bvisiongames.bubblefish.settings.Assets;
import me.bvisiongames.bubblefish.settings.GameInfo;
import me.bvisiongames.bubblefish.settings.GameState;

/**
 * Created by ahzji_000 on 2/19/2016.
 */
public class MainFish {

    //power ups instance
    public PowerUps powerUps;

    //fish properties
    Sprite sprite;
    Body body;
    boolean visibility = true;

    //fish state
    FishControl fishControlState = FishControl.BACK_TO_BEGIN;

    //starting position in pixels
    Vector2 startinPosition;

    //has been reincarnated and ready to play
    boolean hasBeenReincarnated = true;

    /**
     * initiator.
     */
    public MainFish(Assets assets, World world, RayHandler rayHandler){

        //sprite setup
        sprite = new Sprite(assets.getMainFishTexture());
        sprite.setSize(sprite.getWidth() / 2.5f, sprite.getHeight() / 2.5f);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

        //set the point positions of the fish like starting
        startinPosition = new Vector2(100, MainScreen.camera.viewportHeight - sprite.getHeight()*1.5f);

        //setup the body
        this.setupBody(world);

        //initiate the power ups
        this.powerUps = new PowerUps(world);

    }

    /**
     * setup the body
     */
    private void setupBody(World world){

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(startinPosition.x/GameInfo.BOX2D_TO_PIXELS, startinPosition.y/GameInfo.BOX2D_TO_PIXELS);

        // Create our body in the world using our body definition
        body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(sprite.getWidth()/(GameInfo.BOX2D_TO_PIXELS*2));

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0f;

        // Create our fixture and attach it to the body
        body.createFixture(fixtureDef);
        body.setUserData(this);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

        //also add a body for the scoring
        // First we create a body definition
        BodyDef scoreDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        scoreDef.type = BodyDef.BodyType.KinematicBody;
        // Set our body's starting position in the world
        scoreDef.position.set(0, 0);

        // Create our body in the world using our body definition
        Body lineBody = world.createBody(scoreDef);

        // Create a circle shape and set its radius to 6
        ChainShape line = new ChainShape();
        line.createChain(
                new float[]{
                    body.getPosition().x, MainScreen.camera.viewportHeight/GameInfo.BOX2D_TO_PIXELS,
                    body.getPosition().x, 0
                }
        );

        // Create a fixture definition to apply our shape to
        FixtureDef lineFDef = new FixtureDef();
        lineFDef.shape = line;
        lineFDef.isSensor = true;

        // Create our fixture and attach it to the body
        lineBody.createFixture(lineFDef).setUserData(FISHSENSORS.SCORING);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        line.dispose();

    }

    /**
     * show
     */
    public void show(){
        if(!visibility){
            visibility = true;
            body.setActive(true);
        }
    }
    /**
     * hide
     */
    public void hide(){
        if(visibility){
            visibility = false;
            body.setActive(false);
        }
    }

    /**
     * update
     */
    public void update(){

        //apply forces on the body based on the state of the fish begin controlled
        switch (fishControlState){
            case BACK_TO_BEGIN:
                body.setLinearVelocity(0, 0);
                body.setTransform(startinPosition.x/GameInfo.BOX2D_TO_PIXELS, startinPosition.y/GameInfo.BOX2D_TO_PIXELS, 0);
                break;
            case DEAD:
                if(MainScreen.gameState != GameState.GAME_OVER){
                    MainScreen.gameState = GameState.GAME_OVER;
                    hasBeenReincarnated = false;
                }
                break;
            case PLAYER_CONTROL:
                if(!hasBeenReincarnated){
                    body.setLinearVelocity(0, 0);
                    body.setTransform(startinPosition.x/GameInfo.BOX2D_TO_PIXELS, startinPosition.y/GameInfo.BOX2D_TO_PIXELS, 0);
                    hasBeenReincarnated = true;
                }
                break;
        }

        //update the position of the fish same as the body
        sprite.setPosition(body.getPosition().x * GameInfo.BOX2D_TO_PIXELS - sprite.getWidth() / 2,
                body.getPosition().y * GameInfo.BOX2D_TO_PIXELS - sprite.getHeight() / 2);

        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

        powerUps.update();

    }

    /**
     * move up
     */
    public void jump(){
        if(MainScreen.gameState == GameState.PLAYING){
            body.setAwake(true);
            body.applyLinearImpulse(0, 30, body.getPosition().x, body.getPosition().y, false);
        }
    }
    /**
     * move down
     */
    public void jumpDown(){
        if(MainScreen.gameState == GameState.PLAYING){
            body.setAwake(true);
            body.applyLinearImpulse(0, -30, body.getPosition().x, body.getPosition().y, false);
        }
    }

    /**
     * stop moving
     */
    public void seizeMove(){
        if(MainScreen.gameState == GameState.PLAYING){
            body.setAwake(true);
            body.setLinearVelocity(0, 0);
        }
    }

    /**
     * reincarnate
     */
    public void reincarnate(){
        if(this.fishControlState != FishControl.PLAYER_CONTROL){
            this.fishControlState = FishControl.PLAYER_CONTROL;
        }
    }

    /**
     * reincarnate into beginnning position
     */
    public void reincarnateBeginPosition(){
        if(this.fishControlState != FishControl.BACK_TO_BEGIN){
            this.fishControlState = FishControl.BACK_TO_BEGIN;
        }
    }

    /**
     * kill
     */
    public void kill(){
        if(this.fishControlState != FishControl.DEAD){
            this.fishControlState = FishControl.DEAD;
        }
    }

    /**
     * render
     */
    public void render(SpriteBatch batch){

        if(visibility){
            sprite.draw(batch);
            powerUps.render(batch);
        }

    }

    /**
     * Power ups class
     */
    public class PowerUps{

        //power up for speed
        private boolean speed = false;
        //speed effect
        ParticleEffect speedEffect = new ParticleEffect();

        /**
         * initiator
         */
        public PowerUps(World world){

            //initiate the speed effect
            this.speedEffect.load(Gdx.files.internal("effects/speedEffect"),Gdx.files.internal("objects"));
            this.speedEffect.start();
            this.speedEffect.getEmitters().first().getScale().setHigh(sprite.getHeight() + 10);

        }

        //setters
        /**
         * activate the speed power up
         */
        public void activateSpeed(){
            if(!this.speed){
                this.speed = true;
            }
        }
        /**
         * deactivate the speed power up
         */
        public void deactivateSpeed(){
            if(this.speed){
                this.speed = false;
            }
        }
        //end of setters

        /**
         * update
         */
        public void update(){

            if(speed){
                this.speedEffect.update(Gdx.graphics.getDeltaTime());
                this.speedEffect.setPosition(sprite.getX() + sprite.getWidth()/2,
                                        sprite.getY() + sprite.getHeight()/2);
            }

        }

        /**
         * render
         */
        public void render(SpriteBatch batch){

            if(speed){
                this.speedEffect.draw(batch);
            }

        }

    }

    /**
     * enum control of the fish
     */
    private enum FishControl{
        PLAYER_CONTROL, BACK_TO_BEGIN, DEAD
    }

    /**
     * main fish sensors
     */
    public enum FISHSENSORS{
        SCORING
    }

}
