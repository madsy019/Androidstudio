package com.mygdx.game;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy implements CollidableObject{

    private GameScreen game;
    boolean isAlive = true;



    int terrainHeight = 111;

    private Texture[] texture;

    private Texture[] explosion;

    private Vector2 position;

    private int speed = -250;

    private float frame = 0;

    //enemy states created
    enum State {ALIVE, DYING, DEAD}

    private State enemyState;


    public Enemy(GameScreen game, Vector2 position) {

        this.game = game;

        this.position = position;

        //create texture and animation for the enemy
        this.texture = new Texture[4];
        for (int i = 0 ; i < 4; i++) {
            this.texture[i] = new Texture("enemies/4/" + i + ".png");

        }

        //create explosion texture and animation for the enemy
        this.explosion = new Texture[7];
        for (int i = 0 ; i < 7; i++) {
            this.explosion[i] = new Texture("enemies/explosion/" + i + ".png");
        }

        this.enemyState = State.ALIVE;

    }

    public void update() {

        float dt = Gdx.graphics.getDeltaTime();

        this.position.add(new Vector2(this.speed * dt, 0));

        //destroy the enemy when it goes off the screen
        if  (this.position.x < - 300) {
            this.enemyState = State.DEAD;
        }

        //update the Enemy movement frame rate
        int walkingSpeed = 15;

        if (this.enemyState == State.ALIVE){
            this.frame += walkingSpeed * dt;
            if (this.frame >= 4) {
                this.frame = 0;
            }
        }

        if (this.enemyState == State.DYING){
            this.frame += walkingSpeed * dt;
            if (this.frame >= 7) {
                this.enemyState = State.DEAD;
            }
        }


    }
    public void render(SpriteBatch batch) {
        if (this.enemyState == State.ALIVE) {
            batch.draw(this.texture[(int) this.frame], this.position.x, this.position.y + terrainHeight);
        }

        if (this.enemyState == State.DYING) {
            batch.draw(this.explosion[(int)this.frame], this.position.x - 75, this.position.y + terrainHeight- 75);
        }

        if (this.enemyState == State.DEAD) {
        }
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(this.position.x + 40,this.position.y + terrainHeight + 30,55,90);
    }

    @Override
    public void handleCollision() {
        if (this.enemyState == State.ALIVE) {
            this.enemyState = State.DYING;
            this.frame = 0;
        }
    }

    public boolean isAlive() {
        if (this.enemyState == State.DEAD) {
            return false;
        }
        return true;
    }

}
