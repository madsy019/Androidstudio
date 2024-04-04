package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen {
    //

    MyGdxGame game; // Note it’s "MyGdxGame" not "Game"

    // Images and Backgrounds
    Texture title;
    Texture mainbackground;
    Texture terrain;

    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    //Image button created for the play the game
    ImageButton playbutton;
    Texture playTexture;
    TextureRegion playTextureRegion;
    TextureRegionDrawable playTexRegionDrawable;

    //Image button created for exit game
    ImageButton exitButton;
    Texture exitTexture;
    TextureRegion exitTextureRegion;
    TextureRegionDrawable exitTexRegionDrawable;

//
    OrthographicCamera camera;
    Viewport viewport;
    int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();
//


    // constructor to keep a reference to the main Game class
    public MenuScreen(MyGdxGame game) {
        this.game = game;
    }



    public void create() {

        Gdx.app.log("MenuScreen: ","menuScreen create");



        float screenRatio = 1000.0f / screenHeight;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, screenWidth * screenRatio, screenHeight * screenRatio);
        this.viewport = new FitViewport(screenWidth * screenRatio, screenHeight * screenRatio);


        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("gui/uiskin.json"));
        stage = new Stage();

        //Adding textures for main background, tile and terrain
        mainbackground = new Texture("UI/bg01.png");
        title = new Texture("UI/Tittle.png");
        terrain = new Texture("Terain/3.png");


        //Creating Play button
        playTexture = new Texture(Gdx.files.internal("UI/PlayBtn.png"));
        playTextureRegion = new TextureRegion(playTexture);
        playTexRegionDrawable = new TextureRegionDrawable(playTextureRegion);
        playbutton = new ImageButton(playTexRegionDrawable); //Set the button up

        playbutton.setPosition(Gdx.graphics.getWidth() /2 - (playbutton.getWidth()/2) -200, Gdx.graphics.getHeight()/2 - 300f);
        stage.addActor(playbutton);
        Gdx.input.setInputProcessor(stage);

        //When pressed takes player to game screen
        playbutton.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                game.setScreen(MyGdxGame.gameScreen);
            }
        });

        // Creating exit Button
        exitTexture = new Texture(Gdx.files.internal("UI/CloseBtn.png"));
        exitTextureRegion = new TextureRegion(exitTexture);
        exitTexRegionDrawable = new TextureRegionDrawable(exitTextureRegion);
        exitButton = new ImageButton(exitTexRegionDrawable); //Set the button up

        exitButton.setPosition(Gdx.graphics.getWidth() /2 - (exitButton.getWidth()/2) , Gdx.graphics.getHeight()/2 - 300f);
        stage.addActor(exitButton);

        //When pressed, exits the game
        Gdx.input.setInputProcessor(stage);
        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });

    }

    public void render(float f) {

        this.camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        //Draw main background, title and terrain
        batch.draw(mainbackground, 0, 0);//draw background
////////////Ask about drawing the title
        batch.draw(title,0 +  (Gdx.graphics.getWidth()/2)/2  , 0 + (Gdx.graphics.getHeight()/2)/2 );
        batch.draw(terrain , 0,0);
        batch.draw(terrain , 0 + this.terrain.getWidth(),0);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {

        //Dispose of all the textures
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