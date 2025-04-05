package io.github.kennethviov;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class Frog {

    private final InputHandler inputHandler;

    private final Vector2 position;
    public final float speed = 75f;
    public float attackTimer = 0f;

    public final Texture texture;
    private float stateTime;
    private Animation<TextureRegion> currAnimation;

    private final Map<String, Animation<TextureRegion>> animations;

    public Frog (GameScreen game) {
        this.inputHandler = new InputHandler(game, this);

        position = new Vector2(100, 100);
        texture = new Texture("sprites/frog/frog (2).png");

        // Sprite sheet info
        int frameWidth = 16,
            frameHeight = 16;
        TextureRegion[][] frames = TextureRegion.split(texture, frameWidth, frameHeight);

        animations = new HashMap<>();
        String[] aniNames = {
            "idleRight", "idleLeft", "idleDown", "idleUp",
            "moveRight", "moveLeft", "moveDown", "moveUp",
            "catchRight", "catchLeft", "catchDown", "catchUp"
        };

        // assign animation for each state
        int i = 0;
        for (String aniName : aniNames) {
            animations.put(aniName, createAnimation(frames[i++]));
        }

        // default animation
        currAnimation = animations.get("idleDown");
        stateTime = 0f;
    }

    /// create an animation based on the given array of frames
    private Animation<TextureRegion> createAnimation(TextureRegion[] frames) {
        return new Animation<>(0.2f, frames);
    }

    /// getter and setters
    public Vector2 getPosition() { return position; }
    public Animation<TextureRegion> getAnimation(String aniName) { return animations.get(aniName); }
    public Animation<TextureRegion> getCurrAnimation() { return currAnimation; }
    public void setCurrAnimation(Animation<TextureRegion> animation) { this.currAnimation = animation; }

    /// magics
    public void update(float delta) {
        stateTime += delta;

        if (attackTimer > 0) {
            attackTimer -= delta;
            return;
        } else if (attackTimer <= 0) {
            // After attack ends, return to idle animation
            if (currAnimation == animations.get("catchLeft")) currAnimation = animations.get("idleLeft");
            else if (currAnimation == animations.get("catchRight")) currAnimation = animations.get("idleRight");
            else if (currAnimation == animations.get("catchUp")) currAnimation = animations.get("idleUp");
            else if (currAnimation == animations.get("catchDown")) currAnimation = animations.get("idleDown");
        }

        inputHandler.handleInput(delta);
    }

    public void render(SpriteBatch batch) {
        TextureRegion currFrame = currAnimation.getKeyFrame(stateTime, true);
        batch.draw(
            currFrame,
            position.x,
            position.y,
            (float) ((float)currFrame.getRegionWidth() * 1.25),
            (float) ((float)currFrame.getRegionHeight() * 1.25)
        );
    }

    public void dispose() {
        texture.dispose();
    }
}
