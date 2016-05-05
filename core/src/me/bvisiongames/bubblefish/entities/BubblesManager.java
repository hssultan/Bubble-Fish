package me.bvisiongames.bubblefish.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import java.util.List;
import me.bvisiongames.bubblefish.effects.SoundEffect;
import me.bvisiongames.bubblefish.screens.MainScreen;
import me.bvisiongames.bubblefish.settings.Assets;
import me.bvisiongames.bubblefish.settings.CollisionFilter;
import me.bvisiongames.bubblefish.settings.GameInfo;
import me.bvisiongames.bubblefish.settings.GeneralMethods;

/**
 * Created by ahzji_000 on 2/21/2016.
 */
public class BubblesManager {

    //reference to the assets
    Assets assets;
    //reference to the world
    World world;
    //reference to the sound effect
    SoundEffect soundEffect;

    //list of bubbles
    List<Bubble> bubbles = new ArrayList<Bubble>();

    //list of bubble bursting
    List<BubbleBurst> burstingBubbles = new ArrayList<BubbleBurst>();

    //status of the bubble manager
    private boolean state = false;

    //number of active bubbles
    int activeBubbles = 0;
    //max number of bubbles in the game
    int maxBubbles = 6;
    //max game score to activate all the bubbles is 15
    int maxScore = 15;

    /**
     * initiator.
     */
    public BubblesManager(Assets assets, World world, SoundEffect soundEffect){

        //assets
        this.assets = assets;
        //world
        this.world = world;
        //sound effect
        this.soundEffect = soundEffect;

        //start with 1 bubbles that is alive
        Bubble bubble;
        bubble = new Bubble(assets);
        bubble.alive = true;
        this.bubbles.add(bubble);
        this.burstingBubbles.add(new BubbleBurst(assets));
        //set the active bubbles to 1
        this.activeBubbles = 1;

        //add the other bubbles but they are not alive
        for(int i = 0; i < maxBubbles; i++){
            //the bubble
            bubble = new Bubble(assets);
            bubble.kill();
            this.bubbles.add(bubble);
            //add the bursting bubble associated with the bubble
            this.burstingBubbles.add(new BubbleBurst(assets));
        }

    }

    /**
     * update
     */
    public void update(){

        if(this.state){

            //activate bubbles based on game score
            //calculate how much are going to be added
            int numnewBubbles =
                    (maxBubbles *
                    GameInfo.CURRENT_GAME_SCORE
                    )
                    / maxScore;

            //update all bubbles
            int length = this.bubbles.size();
            for (int i = 0; i < length; i++){

                //update all the bubbles
                this.bubbles.get(i).update();

                //update the active bubbles
                if(!this.bubbles.get(i).alive){
                    //activate bubbles if needed
                    if(numnewBubbles > activeBubbles){
                        Gdx.app.log("s","adding a bubble");
                        this.bubbles.get(i).reincarnate();
                        this.bubbles.get(i).resetRound();
                        this.activeBubbles++;
                    }
                }

            }
        }

        //update all bursting bubbles
        int length = this.burstingBubbles.size();
        for (int i = 0; i < length; i++){
            if(!this.burstingBubbles.get(i).particleEffect.isComplete()){
                this.burstingBubbles.get(i).update(Gdx.graphics.getDeltaTime());
            }
        }

    }
    /**
     * render
     * @param batch
     */
    public void render(SpriteBatch batch){

        if(this.state){
            //update all bubbles
            int length = this.bubbles.size();
            for (int i = 0; i < length; i++){
                if(this.bubbles.get(i).alive){
                    this.bubbles.get(i).render(batch);
                }
            }
        }

        //update all bursting bubbles
        int length = this.burstingBubbles.size();
        for (int i = 0; i < length; i++){
            if(!this.burstingBubbles.get(i).particleEffect.isComplete()){
                this.burstingBubbles.get(i).render(batch);
            }
        }

    }

