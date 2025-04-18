package io.github.kennethviov.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.kennethviov.entity.Frog;
import io.github.kennethviov.screens.GameScreen;

public class InputHandler {

    private final GameScreen game;
    private final Frog frog;

    public InputHandler(GameScreen game, Frog frog) {
        this.game = game;
        this.frog = frog;
    }

    public void handleInput(float delta) {
        boolean moving = false;

        /// attack/catch input
        if (Gdx.input.isTouched()) {
            Vector3 click = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(click);

            float dx = click.x - frog.getPosition().x;
            float dy = click.y - frog.getPosition().y;

            frog.attackTimer = 0.3f;
            if (dy > dx && dy < -dx) frog.setCurrAnimation(frog.getAnimation("catchLeft"));
            if (dy < dx && dy > -dx) frog.setCurrAnimation(frog.getAnimation("catchRight"));
            if (dy < dx && dy < -dx) frog.setCurrAnimation(frog.getAnimation("catchDown"));
            if (dy > dx && dy > -dx) frog.setCurrAnimation(frog.getAnimation("catchDown"));

            frog.shootTongue(new Vector2(click.x, click.y));

            return;
        }

        /// movement handlers
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            frog.getPosition().y += frog.SPEED * delta;
            frog.setCurrAnimation(frog.getAnimation("moveUp"));
            moving = true;
            /// dash logic
            if (spaceIsPressed() && frog.canDash()) {
                frog.getPosition().y += (25 * (frog.SPEED * delta));
                frog.triggerDashCooldown();
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            frog.getPosition().y -= frog.SPEED * delta;
            frog.setCurrAnimation(frog.getAnimation("moveDown"));
            moving = true;
            if (spaceIsPressed() && frog.canDash()) {
                frog.getPosition().y -= (25 * (frog.SPEED * delta));
                frog.triggerDashCooldown();
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            frog.getPosition().x += frog.SPEED * delta;
            frog.setCurrAnimation(frog.getAnimation("moveRight"));
            moving = true;
            if (spaceIsPressed() && frog.canDash()) {
                frog.getPosition().x += (25 * (frog.SPEED * delta));
                frog.triggerDashCooldown();
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            frog.getPosition().x -= frog.SPEED * delta;
            frog.setCurrAnimation(frog.getAnimation("moveLeft"));
            moving = true;
            if (spaceIsPressed() && frog.canDash()) {
                frog.getPosition().x -= (25 * (frog.SPEED * delta));
                frog.triggerDashCooldown();
            }
        }
        if (!moving) {
            if (frog.getCurrAnimation() == frog.getAnimation("moveRight"))
                frog.setCurrAnimation(frog.getAnimation("idleRight"));
            if (frog.getCurrAnimation() == frog.getAnimation("moveLeft"))
                frog.setCurrAnimation(frog.getAnimation("idleLeft"));
            if (frog.getCurrAnimation() == frog.getAnimation("moveUp"))
                frog.setCurrAnimation(frog.getAnimation("idleUp"));
            if (frog.getCurrAnimation() == frog.getAnimation("moveDown"))
                frog.setCurrAnimation(frog.getAnimation("idleDown"));
        }

        // Clamp frog position so he stays inside the world
        TextureRegion frame = frog.getCurrAnimation().getKeyFrame(0f);

        frog.getPosition().x = MathUtils.clamp(frog.getPosition().x, 0, game.WORLD_WIDTH - (frame.getRegionWidth() * 1.25f));
        frog.getPosition().y = MathUtils.clamp(frog.getPosition().y, 0, game.WORLD_HEIGHT - (frame.getRegionHeight() * 1.25f));
    }

    private boolean spaceIsPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }
}
