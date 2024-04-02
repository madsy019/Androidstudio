package com.mygdx.game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameScreen implements Screen {
    MyGdxGame game; // Note it’s "MyGdxGame" not "Game"

    Stage stage;
    SpriteBatch batch;

    //Textures for backgrounds and terrain
    Texture background1;
    Texture background2;


    ImageButton exitButton;
    Texture exitTexture;
    TextureRegion exitTextureRegion;
    TextureRegionDrawable exitTexRegionDrawable;


    float xPosition = 0;
    float yPosition = 0;

    float backgroundSpeed = 200;
    float ySpeed = 1000;

    float gravity = 700;

    Player player;


    // constructor to keep a reference to the main Game class
    public GameScreen(MyGdxGame game) {
        this.game = game;
    }
    public void create() {

        Gdx.app.log("GameScreen: ","gameScreen create");
        batch = new SpriteBatch();

        this.background1 = new Texture("Backgrounds/01/Layer01.png");
        this.background2 = new Texture("Backgrounds/01/Layer02.png");


        this.player = new Player(this);


        this.stage = new Stage();

        // 2nd addition image button to exit
        exitTexture = new Texture(Gdx.files.internal("UI/CloseBtn.png"));
        exitTextureRegion = new TextureRegion(exitTexture);
        exitTexRegionDrawable = new TextureRegionDrawable(exitTextureRegion);
        exitButton = new ImageButton(exitTexRegionDrawable); //Set the button up

        exitButton.setPosition(Gdx.graphics.getWidth() /2 - (exitButton.getWidth()/2) -100f, Gdx.graphics.getHeight()- 100f );
        stage.addActor(exitButton);
        Gdx.input.setInputProcessor(stage);
        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                game.setScreen(MyGdxGame.menuScreen);
            }
        });
    }

//    public void update(float f) {
//        float dt =Gdx.graphics.getDeltaTime();
//        this.backgroundX -= this.backgroundSpeed * dt;
//        if (this.backgroundX < -this.background1.getWidth()) this.backgroundX += this.background1.getWidth() -20;
//
//    }
    public void update(float f) {
        float dt = Gdx.graphics.getDeltaTime();

        this.player.update();
        //Move background
        this.xPosition -= (this.backgroundSpeed/3) * dt;

        this.yPosition -= (this.backgroundSpeed/2) * dt;


        if (this.xPosition + this.background1.getWidth() < 0) {
            this.xPosition = 0;
        }
        if(this.yPosition + this.background2.getWidth() < 0){
            this.yPosition = 0;
        }

        if (Gdx.input.isTouched()) {

            if( Gdx.input.getX() < Gdx.graphics.getWidth()/2){

                this.player.jump();


            }


        }


    }


    public void render(float f) {
        this.update(f);
        Gdx.app.log("GameScreen: ","gameScreen render");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        //get the screen width initially
        int screenWidth = Gdx.graphics.getWidth();



        batch.draw(  this.background1, this.xPosition,0);
        batch.draw(  this.background1, this.xPosition + this.background1.getWidth(),0);
        batch.draw(  this.background1,( this.xPosition + this.background1.getWidth()) + + this.background1.getWidth(),0);
        batch.draw(  this.background2, this.yPosition,0);
        batch.draw(  this.background2, this.yPosition + this.background2.getWidth(),0);
        batch.draw(  this.background2, (this.yPosition + this.background2.getWidth()) + this.background2.getWidth(),0);

        this.player.render(batch);

        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }
    @Override
    public void dispose() {
        this.background1.dispose();
        this.background2.dispose();
        exitTexture.dispose();

        this.player.dispose();
    }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void show() {
        Gdx.app.log("GameScreen: ","gameScreen show called");
        create();
    }
    @Override
    public void hide() {
        Gdx.app.log("GameScreen: ","gameScreen hide called");
    }
}