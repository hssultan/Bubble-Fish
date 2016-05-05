package me.bvisiongames.bubblefish.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import me.bvisiongames.bubblefish.screens.MainScreen;
import me.bvisiongames.bubblefish.settings.CollisionFilter;
import me.bvisiongames.bubblefish.settings.GameInfo;

/**
 * Created by ahzji_000 on 2/24/2016.
 */
public class MapBounderies {



    /**
     * initiator
     */
    public MapBounderies(World world, Stage stage){

        //build the sensors for the bubbles bounderies
        setupBubblesBounderies(world);

        //build the bounderies for the main fish
        setupMainFishBounderies(world, stage);

    }

    /**
     * setup the bounderies for the bubbles
     */
    private void setupBubblesBounderies(World world){
        //build a boundary
        //build the bubbles body
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set our body's starting position in the world
        bodyDef.position.set(0, 0);

        // Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        ChainShape chain = new ChainShape();
        chain.createChain(new float[]{
                //first point top
                MainScreen.camera.viewportWidth/(2*GameInfo.BOX2D_TO_PIXELS),
                MainScreen.camera.viewportHeight/(0.5f*GameInfo.BOX2D_TO_PIXELS),
                //second point
                -7, MainScreen.camera.viewportHeight/GameInfo.BOX2D_TO_PIXELS + 7,
                //third point
                -7, -7,
                //last point
                MainScreen.camera.viewportWidth/(2*GameInfo.BOX2D_TO_PIXELS),
                -MainScreen.camera.viewportHeight/(0.5f*GameInfo.BOX2D_TO_PIXELS)
        });

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = chain;
        fixtureDef.isSensor = true;

        // Create our fixture and attach it to the body
        //and add an id for this body fixture
        body.createFixture(fixtureDef).setUserData(Sensors.MAP_BOUNDERIES);
        body.setUserData(this);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        chain.dispose();
    }

    /**
     * setup the bounderies for the main fish
     */
    private void setupMainFishBounderies(World world, Stage stage){

        //build a boundary
        //build the bubbles body
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set our body's starting position in the world
        bodyDef.position.set(0, 0);

        // Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        ChainShape chain = new ChainShape();
        chain.createChain(new float[]{
                (MainScreen.camera.viewportWidth/stage.getCamera().viewportWidth) * 180/GameInfo.BOX2D_TO_PIXELS,
                (MainScreen.camera.viewportHeight/stage.getCamera().viewportHeight) * 120/GameInfo.BOX2D_TO_PIXELS,
                (MainScreen.camera.viewportWidth/stage.getCamera().viewportWidth) * 180/GameInfo.BOX2D_TO_PIXELS, 0,

                MainScreen.camera.viewportWidth/GameInfo.BOX2D_TO_PIXELS, 0,
                MainScreen.camera.viewportWidth/GameInfo.BOX2D_TO_PIXELS, MainScreen.camera.viewportHeight/GameInfo.BOX2D_TO_PIXELS,
                0, MainScreen.camera.viewportHeight/GameInfo.BOX2D_TO_PIXELS,

                0, (MainScreen.camera.viewportHeight/stage.getCamera().viewportHeight) * 120/GameInfo.BOX2D_TO_PIXELS,
                (MainScreen.camera.viewportWidth/stage.getCamera().viewportWidth) * 180/GameInfo.BOX2D_TO_PIXELS,
                (MainScreen.camera.viewportHeight/stage.getCamera().viewportHeight) * 120/GameInfo.BOX2D_TO_PIXELS
        });

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = chain;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 0.6f;
        fixtureDef.friction = 0.7f;
        fixtureDef.filter.groupIndex = CollisionFilter.NO_COLLISION_ENTITIES;

        // Create our fixture and attach it to the body
        //and add an id for this body fixture
        body.createFixture(fixtureDef).setUserData(Sensors.MAIN_FISH_BOUNDERIES);
        body.setUserData(this);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        chain.dispose();

    }

}
