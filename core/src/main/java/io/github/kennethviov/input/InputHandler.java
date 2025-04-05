package io.github.kennethviov.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import io.github.kennethviov.Frog;
import io.github.kennethviov.GameScreen;

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
            if (dy > dx && dy > -dx) frog.setCurrAnimation(frog.getAnimation("catchUp"));

            return;
        }

        /// movement handlers
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            frog.getPosition().y += frog.speed * delta;
            frog.setCurrAnimation(frog.getAnimation("moveUp"));
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            frog.getPosition().y -= frog.speed * delta;
            frog.setCurrAnimation(frog.getAnimation("moveDown"));
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            frog.getPosition().x += frog.speed * delta;
            frog.setCurrAnimation(frog.getAnimation("moveRight"));
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            frog.getPosition().x -= frog.speed * delta;
            frog.setCurrAnimation(frog.getAnimation("moveLeft"));
            moving = true;
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
    }
}
