package me.bvisiongames.bubblefish;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.bvisiongames.bubblefish.screens.MainScreen;
import me.bvisiongames.bubblefish.screens.ScreenManager;
import me.bvisiongames.bubblefish.settings.Assets;
import me.bvisiongames.bubblefish.settings.SavedInfo;

public class Game extends ApplicationAdapter {

	public static float WIDTH = 1024,      //1024  (/3)
						HEIGHT = 728;   //812   (/3)

	SpriteBatch batch;

	//saved info
	public static SavedInfo savedInfo;

	@Override
	public void create () {

		//initiate the spritebatch
		batch = new SpriteBatch();

		//initiate the saved info
		this.savedInfo = new SavedInfo(Gdx.app.getPreferences("MINI_DB"));

		//set the screen
		ScreenManager.setScreen(new MainScreen(new Assets()));

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		if(ScreenManager.GetCurrentScreen() != null ){
			ScreenManager.GetCurrentScreen().update();
		}
		if(ScreenManager.GetCurrentScreen() != null ){
			ScreenManager.GetCurrentScreen().render(batch);
		}

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		if(ScreenManager.GetCurrentScreen() != null ){
			ScreenManager.GetCurrentScreen().resize(width, height);
		}

	}

	@Override
	public void pause() {
		super.pause();


		if(ScreenManager.GetCurrentScreen() != null ){
			ScreenManager.GetCurrentScreen().pause();
		}

	}

	@Override
	public void resume() {
		super.resume();

		if(ScreenManager.GetCurrentScreen() != null ){
			ScreenManager.GetCurrentScreen().resume();
		}

	}

	@Override
	public void dispose() {
		super.dispose();


		if(ScreenManager.GetCurrentScreen() != null ){
			ScreenManager.GetCurrentScreen().dispose();
		}

		batch.dispose();

	}
}
