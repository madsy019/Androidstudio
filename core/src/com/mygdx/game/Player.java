package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Player {

    MyGdxGame game;

    Texture[] texture = new Texture[8];
    float frame = 0;

    Texture terrian;

    float x = 0;
    float y = 0;

    float ySpeed = 600;

    float gravity = 300;

    float heightCorrection = 27;
     float terrainSpeed = 0;
     float backgroundSpeed = 200;


    public Player(GameScreen game) {

        // As the player is moving adding it to an array of Textures
        for (int i = 0 ; i < 8; i++) {
            this.texture[i] = new Texture("players/3/walk/" + i + ".png");
        }

        this.terrian = new Texture("Terain/3.png");

        this.y = this.terrian.getHeight() - heightCorrection;
    }



    public void update() {

        float dt = Gdx.graphics.getDeltaTime();

        this.terrainSpeed -= (this.backgroundSpeed) * dt;

        if(this.terrainSpeed + this.terrian.getWidth() < 0){
            this.terrainSpeed = 0;
        }

        this.ySpeed -= gravity * dt;

        this.y += ySpeed * dt;

        if (this.y < this.terrian.getHeight() - heightCorrection) {

            this.y = this.terrian.getHeight() - heightCorrection ;
            this.ySpeed = 0;
        }

        //fix value for the player to land
        this.x = 150f;

        //update the player movement frame rate
        int walkingSpeed = 15;

        this.frame += walkingSpeed * dt;
        if (this.frame >= 8) {
            this.frame = 0;
        }

    }

    int maxJumpHeight = 800;

    public void jump() {
        if(this.y <= maxJumpHeight){
            this.ySpeed = 300;
        }

    }



    public void render(Batch batch) {

        batch.draw(this.texture[(int)this.frame], this.x, this.y);
        batch.draw( this.terrian, this.terrainSpeed,0);
        batch.draw( this.terrian, this.terrainSpeed + this.terrian.getWidth(),0);
        batch.draw( this.terrian, (this.terrainSpeed + this.terrian.getWidth()) + + this.terrian.getWidth(),0);
    }



    public void dispose() {
        //this.texture.dispose();
        this.terrian.dispose();
    }
}

