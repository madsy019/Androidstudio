package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Player {
    MyGdxGame game;

    Texture texture;

    Texture terrian;

    float x = 0;
    float y = 0;

    float ySpeed = 300;

    float gravity = 200;

    float heightCorrection = 27;
     float terrainSpeed = 0;
     float backgroundSpeed = 200;




    public Player(GameScreen game) {

        this.texture = new Texture("players/3/walk/0.png");
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

    }

    public void jump() {
        this.ySpeed = 300;

    }



    public void render(Batch batch) {

        batch.draw(this.texture, this.x , this.y);
        batch.draw( this.terrian, this.terrainSpeed,0);
        batch.draw( this.terrian, this.terrainSpeed + this.terrian.getWidth(),0);

    }



    public void dispose() {
        this.texture.dispose();
        this.terrian.dispose();
    }
}

