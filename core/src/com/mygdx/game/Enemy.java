package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy implements CollidableObject{

    private GameScreen game;

    int terrainHeight = 111;

    private Texture[] texture;

    private Vector2 position;

    private int speed = -250;

    private float frame = 0;

    public Rectangle getBoundingBox(){
        return null;
    }

    public Enemy(GameScreen game, Vector2 position) {

        this.game = game;
        this.position = position;

        this.texture = new Texture[8];
        for (int i = 0 ; i < 8; i++) {
            this.texture[i] = new Texture("enemies/2/" + i + ".png");

        }

    }

    public void update() {

        float dt = Gdx.graphics.getDeltaTime();

        this.position.add(new Vector2(this.speed * dt, 0));


    }

    public void render(SpriteBatch batch) {
        batch.draw(this.texture[0], this.position.x, this.position.y + terrainHeight);

    }

}
