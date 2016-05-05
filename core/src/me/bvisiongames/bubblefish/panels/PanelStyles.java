package me.bvisiongames.bubblefish.panels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import me.bvisiongames.bubblefish.settings.Assets;

/**
 * Created by ahzji_000 on 2/19/2016.
 */
public class PanelStyles {

    //normal text button styles
    public TextButton.TextButtonStyle normalTextButtonStyle = new TextButton.TextButtonStyle();

    //sound on/off button style
    public ImageButton.ImageButtonStyle soundBtnStyle = new ImageButton.ImageButtonStyle();

    //test label style
    public Label.LabelStyle testLabelStyle = new Label.LabelStyle();

    //fire text button style
    public TextButton.TextButtonStyle fireTextButtonStyle = new TextButton.TextButtonStyle();

    //arrow up
    public ImageButton.ImageButtonStyle upButton = new ImageButton.ImageButtonStyle();

    //arrow down
    public ImageButton.ImageButtonStyle downButton = new ImageButton.ImageButtonStyle();

    /**
     * initiator.
     * @param assets
     */
    public PanelStyles(Assets assets){

        //set the normal button style
        normalTextButtonStyle.font = assets.getBtnFont();
        normalTextButtonStyle.fontColor = Color.WHITE;
        normalTextButtonStyle.up = new NinePatchDrawable(new NinePatch(assets.getGreenButtonUp(), 5, 5, 5, 5));
        normalTextButtonStyle.down = new NinePatchDrawable(new NinePatch(assets.getGreenButtonDown(), 5, 5, 5, 5));
        normalTextButtonStyle.up.setBottomHeight(12);
        normalTextButtonStyle.up.setTopHeight(12);
        normalTextButtonStyle.up.setLeftWidth(30);
        normalTextButtonStyle.up.setRightWidth(30);
        normalTextButtonStyle.down.setBottomHeight(12);
        normalTextButtonStyle.down.setTopHeight(12);
        normalTextButtonStyle.down.setLeftWidth(30);
        normalTextButtonStyle.down.setRightWidth(30);

        //set the sound on/off button style
        soundBtnStyle.up = new NinePatchDrawable(new NinePatch(assets.getYellowButtonUp(), 5, 5, 5, 5));
        soundBtnStyle.down = new NinePatchDrawable(new NinePatch(assets.getYellowButtonDown(), 5, 5, 5, 5));
        soundBtnStyle.imageChecked = new NinePatchDrawable(new NinePatch(assets.getMute(), 5,5,5,5));
        soundBtnStyle.imageUp = new NinePatchDrawable(new NinePatch(assets.getVolume(), 5,5,5,5));

        //set the label style
        testLabelStyle.font = assets.getBoldArialFont();
        testLabelStyle.fontColor = Color.WHITE;

        //set the fire button
        fireTextButtonStyle.font = assets.getBtnFont();
        fireTextButtonStyle.fontColor = Color.WHITE;
        fireTextButtonStyle.up = new NinePatchDrawable(new NinePatch(assets.getOrangeButtonUp(), 5, 5, 5, 5));
        fireTextButtonStyle.down = new NinePatchDrawable(new NinePatch(assets.getOrangeButtonDown(), 5, 5, 5, 5));
        fireTextButtonStyle.up.setBottomHeight(12);
        fireTextButtonStyle.up.setTopHeight(12);
        fireTextButtonStyle.up.setLeftWidth(30);
        fireTextButtonStyle.up.setRightWidth(30);
        fireTextButtonStyle.down.setBottomHeight(12);
        fireTextButtonStyle.down.setTopHeight(12);
        fireTextButtonStyle.down.setLeftWidth(30);
        fireTextButtonStyle.down.setRightWidth(30);

        //setup the up button
        upButton.imageUp = new NinePatchDrawable(new NinePatch(assets.getArrowButtonUp(), 5, 5, 5, 5));
        upButton.up = new NinePatchDrawable(new NinePatch(assets.getGreenButtonUp(), 5, 5, 5, 5));
        upButton.down = new NinePatchDrawable(new NinePatch(assets.getGreenButtonDown(), 5, 5, 5, 5));

        //setup the down button
        downButton.imageUp = new NinePatchDrawable(new NinePatch(assets.getArrowButtonDown(), 5, 5, 5, 5));
        downButton.down = new NinePatchDrawable(new NinePatch(assets.getGreenButtonDown(), 5, 5, 5, 5));
        downButton.up = new NinePatchDrawable(new NinePatch(assets.getGreenButtonUp(), 5, 5, 5, 5));

    }

}
