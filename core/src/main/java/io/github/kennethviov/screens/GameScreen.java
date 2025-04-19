package io.github.kennethviov.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.kennethviov.utilities.CameraManager;
import io.github.kennethviov.Main;
import io.github.kennethviov.utilities.WorldRenderer;
import io.github.kennethviov.entity.Frog;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {

    public final float WORLD_WIDTH = 40 * 16f;
    public final float WORLD_HEIGHT = 30 * 16f;

    public Texture pixel;

    private final Frog frog;
    private final WorldRenderer worldRenderer;
    public final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final CameraManager cameraManager;

    public GameScreen(Main game) {
        this.frog = new Frog(this);
        pixel = new Texture("sprites/frog/pixel.png");
        this.batch = game.batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
            Gdx.graphics.getWidth() / 3f,
            Gdx.graphics.getHeight() / 3f);

        cameraManager = new CameraManager(camera);

        cameraManager.setWorldSize(WORLD_WIDTH, WORLD_HEIGHT);

        worldRenderer = new WorldRenderer(camera, "maps/tmx/pond_map.tmx");
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        frog.update(delta);
        frog.updateTongue(delta);

        cameraManager.setTarget(frog.getPosition());
        cameraManager.update(delta);

        worldRenderer.render();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        frog.render(batch);
        frog.renderTongue(batch, pixel);
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
