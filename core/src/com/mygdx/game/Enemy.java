package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

public class Enemy {
    MyGdxGame game;

    Texture texture;

    final int enemySpeed = 400;

    boolean isDead = false;

    float x = Gdx.graphics.getWidth();
    float y = 0 + Gdx.graphics.getHeight()/4;

    public Enemy(GameScreen game) {
        this.texture = new Texture("enemies/6/0.png");
    }

    public void update() {
        float dt = Gdx.graphics.getDeltaTime();
        this.x -= enemySpeed * dt;
    }

    public void render(Batch batch) {
       batch.draw(this.texture, this.x, this.y);
    }

    private boolean isDead() {
        // Implement logic to determine if the enemy is dead
        // For example, return true when health reaches zero
        return false; // Replace this with your actual logic
    }

    public void dispose() {
        this.texture.dispose();
    }
}