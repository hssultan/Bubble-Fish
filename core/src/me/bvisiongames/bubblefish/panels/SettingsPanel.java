package me.bvisiongames.bubblefish.panels;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import me.bvisiongames.bubblefish.screens.MainScreen;
import me.bvisiongames.bubblefish.settings.GameInfo;
import me.bvisiongames.bubblefish.settings.GameState;

/**
 * Created by ahzji_000 on 2/20/2016.
 */
public class SettingsPanel {

    //main table
    Table table = new Table();

    /**
     * initiator.
     * @param stage
     */
    public SettingsPanel(Stage stage, ImageButton.ImageButtonStyle buttonStyle){

        //draw the start button
        final ImageButton start = new ImageButton(buttonStyle);
        //add the listener to the start button
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(!start.isChecked()){
                    //sound on
                    GameInfo.GAME_SOUND = true;
                }else{
                    //sound off
                    GameInfo.GAME_SOUND = false;
                }

            }
        });

        this.table.align(Align.right);
        this.table.setSize(stage.getViewport().getWorldWidth(),
                            stage.getViewport().getWorldHeight() / 8);
        this.table.setPosition(0, stage.getViewport().getWorldHeight() - this.table.getHeight());
        this.table.add(start).padRight(10);

        //add the table to the stage
        stage.addActor(this.table);

        //set visibility based on game state
        if(MainScreen.gameState != GameState.GAME_OVER){
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
