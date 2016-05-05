package me.bvisiongames.bubblefish.controllers;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import me.bvisiongames.bubblefish.panels.PanelStyles;
import me.bvisiongames.bubblefish.screens.MainScreen;
import me.bvisiongames.bubblefish.settings.GameInfo;
import me.bvisiongames.bubblefish.settings.GameState;

/**
 * Created by ahzji_000 on 2/28/2016.
 */
public class ControllerManager {

    //controller interface
    public ControllersInterface controllersInterface;

    //fire button
    public TextButton fire;

    //up and down arrow
    public ImageButton upArrow,
                        downArrow;

    //score display
    public Label score;

    //total bullets displayed
    public Label totalBullets;

    /**
     * initiator
     */
    public ControllerManager(Stage stage,PanelStyles panelStyles, ControllersInterface controllersInterface){

        this.controllersInterface = controllersInterface;

        //add a fire controller button
        fire = new TextButton("Fire", panelStyles.fireTextButtonStyle);
        fire.setSize(fire.getWidth()*1.5f, fire.getHeight());
        fire.setPosition(stage.getWidth() - fire.getWidth() - 10,
                fire.getHeight() + 10);

        //add the up and down arrow
        upArrow = new ImageButton(panelStyles.upButton);
        downArrow = new ImageButton(panelStyles.downButton);
        upArrow.setSize(100, 50);
        downArrow.setSize(100, 50);
        //upArrow.setSize(upArrow.getWidth()*1.5f, upArrow.getHeight());
        //downArrow.setSize(downArrow.getWidth()*1.5f, downArrow.getHeight());
        downArrow.setPosition(20, 10);
        upArrow.setPosition(20 + downArrow.getWidth()/2 - upArrow.getWidth()/2,
                            downArrow.getHeight() + 20 );

        //add the score label
        this.score = new Label("Score: "+ GameInfo.CURRENT_GAME_SCORE, panelStyles.testLabelStyle);
        this.score.setPosition(
                stage.getWidth()/2 - this.score.getWidth()/2,
                stage.getHeight() - this.score.getHeight() - 5);

        //add the total bullets label
        this.totalBullets = new Label("Bullets: "+GameInfo.TOTAL_BULLETS, panelStyles.testLabelStyle);
        this.totalBullets.setPosition(
                stage.getWidth()/2 - this.totalBullets.getWidth()/2,
                stage.getHeight() - this.score.getHeight() - 5 - this.totalBullets.getHeight()
        );

        //add the actors
        stage.addActor(fire);
        stage.addActor(upArrow);
        stage.addActor(downArrow);
        stage.addActor(this.score);
        stage.addActor(this.totalBullets);

        //if there is a game then show the controllers
        if(MainScreen.gameState == GameState.PLAYING){
            show();
        }else{
            hide();
        }

    }

    /**
     * hide controllers
     */
    public void hide(){
        if(fire.isVisible())
            fire.setVisible(false);
        if(upArrow.isVisible())
            upArrow.setVisible(false);
        if(downArrow.isVisible())
            downArrow.setVisible(false);
        if(this.score.isVisible())
            this.score.setVisible(false);
        if(this.totalBullets.isVisible())
            this.totalBullets.setVisible(false);
    }

    /**
     * show controllers
     */
    public void show(){
        if(!fire.isVisible()){
            fire.setVisible(true);
        }
        if(!upArrow.isVisible()){
            upArrow.setVisible(true);
        }
        if(!downArrow.isVisible()){
            downArrow.setVisible(true);
        }
        if(!this.score.isVisible()){
            this.score.setVisible(true);
        }
        if(!this.totalBullets.isVisible()){
            this.totalBullets.setVisible(true);
        }
    }

    /**
     * interface
     */
    public interface ControllersInterface{
        void fire(float x, float y);
    }

}
