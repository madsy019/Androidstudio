package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Bullets implements CollidableObject {
    private int speed = 800;

    Texture texture;
    float xPosition =0;
    float yPosition =0;

    boolean isAlive = true;

    public Bullets(float x, float y){
        this.xPosition = x;
        this.yPosition = y;

        if (texture == null){
            texture = new Texture("missiles/9.png");
        }

    }

    public void update(){

        float dt = Gdx.graphics.getDeltaTime();

        xPosition+= speed * dt;

        if (this.xPosition > Gdx.graphics.getWidth() + 300) {
            this.isAlive = false;
        }

    }

    public void render(Batch batch) {
        batch.draw(texture, xPosition, yPosition);
    }

    public void dispose() {
        this.texture.dispose();

    }
    @Override
    public Rectangle getBoundingBox()
    {
        return new Rectangle(this.xPosition + 250,this.yPosition + 5  ,50,40);
    }

    @Override
    public void handleCollision() {

    }

    public boolean isAlive() {
        return this.isAlive;
    }
}
