package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;


public class Player implements CollidableObject{


    MyGdxGame game;

    //Got the height from measuring the terrain
    int terrainHeight = 111;

    Texture[] texture = new Texture[8];
    Texture[] smoke = new Texture[4];

    float frame = 0;

    float smokeRate = 0;


    float x = 0;
    float y = 0;

    float ySpeed = 600;

    float gravity = 300;

    float heightCorrection = 27;

    float backgroundSpeed = 200;


    public Player(GameScreen game) {

        // As the player is moving adding it to an array of Textures
        for (int i = 0 ; i < 8; i++) {
            this.texture[i] = new Texture("players/3/walk/" + i + ".png");
        }
        // Texture for smoke
        for (int i = 0 ; i < 4; i++) {
            this.smoke[i] = new Texture("players/smoke/" + i + ".png");
        }

        this.y = + terrainHeight;
    }

    public void update() {

        float dt = Gdx.graphics.getDeltaTime();

        this.ySpeed -= gravity * dt;

        this.y += ySpeed * dt;


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
        int walkingSpeed = 15;
        this.frame += walkingSpeed * dt;

        if (this.frame >= 8) {
            this.frame = 0;
        }

        //update the smoke movement frame rate
        int smokeSpeed = 15;
        this.smokeRate += smokeSpeed * dt;

        if (this.smokeRate >= 4) {
            this.smokeRate = 0;
        }




    }


    public void jump() {
        this.ySpeed = 350;

    }


    public void render(Batch batch) {

        if(this.y > terrainHeight){
            batch.draw(this.smoke[(int)this.smokeRate], this.x-43, this.y-113);
        }
        batch.draw(this.texture[(int)this.frame], this.x, this.y);




    }

    //creating player hit box
    @Override
    public Rectangle getBoundingBox()
    {
        return new Rectangle(x + 30,y + 30,65,90);
    }

    @Override
    public void handleCollision() {

    }
    public void dispose() {
        //this.texture.dispose();

    }
}

