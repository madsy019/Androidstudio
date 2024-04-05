package com.mygdx.game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class GameScreen implements Screen {

    MyGdxGame game; // Note it’s "MyGdxGame" not "Game"
    Stage stage;
    SpriteBatch batch;

    int terrainHeight = 111;
    float minY = terrainHeight;
    float maxY =  Gdx.graphics.getHeight() - 400;

    //Textures for backgrounds and terrain
    Texture background1;
    Texture background2;

    Texture terrian;


    ImageButton exitButton;
    Texture exitTexture;
    TextureRegion exitTextureRegion;
    TextureRegionDrawable exitTexRegionDrawable;


    float xPosition = 0;
    float yPosition = 0;

    float zPosition = 0;

    float backgroundSpeed = 200;


    Player player;

    Array<Bullets> bullets;

    Array<Enemy> enemyArr;

    private long lastAppear;


    private int enemyDelay = 200;

    private long lastFired;
    private int shootDelay = 450;

    ShapeRenderer shapeRenderer;


    /*
    Bullets[] bullets = new Bullets[20];

    for (int i = 0; i < bullets.size; i++) {
      if (bullets[i] == null || bullets[i].isDead()) {
        bullets[i] = new Bullets();
        break;
      }
    }

    for (int i = 0; i < bullets.size; i++) {
      if (bullets[i] != null || !bullets[i].isDead()) {
        bullets[i].update();
      }
    }

     */




    // constructor to keep a reference to the main Game class
    public GameScreen(MyGdxGame game) {

        this.game = game;

        bullets = new Array<Bullets>();

        enemyArr = new Array<Enemy>();
    }
    public void create() {

        Gdx.app.log("GameScreen: ", "gameScreen create");
        batch = new SpriteBatch();

        this.shapeRenderer = new ShapeRenderer();

        int randomBackground = 2;
        int minValue = 1;
        int maxValue = 4;
        randomBackground = MathUtils.random(minValue, maxValue);



        this.background1 = new Texture("Backgrounds/" +"0"+ randomBackground + "/Layer01.png");

        this.background2 = new Texture("Backgrounds/" +"0"+ randomBackground + "/Layer02.png");

        this.terrian = new Texture("Terain/3.png");

        this.player = new Player(this);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();


//        this.enemy = new Enemy(this, new Vector2(screenWidth + 100, 0));


        this.stage = new Stage();

        // 2nd addition image button to exit
        exitTexture = new Texture(Gdx.files.internal("UI/CloseBtn.png"));
        exitTextureRegion = new TextureRegion(exitTexture);
        exitTexRegionDrawable = new TextureRegionDrawable(exitTextureRegion);
        exitButton = new ImageButton(exitTexRegionDrawable); //Set the button up

        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - (exitButton.getWidth() / 2) - 100f, Gdx.graphics.getHeight() - 100f);
        stage.addActor(exitButton);
        Gdx.input.setInputProcessor(stage);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(MyGdxGame.menuScreen);
            }
        });
    }

    public void update(float f) {
        float dt = Gdx.graphics.getDeltaTime();

        this.player.update();

//        this.enemy.update();
//
//        if (this.player.getBoundingBox().overlaps(this.enemy.getBoundingBox())) {
//            this.enemy.handleCollision();
//        }


        //Move background
        this.xPosition -= (this.backgroundSpeed/3) * dt;

        this.yPosition -= (this.backgroundSpeed/2) * dt;

        this.zPosition -= (this.backgroundSpeed) * dt;


        if (this.xPosition + this.background1.getWidth() < 0) {
            this.xPosition = 0;
        }
        if(this.yPosition + this.background2.getWidth() < 0){
            this.yPosition = 0;
        }

        if(this.zPosition + this.terrian.getWidth() < 0){
            this.zPosition = 0;
        }

        //Check to see if the screen is being touched
        if (Gdx.input.isTouched()) {

            //Check to see if left half of the screen is touched.
            //If yes then perform jump action on the player
            if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {

                this.player.jump();

            }

            //shoots bullets
            if (System.currentTimeMillis() > lastFired + this.shootDelay) {
                //Check to see if right half of the screen is touched
                if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                    bullets.add(new Bullets((int) (this.player.x - 140), this.player.y + 45));
                    this.lastFired = System.currentTimeMillis();
                }
            }
        }


            //generate enemies
            if(System.currentTimeMillis() > lastAppear + this.enemyDelay){
                float randomEnemyDelay = MathUtils.random(1500, 2000);
                enemyDelay = (int) randomEnemyDelay;
                float randomY = MathUtils.random(minY, maxY);
                enemyArr.add(new Enemy(this, new Vector2(Gdx.graphics.getWidth() + 300, randomY)));
                this.lastAppear = System.currentTimeMillis();
            }

        for (Enemy enemy: enemyArr) {
            enemy.update();

            if (!enemy.isAlive()) {
                enemyArr.removeValue(enemy, true);
            }

            if (this.player.getBoundingBox().overlaps(enemy.getBoundingBox())) {
                enemy.handleCollision();
            }
        }

        //update bullets
        for (Bullets bullet : bullets){
            bullet.update();

            if (!bullet.isAlive()) {
                bullets.removeValue(bullet, true);
            }

            //check for bullets
            for (Enemy enemy: enemyArr){
                if (bullet.getBoundingBox().overlaps(enemy.getBoundingBox())) {
                    enemy.handleCollision();
                }
            }
        }

    }

    public void render(float f) {
        this.update(f);
        Gdx.app.log("GameScreen: ", "gameScreen render");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        //get the screen width initially
        int screenWidth = Gdx.graphics.getWidth();


        batch.draw(this.background1, this.xPosition, 0);
        batch.draw(this.background1, this.xPosition + this.background1.getWidth(), 0);
        batch.draw(this.background1, (this.xPosition + this.background1.getWidth()) + +this.background1.getWidth(), 0);
        batch.draw(this.background2, this.yPosition, 0);
        batch.draw(this.background2, this.yPosition + this.background2.getWidth(), 0);
        batch.draw(this.background2, (this.yPosition + this.background2.getWidth()) + this.background2.getWidth(), 0);
        batch.draw(this.terrian, this.zPosition, 0);
        batch.draw(this.terrian, this.zPosition + this.terrian.getWidth(), 0);
        batch.draw(this.terrian, (this.zPosition + this.terrian.getWidth()) + +this.terrian.getWidth(), 0);


        //render the bullets on the screen from the array.
        for (Bullets bullet : bullets) {
            bullet.render(batch);
        }

        for (Enemy enemy : enemyArr) {
            enemy.render(batch);
            //Render player
            this.player.render(this.batch);
//
//            this.enemy.render(this.batch);
        }
            batch.end();

            //Begin of shape renderer
//            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//
//            this.shapeRenderer.setColor(Color.RED);
//
//            if (this.player.getBoundingBox() != null) {
//                this.shapeRenderer.rect(this.player.getBoundingBox().x, this.player.getBoundingBox().y,
//                        this.player.getBoundingBox().width, this.player.getBoundingBox().height);
//            }
//
//            for (Enemy currentEnemy : this.enemyArr) {
//
//                if (currentEnemy.getBoundingBox() != null) {
//                    this.shapeRenderer.rect(currentEnemy.getBoundingBox().x, currentEnemy.getBoundingBox().y,
//                            currentEnemy.getBoundingBox().width, currentEnemy.getBoundingBox().height);
//                }
//            }
//
//            for (Bullets bullet : bullets) {
//                if (bullet.getBoundingBox() != null) {
//                    this.shapeRenderer.rect(bullet.getBoundingBox().x, bullet.getBoundingBox().y,
//                            bullet.getBoundingBox().width, bullet.getBoundingBox().height);
//                }
//
//            }
//
//
//            this.shapeRenderer.end();


            stage.act(Gdx.graphics.getDeltaTime());

            stage.draw();

    }
    @Override
    public void dispose() {

        this.background1.dispose();
        this.background2.dispose();
        this.terrian.dispose();
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