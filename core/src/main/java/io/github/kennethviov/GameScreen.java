package io.github.kennethviov;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {

    private final Main game;
    private final Frog frog;
    private WorldRenderer worldRenderer;
    public final OrthographicCamera camera;
    private final SpriteBatch batch;

    public GameScreen(Main game) {
        this.game = game;
        this.frog = new Frog(this);
        this.batch = game.batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
            (float)Gdx.graphics.getWidth() / 3,
            (float)Gdx.graphics.getHeight() / 3);

        worldRenderer = new WorldRenderer(camera);
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        frog.update(delta);

        camera.position.set(frog.getPosition().x, frog.getPosition().y, 0);
        camera.update();

        worldRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        frog.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        frog.dispose();
        worldRenderer.dispose();
    }
}
