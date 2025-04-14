package io.github.kennethviov;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraManager {
    private OrthographicCamera camera;
    private Vector2 targetPos;

    public CameraManager(float viewportWidth, float viewportHeight) {
        camera = new OrthographicCamera(viewportWidth, viewportHeight);
        camera.position.set(viewportWidth / 2f, viewportHeight / 2f, 0);
        camera.update();

        targetPos = new Vector2(camera.position.x, camera.position.y);
    }

    public void update(float deltaTime) {
        // smooth follow (lerp(linear interpolation))
        Vector3 pos = camera.position;
        pos.x += (targetPos.x - pos.x) * 5f * deltaTime;
        pos.y += (targetPos.y - pos.y) * 5f * deltaTime;
        camera.update();
    }

    public void follow(Vector2 newTarget) {
        this.targetPos.set(newTarget);
    }

    public void zoom(float zoomAmount) {
        camera.zoom += zoomAmount;
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
