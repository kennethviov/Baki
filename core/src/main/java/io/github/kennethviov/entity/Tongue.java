package io.github.kennethviov.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Tongue {

    private final Vector2 origin;
    private final Vector2 target;
    private final Vector2 current;
    private final float speed = 550f;
    private boolean extending;
    private boolean retracting;
    private boolean active;

    public Tongue() {
        origin = new Vector2();
        target = new Vector2();
        current = new Vector2();
        active = false;
    }

    public void shoot(Vector2 from, Vector2 to) {
        origin.set(from);
        target.set(to);
        current.set(from);
        extending = true;
        retracting = false;
        active = true;
    }

    public void update(float delta) {
        if (!active) return;

        Vector2 dir = new Vector2(target).sub(origin).nor();
        float step = speed * delta;

        if (extending) {
            float distanceToTarget = current.dst(target);
            if (step >= distanceToTarget) {
                current.set(target);
                extending = false;
                retracting = true;
            } else {
                current.mulAdd(dir, step);
            }
        } else if (retracting) {
            Vector2 retractDir = new Vector2(origin).sub(current).nor();
            float distanceToOrigin = current.dst(origin);
            if (step >= distanceToOrigin) {
                current.set(origin);
                active = false;
            } else {
                current.mulAdd(retractDir, step);
            }
        }
    }

    public void render(SpriteBatch batch, Texture pixel) {
        if (!active) return;

        float length = current.dst(origin);
        float angle = new Vector2(current).sub(origin).angleDeg();

        batch.draw(
            pixel,
            origin.x, origin.y,
            0, 0,
            length, 1.5f,
            1f, 1f,
            angle,
            0, 0,
            1, 1,
            false, false
        );
    }

}
