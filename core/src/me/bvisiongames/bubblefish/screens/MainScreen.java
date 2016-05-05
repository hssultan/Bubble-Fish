package me.bvisiongames.bubblefish.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import box2dLight.RayHandler;
import me.bvisiongames.bubblefish.Game;
import me.bvisiongames.bubblefish.effects.SoundEffect;
import me.bvisiongames.bubblefish.entities.EntityManager;
import me.bvisiongames.bubblefish.panels.GameOverPanel;
import me.bvisiongames.bubblefish.panels.MainMenuPanel;
import me.bvisiongames.bubblefish.panels.PanelStyles;
import me.bvisiongames.bubblefish.panels.SettingsPanel;
import me.bvisiongames.bubblefish.settings.Assets;
import me.bvisiongames.bubblefish.settings.GameInfo;
import me.bvisiongames.bubblefish.settings.GameState;

/**
 * Created by ahzji_000 on 2/19/2016.
 */
public class MainScreen extends Screen implements GestureDetector.GestureListener{

    //camera
    public static OrthographicCamera camera;

    //assets holder
    Assets assets;

    //world
    World world;

    //box2dlight
    RayHandler rayHandler;

    //box2d debugger camera
    OrthographicCamera box2dCamera;
    //world debugger
    Box2DDebugRenderer box2DDebugRenderer;

    //entity manager
    EntityManager entityManager;

    //main menu panel
    MainMenuPanel mainMenuPanel;

    //settings panel
    SettingsPanel settingsPanel;

    //game over panel
    GameOverPanel gameOverPanel;

    //panel styles
    PanelStyles panelStyles;

    //sound effects
    SoundEffect soundEffect;

    //stage
    Stage stage;

    //game state
    public static GameState gameState;

    /**
     * initiator
     */
    public MainScreen(Assets assets){

        //initiate the world
        world = new World(new Vector2(0, 0), true);

        //initiate the box2dlight
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(1f);

        //initiate the camera
        camera = new OrthographicCamera(Game.WIDTH,
                Game.WIDTH * ((float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth()) );
        //readjust the position of the camera so the center is at the bottom left corner of the screen
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, camera.position.z);

        //initiate the box2d camera
        this.box2dCamera = new OrthographicCamera(Game.WIDTH,
                Game.WIDTH * ((float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth()) );

        //initiate the stage
        this.stage = new Stage(new ExtendViewport(Game.WIDTH/1.5f, Game.HEIGHT/1.5f));

        //initiate the box2d debugger
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        //set the assets
        this.assets = assets;

        //set the game state
        this.gameState = GameState.MENU;

        //set the gesture input listener
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(this));
        multiplexer.addProcessor(this.stage);
        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void create() {

        //set the scores
        GameInfo.CURRENT_GAME_SCORE = 0;
        GameInfo.HIGHEST_GAME_SCORE = Game.savedInfo.getHighestScore();

        //load the assets
        this.assets.loadAssets();

    }

    @Override
    public void update() {

        //update camera
        camera.update();
        box2dCamera.update();
        stage.getCamera().update();

        //update the box2dlight
        rayHandler.update();

        //act the stage
        this.stage.act();

        //state of the game
        //first check whether the assets has been loaded
        if(assets.wereInitiated()){
            switch (gameState){
                case GAME_OVER:
                    //hide the main menu
                    mainMenuPanel.hide();
                    settingsPanel.show();
                    entityManager.mainFish.kill();
                    gameOverPanel.show();
                    entityManager.gameOver();
                    //reset the current score
                    GameInfo.CURRENT_GAME_SCORE = 0;
                    break;
                case PAUSED:
                    //hide the main menu
                    mainMenuPanel.hide();
                    settingsPanel.show();
                    break;
                case RESUMED:
                    //hide the main menu
                    mainMenuPanel.hide();
                    settingsPanel.show();
                    break;
                case PLAYING:
                    entityManager.start();
                    //hide the main menu
                    mainMenuPanel.hide();
                    settingsPanel.show();
                    entityManager.mainFish.reincarnate();
                    //step the world
                    world.step(1 / 60f, 6, 2);
                    //hide the game over
                    gameOverPanel.hide();
                    break;
                case MENU:
                    //show the main menu
                    mainMenuPanel.show();
                    settingsPanel.show();
                    entityManager.mainFish.reincarnateBeginPosition();
                    gameOverPanel.hide();
                    break;
            }
        }

        //update the entity manager
        if(entityManager != null)
            entityManager.update();

    }

    @Override
    public void render(SpriteBatch sb) {

        //project the batch to the camera dimensions
        sb.setProjectionMatrix(camera.combined);

        sb.begin();

        if(assets.areLoaded()){

            //initiate all the objects before drawing them
            if(!assets.wereInitiated()){

                //initiate the sound effects
                soundEffect = new SoundEffect(assets);

                //initiate the panel styles
                panelStyles = new PanelStyles(this.assets);

                //initiate the main menu panel
                mainMenuPanel = new MainMenuPanel(stage, panelStyles.normalTextButtonStyle, panelStyles.testLabelStyle);

                //initiate the settings panel
                settingsPanel = new SettingsPanel(stage, panelStyles.soundBtnStyle);

                //initiate the game over panel
                gameOverPanel = new GameOverPanel(stage, panelStyles.normalTextButtonStyle, panelStyles.testLabelStyle);

                //initiate the objects below
                entityManager = new EntityManager(assets, world, rayHandler, soundEffect, panelStyles, stage);

                //end of objects initiation

                //disable the initiation
                assets.doneInitiating();
            }

            //draw all the objects below
            entityManager.render(sb);

        }

        sb.end();

        //resize the dimensions of the box2d camera
        this.box2dCamera.position.set(camera.position);
        this.box2dCamera.zoom = camera.zoom;
        this.box2dCamera.combined.scl(GameInfo.BOX2D_TO_PIXELS);

        //project the rayhandler to the camera dimensions
        rayHandler.setCombinedMatrix(box2dCamera);

        //render the rayhandler
        rayHandler.render();

        //project the box2d debugger
        //this.box2DDebugRenderer.render(world, box2dCamera.combined);

        //render the stage
        this.stage.draw();

    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width, height);

    }

    @Override
    public void dispose() {

        //dispose assets
        assets.dispose();

        //dispose the world and box2dlight
        world.dispose();
        //rayHandler.dispose();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        if(entityManager != null)
            entityManager.touchDown(x, y, pointer, button);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if(entityManager != null)
            entityManager.tap(x, y, count, button);
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        if(entityManager != null)
            entityManager.longPress(x, y);
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if(entityManager != null)
            entityManager.fling(velocityX, velocityY, button);
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if(entityManager != null)
            entityManager.pan(x, y, deltaX, deltaY);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        if(entityManager != null)
            entityManager.panStop(x, y, pointer, button);
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        if(entityManager != null)
            entityManager.zoom(initialDistance, distance);
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        if(entityManager != null)
            entityManager.pinch(initialPointer1, initialPointer2, pointer1, pointer2);
        return false;
    }
}