    /**
     * start the bubbles
     */
    public void start(){
        if(this.state != true){
            this.state = true;
        }
    }
    /**
     * shutdown the bubbles
     */
    public void shutdown(){
        if(this.state != false){
            this.state = false;
            this.restartBubblesState();
        }
    }
    /**
     * reset the bubbles states.
     */
    public void restartBubblesState(){

        //update all bubbles
        int length = this.bubbles.size();
        for (int i = 0; i < length; i++){
            //and add a bursting effect
            this.bubbles.get(i).burst();
            //restart the bubbles
            this.bubbles.get(i).restart();
        }
        //activate the first bubble
        if(this.bubbles.size() > 0){
            this.activeBubbles = 1;
            this.bubbles.get(0).alive = true;
        }

    }
    /**
     * activate a bursting bubble
     */
    public void activateBurstingBubble(float x, float y){

        //update all bubbles
        int length = this.burstingBubbles.size();
        for (int i = 0; i < length; i++){
            if(this.burstingBubbles.get(i).particleEffect.isComplete()){
                this.burstingBubbles.get(i).activate(x, y);
                break;
            }
        }

    }

    /**
     * a bubble class.
     */
    public class Bubble{

        //status of this bubble
        public boolean alive = false;
        //status after bursting
        public boolean bursted = true;
        //status of finishing the round
        private boolean isRoundFinished = true;

        //sprite
        private Sprite sprite;
        //original dimensions of the sprite
        private float ORIGINAL_WIDTH = 0, ORIGINAL_HEIGHT = 0;
        //body
        private Body body;

        //linear velocity
        private Vector2 linearVelocity;
        private float angularVelocityRad = 0;

        //bubble properties random generator
        private RandomGeneratorBubble randomGeneratorBubble;

        /**
         * initiator.
         */
        public Bubble(Assets assets){

            //set the bubble ot be alive
            this.alive = true;

            //build the sprite for the bubble
            this.sprite = new Sprite(assets.getBubbleTexture());
            this.ORIGINAL_HEIGHT = this.sprite.getHeight();
            this.ORIGINAL_WIDTH = this.sprite.getWidth();
            //set an initial size of the bubble
            this.sprite.setSize(this.ORIGINAL_WIDTH*0.5f, this.ORIGINAL_HEIGHT*0.5f);
            this.sprite.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);

            //create the random generator
            this.randomGeneratorBubble = new RandomGeneratorBubble(this.sprite);

            //build the bubbles body
            // First we create a body definition
            BodyDef bodyDef = new BodyDef();
            // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            // Set our body's starting position in the world
            bodyDef.position.set(this.randomGeneratorBubble.GenerateRandomStartingPoint());

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
            fixtureDef.restitution = 0.6f; // Make it bounce a little bit
            fixtureDef.isSensor = true;
            fixtureDef.filter.groupIndex = CollisionFilter.NO_COLLISION_ENTITIES;

            // Create our fixture and attach it to the body
            //and add an id for this body fixture
            body.createFixture(fixtureDef).setUserData(BUBBLEIDS.BODY);
            body.setUserData(this);

            // Remember to dispose of any shapes after you're done with them!
            // BodyDef and FixtureDef don't need disposing, but shapes do.
            circle.dispose();

            //add a center fixture for each bubble
            // Create a circle shape and set its radius to 6
            CircleShape center = new CircleShape();
            center.setRadius(5/GameInfo.BOX2D_TO_PIXELS);

            // Create a fixture definition to apply our shape to
            fixtureDef = new FixtureDef();
            fixtureDef.shape = center;
            fixtureDef.isSensor = true;
            fixtureDef.filter.groupIndex = CollisionFilter.NO_COLLISION_ENTITIES;

            // Create our fixture and attach it to the body
            //and add an id for this center fixture
            body.createFixture(fixtureDef).setUserData(BUBBLEIDS.CENTER);

