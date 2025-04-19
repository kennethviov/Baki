package io.github.kennethviov.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.kennethviov.screens.GameScreen;
import io.github.kennethviov.utilities.InputHandler;

import java.util.HashMap;
import java.util.Map;

public class Frog {

    private final InputHandler inputHandler;

    private final Vector2 position;
    public final float SPEED = 75f;
    public float attackTimer = 0f;
    public float dashCooldown = 0f;
    public final float DASH_COOLDOWN = 2f; // in seconds

    private final Tongue tongue;

    public final int width = 16, height = 16; // sprite dimensions
    private final Texture texture;
    private float stateTime;
    private Animation<TextureRegion> currAnimation;
    private final Map<String, Animation<TextureRegion>> animations;

    public Frog (GameScreen game) {
        this.inputHandler = new InputHandler(game, this);

        position = new Vector2(100, 100);
        texture = new Texture("sprites/frog/frog (2).png");

        // Sprite sheet info
        TextureRegion[][] frames = TextureRegion.split(texture, width, height);

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

        tongue = new Tongue();
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

    /// space dash logic
    public boolean canDash() { return dashCooldown <= 0; }
    public void triggerDashCooldown() { dashCooldown = DASH_COOLDOWN; }

    ///  tongue stuff
    public void shootTongue(Vector2 clickPos) {
        tongue.shoot(new Vector2(position).add(width * .6f, height * .75f), clickPos);
    }

    public void updateTongue(float delta) {
        tongue.update(delta);
    }

    public void renderTongue(SpriteBatch batch, Texture pixel) {
        if (pixel == null) {
            System.out.println("pixel is null");
        }
        tongue.render(batch, pixel);
    }

    /// magic
    public void update(float delta) {
        stateTime += delta;

        if (dashCooldown > 0) {
            dashCooldown -= delta;
        }
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
