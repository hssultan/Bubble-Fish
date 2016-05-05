package me.bvisiongames.bubblefish.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import java.util.List;
import me.bvisiongames.bubblefish.settings.Assets;
import me.bvisiongames.bubblefish.settings.GameInfo;

/**
 * Created by ahzji_000 on 2/29/2016.
 */
public class BulletManager {

    //list of the bullets
    List<Bullet> bullets = new ArrayList<Bullet>();

    //world in box2d
    World world;

    //assets
    Assets assets;

    /**
     * initiator
     */
    public BulletManager(Assets assets, World world){

        this.assets = assets;
        this.world = world;

    }

    /**
     * update
     */
    public void update(){

        //update bullets
        int length = this.bullets.size();
        for(int i = 0; i < length; i++){
            this.bullets.get(i).update();
        }

    }

    /**
     * render
     */
    public void render(SpriteBatch batch){

        //update bullets
        int length = this.bullets.size();
        for(int i = 0; i < length; i++){
            this.bullets.get(i).render(batch);
        }

    }

    /**
     * activate a bullet
     * x and y are in body 2d dimensions
     */
    public void activateBullet(float x, float y){

        //decrement bullets from total bullets
        if(GameInfo.TOTAL_BULLETS > 0){
            GameInfo.TOTAL_BULLETS--;
        }

        //check if there are bullets to resurrect
        int length = this.bullets.size();
        for(int i = 0; i < length; i++){
            if(!bullets.get(i).isAlive){
                this.bullets.get(i).resurrect(x, y);
                return;
            }
        }

        //create a new bullet if there are none in the bullet list that are alive
        Vector2 tmpVec = new Vector2(x, y);
        Bullet tmpBullet = new Bullet(tmpVec);
        tmpBullet.resurrect(tmpVec.x, tmpVec.y);
        this.bullets.add(tmpBullet);

    }

    /**
     * bullet class
     */
    public class Bullet{

        //status of this bullet
        public boolean isAlive = false;

        //status to kill the bullet
        private boolean kill = false;

        //body of bullet
        Body body;

        //bullet effect
        ParticleEffect particleEffect = new ParticleEffect();

        /**
         * initiator
         */
        public Bullet(Vector2 pos){

            //create the body
            // First we create a body definition
            BodyDef bodyDef = new BodyDef();
            // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            // Set our body's starting position in the world
            bodyDef.position.set(pos);

            // Create our body in the world using our body definition
            body = world.createBody(bodyDef);

            // Create a circle shape and set its radius to 6
            CircleShape circle = new CircleShape();
            circle.setRadius(0.4f);

            // Create a fixture definition to apply our shape to
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = circle;
            fixtureDef.density = 0.1f;
            fixtureDef.restitution = 0f;
            fixtureDef.isSensor = true;

            // Create our fixture and attach it to the body
            body.createFixture(fixtureDef);
            body.setUserData(this);
            body.setBullet(true);

            // Remember to dispose of any shapes after you're done with them!
            // BodyDef and FixtureDef don't need disposing, but shapes do.
            circle.dispose();

            particleEffect.load(Gdx.files.internal("effects/bullet"), Gdx.files.internal("objects"));
            particleEffect.setPosition(pos.x* GameInfo.BOX2D_TO_PIXELS, pos.y * GameInfo.BOX2D_TO_PIXELS);
            particleEffect.start();

        }

        /**
         * update
         */
        public void update(){

            if(isAlive){

                if(kill){
                    this.body.setLinearVelocity(0, 0);
                    isAlive = false;
                    kill = false;
                }else{
                    this.body.setLinearVelocity(70, 0);
                    particleEffect.setPosition(body.getPosition().x * GameInfo.BOX2D_TO_PIXELS,
                            body.getPosition().y * GameInfo.BOX2D_TO_PIXELS);
                    particleEffect.update(Gdx.graphics.getDeltaTime());
                }

            }

        }

        /**
         * render
         */
        public void render(SpriteBatch batch){

            if(isAlive){
                particleEffect.draw(batch);
            }

        }

        /**
         * kill this bullet
         */
        public void kill(){
            kill = true;
        }

        /**
         * resurrect
         */
        public void resurrect(float x, float y){
            this.kill = false;
            this.isAlive = true;
            this.body.setTransform(x, y, 0);
            particleEffect.setPosition(x * GameInfo.BOX2D_TO_PIXELS, y *GameInfo.BOX2D_TO_PIXELS);
            particleEffect.reset();
        }

    }

}
