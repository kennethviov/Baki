package io.github.kennethviov;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class WorldRenderer {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    public WorldRenderer(OrthographicCamera camera) {
        map = new TmxMapLoader().load("maps/tmx/pond_map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        this.camera = camera;
    }

    public void render() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
    }
}
