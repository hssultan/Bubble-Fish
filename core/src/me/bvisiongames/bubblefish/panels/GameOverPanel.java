package me.bvisiongames.bubblefish.panels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.bvisiongames.bubblefish.Game;
import me.bvisiongames.bubblefish.screens.MainScreen;
import me.bvisiongames.bubblefish.settings.GameInfo;
import me.bvisiongames.bubblefish.settings.GameState;

/**
 * Created by ahzji_000 on 2/23/2016.
 */
public class GameOverPanel {

    //main table
    Table table = new Table();

    //score label
    private Label currentScore,
                    highScore;

    MoveToAction moveToAction = new MoveToAction();

    /**
     * initiator.
     * @param stage
     */
    public GameOverPanel(Stage stage, TextButton.TextButtonStyle buttonStyle,
                         Label.LabelStyle labelStyle){

        //move to action
        moveToAction.setPosition(0, 0);
        moveToAction.setInterpolation(new Interpolation.SwingOut(1));
        moveToAction.setDuration(1f);

        //draw the restart button
        TextButton restart = new TextButton("Restart", buttonStyle);
        //add the listener to the restart button
        restart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainScreen.gameState = GameState.PLAYING;
            }
        });

        //draw the exit button
        TextButton exit = new TextButton("Exit", buttonStyle);
        //add the listener to the restart button
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //save the current to high score if the current is higher
        if(GameInfo.CURRENT_GAME_SCORE > GameInfo.HIGHEST_GAME_SCORE){
            GameInfo.HIGHEST_GAME_SCORE = GameInfo.CURRENT_GAME_SCORE;
            Game.savedInfo.saveHighestScore(GameInfo.CURRENT_GAME_SCORE);
        }

        //draw the label score
        this.currentScore = new Label("Current Score: "+ GameInfo.CURRENT_GAME_SCORE, labelStyle);
        this.highScore = new Label("Highest Score: "+GameInfo.HIGHEST_GAME_SCORE, labelStyle);

        this.table.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        this.table.add(currentScore).colspan(2);
        this.table.row();
        this.table.add(highScore).padTop(10).colspan(2);
        this.table.row();
        this.table.add(restart).padTop(20).padRight(10);
        this.table.add(exit).padTop(20).padLeft(10);

        //add the table to the stage
        stage.addActor(this.table);

        //set visibility based on game state
        if(MainScreen.gameState == GameState.GAME_OVER){
            show();
        }else{
            hide();
        }

    }


    //setters
    /**
     * show
     */
    public void show(){

        if(!table.isVisible()){

            moveToAction.restart();
            table.addAction(moveToAction);
            table.setVisible(true);

            //save the current to high score if the current is higher
            if(GameInfo.CURRENT_GAME_SCORE > GameInfo.HIGHEST_GAME_SCORE){
                GameInfo.HIGHEST_GAME_SCORE = GameInfo.CURRENT_GAME_SCORE;
                Game.savedInfo.saveHighestScore(GameInfo.CURRENT_GAME_SCORE);
            }

            this.currentScore.setText("Current Score: "+ GameInfo.CURRENT_GAME_SCORE);
            this.highScore.setText("Highest Score: "+GameInfo.HIGHEST_GAME_SCORE);

        }

    }
    /**
     * hide
     */
    public void hide(){

        if(table.isVisible()){
            table.setVisible(false);
            table.setPosition(0, -MainScreen.camera.viewportHeight);
        }

    }
    //end of setters

}
