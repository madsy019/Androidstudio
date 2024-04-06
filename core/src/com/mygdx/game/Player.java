package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class Player implements CollidableObject{


    MyGdxGame game;
    float dyinRate;



    enum State {ALIVE, DYING, DEAD}
    private State playerState;


    //Got the height from measuring the terrain
    int terrainHeight = 111;

    Texture[] texture = new Texture[8];
    Texture[] smoke = new Texture[4];
    Texture[] dying = new Texture[4];






    float frame = 0;

    float smokeRate = 0;


    float x = 0;
    float y = 0;

    float ySpeed = 500;

    float gravity = 200;

    float heightCorrection = 27;

    float backgroundSpeed = 100;


    public Player(GameScreen game) {

        // As the player is moving adding it to an array of Textures
        for (int i = 0 ; i < 8; i++) {
            this.texture[i] = new Texture("players/3/walk/" + i + ".png");
        }
        // Texture for smoke
        for (int i = 0 ; i < 4; i++) {
            this.smoke[i] = new Texture("players/smoke/" + i + ".png");
        }

        // Texture for smoke
        for (int i = 0 ; i < 4; i++) {
            this.dying[i] = new Texture("players/3/die/" + i + ".png");
        }

        //Assign enemy state as alive
        this.playerState = State.ALIVE;

        this.y = + terrainHeight;
    }

    public void update() {

        float dt = Gdx.graphics.getDeltaTime();

        this.ySpeed -= gravity * dt;

        this.y += ySpeed * dt;

        if (this.playerState == State.ALIVE) {

            //landing the player on top of the terrain
            if (this.y < terrainHeight) {
                this.y = terrainHeight;
                this.ySpeed = 0;
            }

            //Stop the player at the top of the screen
            if (this.y > Gdx.graphics.getHeight() - 200) {
                this.y = Gdx.graphics.getHeight() - 200;
                this.ySpeed = 0;
            }

            //fix value for the player to land
            this.x = 150f;

            //update the player movement frame rate
            int walkingSpeed = 10;
            this.frame += walkingSpeed * dt;

            if (this.frame >= 8) {
                this.frame = 0;
            }

            //update the smoke movement frame rate
            int smokeSpeed = 10;
            this.smokeRate += smokeSpeed * dt;

            if (this.smokeRate >= 4) {
                this.smokeRate = 0;
            }

        }

        if (this.playerState == State.DYING) {

            if(this.y < - 4000){

                this.playerState = State.DEAD;
            }
            //Stop the player at the top of the screen
            if (this.y > Gdx.graphics.getHeight() - 200) {
                this.y = Gdx.graphics.getHeight() - 200;
                this.ySpeed = 0;
            }
            int dyingSpeed = 8;
            this.dyinRate += dyingSpeed * dt;

            if (this.dyinRate >= 4) {
                this.dyinRate = 0;
            }


        }


    }


    public void jump() {
        this.ySpeed = 250;

    }


    public void render(Batch batch) {

        if (this.playerState == State.ALIVE) {
            if(this.y > terrainHeight){
                batch.draw(this.smoke[(int)this.smokeRate], this.x-43, this.y-113);
            }
            batch.draw(this.texture[(int)this.frame], this.x, this.y);
        }

        if (this.playerState == State.DYING) {

            batch.draw(this.dying[(int)this.dyinRate], this.x, this.y);
        }

        if (this.playerState == State.DEAD) {

        }


    }

    //creating player hit box
    @Override
    public Rectangle getBoundingBox()
    {
        return new Rectangle(x + 30,y + 30,65,90);
    }

    @Override
    public void handleCollision() {
        if (this.playerState == State.ALIVE) {
            this.playerState = State.DYING;
            this.dyinRate = 0;
        }
    }
    public void dispose() {
        //this.texture.dispose();
    }

    public State getplayerState(){
        return this.playerState;
    }

}

