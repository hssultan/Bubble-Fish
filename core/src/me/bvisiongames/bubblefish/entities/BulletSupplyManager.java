package me.bvisiongames.bubblefish.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import java.util.List;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import me.bvisiongames.bubblefish.screens.MainScreen;
import me.bvisiongames.bubblefish.settings.Assets;
import me.bvisiongames.bubblefish.settings.GameInfo;
import me.bvisiongames.bubblefish.settings.GameState;
import me.bvisiongames.bubblefish.settings.GeneralMethods;

/**
 * Created by ahzji_000 on 3/8/2016.
 */
public class BulletSupplyManager {

    //asset
    Assets assets;

    //world
    World world;

    //ray handler
    RayHandler rayHandler;

    //list of bullet supply entity
    List<BulletSupplyEntity> bulletSupplyEntities = new ArrayList<BulletSupplyEntity>();

    //count down to send a bullet supply entity
    long frameCount = 0;
    float timeOut = 30000;

    /**
     * initiator
     */
    public BulletSupplyManager(Assets assets, World world, RayHandler rayHandler){

        this.assets = assets;
        this.world = world;
        this.rayHandler = rayHandler;

        this.activateOne();

    }

    /**
     * update
     */
    public void update(){

        if(MainScreen.gameState == GameState.PLAYING){
            if(frameCount > timeOut/(Gdx.graphics.getDeltaTime()*1000) ){
                frameCount = 0;
                //initiate an event after timeOut
                this.activateOne();
            }else{
                frameCount++;
            }
        }

        int length = bulletSupplyEntities.size();
        for(int i = 0; i < length; i++){
            bulletSupplyEntities.get(i).update();
        }

    }

    /**
     * render
     */
    public void render(SpriteBatch batch){

        int length = bulletSupplyEntities.size();
        for(int i = 0; i < length; i++){
            bulletSupplyEntities.get(i).render(batch);
        }

    }

    /**
     * activate a bullet supply entity
     */
    public void activateOne(){

        int length = bulletSupplyEntities.size();
        for(int i = 0; i < length; i++){
            if(!bulletSupplyEntities.get(i).isActive){
                bulletSupplyEntities.get(i).activate();
                return;
            }
        }

        //else add a new bullet supply entity
        BulletSupplyEntity bulletSupplyEntity = new BulletSupplyEntity();
        bulletSupplyEntity.activate();
        bulletSupplyEntities.add(bulletSupplyEntity);

    }

    /**
     * reset
     */
    public void reset(){

        int length = bulletSupplyEntities.size();
        for(int i = 0; i < length; i++){
            bulletSupplyEntities.get(i).deactivate();
        }

        //reset the framecount
        frameCount = 0;

    }

    /**
     * bullet supply entity class
     */
    public class BulletSupplyEntity{

        //is active
        public boolean isActive = false;

        //body
        public Body body;

        //size of the entity
        public float radius = 0.5f;

        //add a bullet point light
        private PointLight sourceLight;
        //glow on/off variable
        private boolean glowOn = false;

        //enable motion
        public boolean isMotionEnabled = true;

        /**
         * initiator
         */
        public BulletSupplyEntity(){

            //build the bullet supply entity
            // First we create a body definition
            BodyDef bodyDef = new BodyDef();
            // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            // Set our body's starting position in the world
            bodyDef.position.set(MainScreen.camera.viewportWidth/GameInfo.BOX2D_TO_PIXELS + radius,
                                GeneralMethods.GenerateRandom(0, (int)(MainScreen.camera.viewportHeight/GameInfo.BOX2D_TO_PIXELS) )
                                );

            // Create our body in the world using our body definition
            body = world.createBody(bodyDef);

            // Create a circle shape and set its radius to 6
            CircleShape circle = new CircleShape();
            circle.setRadius(radius);

            // Create a fixture definition to apply our shape to
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = circle;
            fixtureDef.isSensor = true;

            // Create our fixture and attach it to the body
            //and add an id for this body fixture
            body.createFixture(fixtureDef).setUserData(Sensors.BULLET_SUPPLY_ENTITY);
            body.setUserData(this);

            // Remember to dispose of any shapes after you're done with them!
            // BodyDef and FixtureDef don't need disposing, but shapes do.
            circle.dispose();

            //add the point light
            this.sourceLight = new PointLight(rayHandler, 100, Color.GOLD,
                    15, body.getPosition().x, body.getPosition().y);
            this.sourceLight.attachToBody(body);
            this.sourceLight.setXray(true);

        }

        /**
         * update
         */
        public void update(){

            if(isActive){

                //activate the body if deactivated
                if(!body.isActive()){
                    body.setActive(true);
                }

                //activate the sourcelight if deactivated
                if(!sourceLight.isActive()){
                    sourceLight.setActive(true);
                }

                //make the body move left
                if(isMotionEnabled){
                    body.setLinearVelocity(-5, 0);
                }

                //add the glowing effect
                if(glowOn){
                    if(this.sourceLight.getDistance() > 15){
                        glowOn = false;
                    }else{
                        this.sourceLight.setDistance(this.sourceLight.getDistance() + 1);
                    }
                }else{
                    if(this.sourceLight.getDistance() < 1){
                        glowOn = true;
                    }else{
                        this.sourceLight.setDistance(this.sourceLight.getDistance() - 1);
                    }
                }

            }else{

                //make the body stop moving
                body.setLinearVelocity(0, 0);

                //deactivate the body if activated
                if(body.isActive()){
                    body.setActive(false);
                }

                //deactivate the sourcelight if activated
                if(sourceLight.isActive()){
                    sourceLight.setActive(false);
                }

            }

        }

        /**
         * render
         */
        public void render(SpriteBatch batch){

            if(isActive){

            }

        }

        /**
         * activate the entity
         */
        public void activate(){
            body.setTransform(MainScreen.camera.viewportWidth/GameInfo.BOX2D_TO_PIXELS + 5,
                    GeneralMethods.GenerateRandom(0, (int)(MainScreen.camera.viewportHeight/GameInfo.BOX2D_TO_PIXELS) ),
                    0);
            this.isActive = true;
        }

        /**
         * deactivate the entity
         */
        public void deactivate(){
            this.isActive = false;
        }

    }

}
