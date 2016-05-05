package me.bvisiongames.bubblefish.panels;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.bvisiongames.bubblefish.Game;
import me.bvisiongames.bubblefish.screens.MainScreen;
import me.bvisiongames.bubblefish.settings.GameState;

/**
 * Created by ahzji_000 on 2/19/2016.
 */
public class MainMenuPanel {

    //main table
    Table table = new Table();

    //score label
    private Label score;

    /**
     * initiator.
     * @param stage
     */
    public MainMenuPanel(Stage stage, TextButton.TextButtonStyle buttonStyle,
                         Label.LabelStyle labelStyle){

        //draw the start button
        TextButton start = new TextButton("Start", buttonStyle);
        //add the listener to the start button
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainScreen.gameState = GameState.PLAYING;
            }
        });

        //draw the label score
        this.score = new Label("Highest Score: "+ Game.savedInfo.getHighestScore(), labelStyle);

        this.table.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        this.table.add(start);
        this.table.row();
        this.table.add(score).padTop(10);

        //add the table to the stage
        stage.addActor(this.table);

        //set visibility based on game state
        if(MainScreen.gameState == GameState.MENU){
            show();
        }else{
            hide();
        }

    }


    //setters
    /**
     * update highest score
     * @param score
     */
    public void updateHighestScoreDisplay(int score){
        this.score.setText("Highest Score: "+score);
    }
    /**
     * show
     */
    public void show(){

        if(!table.isVisible()){
            table.setVisible(true);
        }

    }
    /**
     * hide
     */
    public void hide(){

        if(table.isVisible()){
            table.setVisible(false);
        }

    }
    //end of setters

}
