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

    MyGdxGame game; // Reference to the main game object to access global properties and methods
    Stage stage; // Stage for managing UI elements like buttons
    SpriteBatch batch; // For rendering textures and sprites on the screen

    int terrainHeight = 111;
    float minY = 0; // Minimum Y value for enemy spawning
    float maxY = Gdx.graphics.getHeight() - 400; // Maximum Y value for enemy spawning

    // Textures for backgrounds, terrain, game over screen, and player lives
    Texture background1;
    Texture background2;
    Texture terrian;
    Texture gameOver;
    Texture playerLives;

    // UI components
    ImageButton resetButton;
    Texture resetTexture;
    TextureRegion resetTextureRegion;
    TextureRegionDrawable resetTexRegionDrawable;

    // Pause button and its related textures
    Texture pauseTexture;
    TextureRegion pauseTextureRegion;
    TextureRegionDrawable pauseTexRegionDrawable;
    ImageButton pauseButton;

    //Exit button and its textures
    ImageButton exitButton;
    Texture exitTexture;
    TextureRegion exitTextureRegion;
    TextureRegionDrawable exitTexRegionDrawable;



    boolean isPaused = false; // Flag for toggling game pause

    // Background position and speed variables
    float xPosition = 0;
    float yPosition = 0;
    float zPosition = 0;
    float backgroundSpeed = 100;

    Player player; // Player character
    Array<Bullets> bullets; // Active bullets in the game
    Array<Enemy> enemyArr; // Active enemies in the game

    private long lastAppear; // Last time an enemy appeared
    private int enemyDelay = 200; // Time between enemy spawns

    private long lastFired; // Last time a bullet was fired
    private int shootDelay = 450; // Time between shots

    int screenWidth = Gdx.graphics.getWidth(); // Screen width

    ShapeRenderer shapeRenderer; // For debugging, can draw shapes like rectangles around sprites


    // constructor to keep a reference to the main Game class
    // Constructor initializes player, bullets, and enemies
    public GameScreen(MyGdxGame game) {

        this.game = game;

        bullets = new Array<Bullets>();

        enemyArr = new Array<Enemy>();
    }

    // Initialize game elements like textures, player, enemies, UI components
    public void create() {

        Gdx.app.log("GameScreen: ", "gameScreen create");
        batch = new SpriteBatch();

        this.shapeRenderer = new ShapeRenderer();

        // Select a random background
        int randomBackground = 2;
        int minValue = 1;
        int maxValue = 4;
        randomBackground = MathUtils.random(minValue, maxValue);


        this.background1 = new Texture("Backgrounds/" +"0"+ randomBackground + "/Layer01.png");

        this.background2 = new Texture("Backgrounds/" +"0"+ randomBackground + "/Layer02.png");

        this.terrian = new Texture("Terain/3.png");

        this.gameOver = new Texture("UI/gameover.png");

        this.playerLives = new Texture("UI/hudplayerIco.png"); //load player lives texture

        this.player = new Player(this);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        //Create stage and set up reset , pause and exit to menu
        this.stage = new Stage();
        setupResetButton();
        setupPauseButton();
        setUpExitButton();


    }

    public void update(float f) {
        float dt = Gdx.graphics.getDeltaTime();

        if (isPaused) {
            return; // Skip update logic if the game is paused
        }

        this.player.update();

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

                if ( this.player.getplayerState() == Player.State.ALIVE) {
                    //enable to jump if player is alive
                    if(this.player.hasFuel()== true){
                        this.player.jump();
                        this.player.fuel -= this.player.fuelConsumptionPerJump *dt; // Consume fuel
                    }

                }

            }
            //shoots bullets
            if (System.currentTimeMillis() > lastFired + this.shootDelay) {
                //Check to see if right half of the screen is touched
                if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                    bullets.add(new Bullets((int) (this.player.x - 140), this.player.y + 45));
                    this.lastFired = System.currentTimeMillis();
                }
            }
        }else{
            //if player is not jumping recharage fuel
            this.player.fuel = Math.min(this.player.fuel + this.player.fuelRechargeRate * dt, this.player.maxFuel);
        }

            //generate enemies at random time intervals
            if(System.currentTimeMillis() > lastAppear + this.enemyDelay){

                float randomEnemyDelay = MathUtils.random(1500, 2000);
                enemyDelay = (int) randomEnemyDelay;
                float randomY = MathUtils.random(minY, maxY);

                enemyArr.add(new Enemy(this, new Vector2(Gdx.graphics.getWidth() + 300, randomY)));
                this.lastAppear = System.currentTimeMillis();
            }

        //Check for the enemy in the array and update them
        for (Enemy enemy: enemyArr) {
            enemy.update();

            //Remove enemy from array if they are not alive
            if (!enemy.isAlive()) {
                enemyArr.removeValue(enemy, true);
            }
            //handling collision of both enemy and player if they knock
            if (this.player.getBoundingBox().overlaps(enemy.getBoundingBox())) {
                enemy.handleCollision();
                player.handleCollision();
            }

        }

        //update bullets
        for (Bullets bullet : bullets){
            bullet.update();

            //Remove bullets from array if they are not live
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

        //check if the player is dead
        if ( this.player.getplayerState() == Player.State.DEAD) {

            //And if players lives are all gone end game and go to main menu
            if(this.player.lives == 0){
                game.setScreen(MyGdxGame.menuScreen);
            }

        }

    }

    public void render(float f) {
        this.update(f);
        Gdx.app.log("GameScreen: ", "gameScreen render");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (!isPaused) {
            this.update(f); // Only update if the game is not paused
        }


        //Draw all the backgrounds, and terrain
        drawBackground(this.background1, this.xPosition);
        drawBackground(this.background2, this.yPosition);
        drawBackground(this.terrian, this.yPosition);

        //Check for players lives and render respective number of lives on screen
        if(this.player.lives == 3){
            renderPlayerLives(playerLives,1500);
            renderPlayerLives(playerLives,1600);
            renderPlayerLives(playerLives,1700);
        } else if (this.player.lives == 2) {
            renderPlayerLives(playerLives,1500);
            renderPlayerLives(playerLives,1600);
        }else if (this.player.lives == 1) {
            renderPlayerLives(playerLives,1500);
        }


        if ( this.player.getplayerState() == Player.State.DYING) {
            //if the player is dying and there are no more lives left. render game over logo
            if(this.player.lives < 1){
                batch.draw(this.gameOver,  450, -50);
            }

            //If there are lives left render the player again
            if(this.player.y == -4000 ){
                this.player.render(this.batch);
            }

        }

        //render the bullets on the screen from the array.
        for (Bullets bullet : bullets) {
            bullet.render(batch);
        }

        //Render enemies on the Screen
        for (Enemy enemy : enemyArr) {
            enemy.render(batch);
        }

        //Render player
        this.player.render(this.batch);



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

        //disposing all the textures that were created
        this.background1.dispose();
        this.background2.dispose();
        this.terrian.dispose();
        resetTexture.dispose();
        this.player.dispose();
        this.pauseTexture.dispose();
        this.gameOver.dispose();
        this.playerLives.dispose();
        this.exitTexture.dispose();



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

    //method to draw the background on the game screen
    public void drawBackground(Texture texture, Float position){
        batch.draw(texture, position, 0);
        batch.draw(texture, position + texture.getWidth(), 0);
        batch.draw(texture, (position + texture.getWidth()) + + texture.getWidth(), 0);
    }


    //method to render the player on the game screen
    public void renderPlayerLives(Texture player, float xValue){
        batch.draw(player, xValue, Gdx.graphics.getHeight()-120);
    }

    public void setupResetButton(){

        //Setting up rest button
        resetTexture = new Texture(Gdx.files.internal("UI/RestartBtn.png"));
        resetTextureRegion = new TextureRegion(resetTexture);
        resetTexRegionDrawable = new TextureRegionDrawable(resetTextureRegion);
        resetButton = new ImageButton(resetTexRegionDrawable); //Set the button up

        resetButton.setPosition(Gdx.graphics.getWidth() / 2 - (resetButton.getWidth() / 2) - 120f, Gdx.graphics.getHeight() - 100f);
        stage.addActor(resetButton);
        Gdx.input.setInputProcessor(stage);
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.create();
                game.setScreen(MyGdxGame.gameScreen);
            }
        });

    }



    public void setupPauseButton(){
        // Initialize pause button
        pauseTexture = new Texture(Gdx.files.internal("UI/PauseBtn.png")); // Ensure you have a PauseBtn.png in your assets
        pauseTextureRegion = new TextureRegion(pauseTexture);
        pauseTexRegionDrawable = new TextureRegionDrawable(pauseTextureRegion);
        pauseButton = new ImageButton(pauseTexRegionDrawable); // Set the button up

        pauseButton.setPosition(Gdx.graphics.getWidth() / 2 - (pauseButton.getWidth() / 2) +20 , Gdx.graphics.getHeight() - 100f);
        stage.addActor(pauseButton);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = !isPaused; // Toggle pause state
            }
        });

    }

    public void setUpExitButton(){

        exitTexture = new Texture(Gdx.files.internal("UI/CloseBtn.png")); // Ensure you have a PauseBtn.png in your assets
        exitTextureRegion = new TextureRegion(exitTexture);
        exitTexRegionDrawable = new TextureRegionDrawable(exitTextureRegion);
        exitButton = new ImageButton(exitTexRegionDrawable); // Set the button up

        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - (exitButton.getWidth() / 2) -260 , Gdx.graphics.getHeight() - 100f);
        stage.addActor(exitButton);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(MyGdxGame.menuScreen);
            }
        });
    }

}