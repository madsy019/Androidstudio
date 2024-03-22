package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {

    //Organizing the code in MenuScreen.java

    // Images and Backgrounds
    Texture title;
    Texture mainbackground;

    Texture terrain;

    MyGdxGame game; // Note it’s "MyGdxGame" not "Game"
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    //for the use of play image button
    ImageButton playbutton;
    Texture playTexture;
    TextureRegion playTextureRegion;
    TextureRegionDrawable playTexRegionDrawable;

    //for the use of exit image button
    ImageButton exitButton;
    Texture exitTexture;
    TextureRegion exitTextureRegion;
    TextureRegionDrawable exitTexRegionDrawable;





    // constructor to keep a reference to the main Game class
    public MenuScreen(MyGdxGame game) {
        this.game = game;
    }



    public void create() {
        Gdx.app.log("MenuScreen: ","menuScreen create");
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("gui/uiskin.json"));
        stage = new Stage();
        mainbackground = new Texture("UI/bg01.png");
        title = new Texture("UI/Tittle.png");
        terrain = new Texture("Terain/3.png");


        // 2nd addition image button to play
        playTexture = new Texture(Gdx.files.internal("UI/PlayBtn.png"));
        playTextureRegion = new TextureRegion(playTexture);
        playTexRegionDrawable = new TextureRegionDrawable(playTextureRegion);
        playbutton = new ImageButton(playTexRegionDrawable); //Set the button up

        playbutton.setPosition(Gdx.graphics.getWidth() /2 - (playbutton.getWidth()/2) -200, Gdx.graphics.getHeight()/2 - 300f);
        stage.addActor(playbutton);
        Gdx.input.setInputProcessor(stage);
        playbutton.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                game.setScreen(MyGdxGame.gameScreen);
            }
        });

        // 2nd addition image button to exit
        exitTexture = new Texture(Gdx.files.internal("UI/CloseBtn.png"));
        exitTextureRegion = new TextureRegion(exitTexture);
        exitTexRegionDrawable = new TextureRegionDrawable(exitTextureRegion);
        exitButton = new ImageButton(exitTexRegionDrawable); //Set the button up

        exitButton.setPosition(Gdx.graphics.getWidth() /2 - (exitButton.getWidth()/2) , Gdx.graphics.getHeight()/2 - 300f);
        stage.addActor(exitButton);
        Gdx.input.setInputProcessor(stage);
        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });

//        

    }

    public void render(float f) {

        batch.begin();

        batch.draw(mainbackground, 0, 0);//draw background
        batch.draw(title,500f , Gdx.graphics.getHeight()/2 -50f );
        batch.draw(terrain, 0,0);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {
        title.dispose();
        mainbackground.dispose();
        playTexture.dispose();
        exitTexture.dispose();
        terrain.dispose();

    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen: ","menuScreen show called");

        create();
    }

    @Override
    public void hide() {
        Gdx.app.log("MenuScreen: ","menuScreen hide called");
    }
}