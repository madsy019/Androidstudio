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

    float ySpeed = 1000;

    float gravity = 700;

    float heightCorrection = 30;




    public Player(GameScreen game) {

        this.texture = new Texture("players/3/walk/0.png");
        this.terrian = new Texture("Terain/3.png");
    }



    public void update() {

        float dt = Gdx.graphics.getDeltaTime();

        this.ySpeed -= gravity * dt;

        this.y += ySpeed * dt;
        if (this.y < this.terrian.getHeight()) {

            this.y = this.terrian.getHeight() - heightCorrection ;
        }

        this.x = 150f;



    }


    public void render(Batch batch) {

        batch.draw(this.texture, this.x , this.y);

    }



    public void dispose() {
        this.texture.dispose();
        this.terrian.dispose();
    }
}

