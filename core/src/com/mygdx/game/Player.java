package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import org.w3c.dom.Text;

public class Player {
    MyGdxGame game;

    Texture texture;

    float x = 0;
    float y = 0;

    float ySpeed = 1000;

    float gravity = 600;


    public Player(MyGdxGame game) {
        this.texture = new Texture("players/3/walk/0.png");
    }

    public void update() {
        float dt = Gdx.graphics.getDeltaTime();

        this.ySpeed -= gravity * dt;

        this.y += ySpeed * dt;
        if (this.y < 0) {
            this.y = 0;
        }

        this.x += 100 * dt;
    }


    public void render(Batch batch) {
        batch.draw(this.texture, this.x, this.y);
    }



    public void dispose() {
        this.texture.dispose();
    }
}