            center.dispose();

            //initialize the linear velocity
            this.linearVelocity = new Vector2(1, 1);

        }

        /**
         * update
         */
        public void update(){

            if(this.alive){
                //reset the bubble if it has went out of the screen
                if(isRoundFinished){
                    //play a bubbling sound
                    soundEffect.playBubbleSound();
                    //randomize the angular velocity
                    this.angularVelocityRad = ((float)GeneralMethods.GenerateRandom(0, 180))*MathUtils.degreesToRadians;
                    //randomize the size of the bubble
                    //get a random percent ratio of the radius of the bubble
                    float ratio = ((float)GeneralMethods.GenerateRandom(20, 50))/100;
                    this.sprite.setSize(this.ORIGINAL_WIDTH * ratio, this.ORIGINAL_HEIGHT * ratio);
                    this.sprite.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
                    this.body.getFixtureList().get(0).getShape().setRadius(sprite.getWidth() / (GameInfo.BOX2D_TO_PIXELS * 2));
                    //generate a random property
                    Vector2 randomPoint = this.randomGeneratorBubble.GenerateRandomStartingPoint();
                    //reset the position of the bubble
                    this.body.setTransform(randomPoint.x, randomPoint.y, 0);
                    //randomize a linear velocity
                    float length = (float)GeneralMethods.GenerateRandom(10, 20);
                    float angle = (float)GeneralMethods.GenerateRandom(this.randomGeneratorBubble.chosenLangle,
                                                                        this.randomGeneratorBubble.chosenUangle);
                    this.linearVelocity.setLength(length);
                    this.linearVelocity.setAngle(angle);
                    //reset the bursting
                    bursted = false;
                    isRoundFinished = false;
                }

                //set the velocity of the bubble
                this.body.setLinearVelocity(this.linearVelocity);
                this.body.setAngularVelocity(this.angularVelocityRad);
            }else{
                //freeze the bubble
                this.body.setAngularVelocity(0);
                this.body.setLinearVelocity(0, 0);
            }

            //set the position of the bubble
            this.sprite.setPosition(
                    this.body.getPosition().x * GameInfo.BOX2D_TO_PIXELS - this.sprite.getWidth()/2,
                    this.body.getPosition().y * GameInfo.BOX2D_TO_PIXELS - this.sprite.getHeight()/2);
            this.sprite.setRotation(this.body.getAngle() * MathUtils.radiansToDegrees);

        }
        /**
         * render
         */
        public void render(SpriteBatch batch){

            if(!bursted){
                this.sprite.draw(batch);
            }

        }

        /**
         * restart the state of this bubble.
         */
        public void restart(){
            this.alive = false;
            this.isRoundFinished = true;
            bursted = false;
        }

        /**
         * reset the round
         */
        public void resetRound(){
            isRoundFinished = true;
        }

        /**
         * burst
         */
        public void burst(){
            if(!this.bursted){
                //initiate a bursting bubble
                BubblesManager.this.activateBurstingBubble(
                        this.body.getPosition().x * GameInfo.BOX2D_TO_PIXELS,
                        this.body.getPosition().y * GameInfo.BOX2D_TO_PIXELS);
            }
            //set the bursted to true
            this.bursted = true;
        }
        /**
         * kill
         */
        public void kill(){
            if(alive){
                this.alive = false;
                //initiate a bursting bubble, if it has not bursted
                if(!bursted){
                    BubblesManager.this.activateBurstingBubble(this.sprite.getX() + this.sprite.getWidth()/2,
                            this.sprite.getY() + this.sprite.getHeight()/2);
                }
            }
        }

        /**
         * reincarnate
         */
        public void reincarnate(){
            this.alive = true;
        }

