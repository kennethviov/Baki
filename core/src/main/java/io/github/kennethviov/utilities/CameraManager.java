package io.github.kennethviov.utilities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraManager {
    private final OrthographicCamera camera;
    private final Vector2 target = new Vector2();

    private float worldWidth, worldHeight;

    public CameraManager(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void setTarget(Vector2 position) {
        target.set(position).add(8, 8); // half of sprite size
    }

    public void setWorldSize(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void update(float delta) {
        //smooth follow
        Vector3 pos = camera.position;
        pos.x += (target.x - pos.x) * 5f * delta;
        pos.y += (target.y - pos.y) * 5f * delta;

        //clamp camera to world bounds
        float halfW = camera.viewportWidth * camera.zoom / 2f;
        float halfH = camera.viewportHeight * camera.zoom  / 2f;

        pos.x = MathUtils.clamp(pos.x, halfW, worldWidth - halfW);
        pos.y = MathUtils.clamp(pos.y, halfH, worldHeight - halfH);

        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
