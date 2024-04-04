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
    float spawnTimer = 0;
    float spawnInterval = 2; // Adjust this value for the interval between enemy spawns

    public Enemy(GameScreen game) {
        this.texture = new Texture("enemies/6/0.png");
    }

    public void update() {
        float dt = Gdx.graphics.getDeltaTime();
        spawnTimer += dt;
        if (spawnTimer >= spawnInterval) {
            spawnEnemy();
            spawnTimer = 0;
        }

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
    private void spawnEnemy() {

        Random random = new Random();
        y = random.nextFloat() * (Gdx.graphics.getHeight() - texture.getHeight());
        // Instantiate or spawn your enemy object here with x and y values
        // Example: game.spawnEnemy(new Enemy(texture, x, y));
    }

    public void dispose() {
        this.texture.dispose();
    }
}