        /**
         * deactivate the body
         */
        public void deactivate(){
            if(body.isActive()){
                body.setActive(false);
            }
        }

    }

    /**
     * burst class.
     */
    public class BubbleBurst{

        //particle effect
        public ParticleEffect particleEffect = new ParticleEffect();

        /**
         * initiator
         */
        public BubbleBurst(Assets assets){

            particleEffect.load(Gdx.files.internal("effects/bubbleBurst"), Gdx.files.internal("objects"));
            particleEffect.setPosition(0, 0);
            particleEffect.start();

        }

        /**
         * activate
         * @param position
         */
        public void activate(Vector2 position){
            particleEffect.setPosition(position.x, position.y);
        }
        /**
         * activate
         * @param x
         * @param y
         */
        public void activate(float x, float y){
            //play the sound
            soundEffect.playPopSound();
            //set the animation
            particleEffect.setPosition(x, y);
            if(particleEffect.isComplete()){
                particleEffect.reset();
            }
        }

        /**
         * update
         */
        public void update(float delta){
            particleEffect.update(delta);
        }

        /**
         * render
         */
        public void render(SpriteBatch batch){
            particleEffect.draw(batch);
        }

    }

    /**
     * random properties generator class for the bubble
     */
    public class RandomGeneratorBubble{

        //lower angles
        public int lowerLangle = 135, lowerUangle = 175,
        //middle angles
                    //lower 155                        190
                    middleLangle = 155, middleUangle = 190,
        //upper angles
                    //lower 185                     213
                    upperLangle = 185, upperUangle = 213
                ;

        //lower position
        public Vector2
                lowerPos,
        //middle position
                middlePos,
        //upper position
                upperPos;

        //chosen angles
        public int chosenLangle = 0,
                        chosenUangle = 0;

        /**
         * initiator
         */
        public RandomGeneratorBubble(Sprite sprite){

            //lower position
            this.lowerPos = new Vector2(
                    MainScreen.camera.viewportWidth/GameInfo.BOX2D_TO_PIXELS +
                    sprite.getWidth()/(2*GameInfo.BOX2D_TO_PIXELS),
                    -sprite.getWidth()/(2*GameInfo.BOX2D_TO_PIXELS)
            );

            //middle position
            this.middlePos = new Vector2(
                    MainScreen.camera.viewportWidth/GameInfo.BOX2D_TO_PIXELS
                    + sprite.getWidth()/GameInfo.BOX2D_TO_PIXELS,
                    MainScreen.camera.viewportHeight/(GameInfo.BOX2D_TO_PIXELS*2)
                    - sprite.getHeight()/(2*GameInfo.BOX2D_TO_PIXELS)
            );

            //upper position
            this.upperPos = new Vector2(
                    MainScreen.camera.viewportWidth/GameInfo.BOX2D_TO_PIXELS +
                            sprite.getWidth()/(2*GameInfo.BOX2D_TO_PIXELS),
                    MainScreen.camera.viewportHeight/GameInfo.BOX2D_TO_PIXELS
                    + sprite.getHeight()/(2*GameInfo.BOX2D_TO_PIXELS)
            );

        }

        /**
         * generates a randome point to be chosen.
         * and returns a starting position.
         */
        public Vector2 GenerateRandomStartingPoint(){
            byte r = (byte)GeneralMethods.GenerateRandom(0, 2);

            if(r == 0){
                this.chosenLangle = this.lowerLangle;
                this.chosenUangle = this.lowerUangle;
                return this.lowerPos;
            }else
            if(r == 1){
                this.chosenLangle = this.middleLangle;
                this.chosenUangle = this.middleUangle;
                return this.middlePos;
            }else
            if(r == 2){
                this.chosenLangle = this.upperLangle;
                this.chosenUangle = this.upperUangle;
                return this.upperPos;
            }

            this.chosenLangle = this.lowerLangle;
            this.chosenUangle = this.lowerUangle;
            return this.lowerPos;
        }

    }

    /**
     * bubble enums
     */
    public enum BUBBLEIDS{
        CENTER, BODY
    }

}
