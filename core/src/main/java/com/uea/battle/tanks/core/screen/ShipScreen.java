package com.uea.battle.tanks.core.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.uea.battle.tanks.core.map.MapLoader;
import com.uea.battle.tanks.core.screen.wind.CargoManager;
import com.uea.battle.tanks.core.screen.wind.EnvironmentManager;
import com.uea.battle.tanks.core.ship.Direction;
import com.uea.battle.tanks.core.ship.EnemyShip;
import com.uea.battle.tanks.core.ship.PlayerShip;
import com.uea.battle.tanks.core.ship.Ship;
import com.uea.battle.tanks.core.ui.GameUI;

import java.util.ArrayList;
import java.util.List;

public class ShipScreen implements Screen {

    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";

    private OrthographicCamera camera;

    private TiledMapRenderer tiledMapRenderer;

    private SpriteBatch spriteBatch;

    private PlayerShip playerShip;

    private EnvironmentManager environmentManager;
    private CargoManager cargoManager;

    private final MapLoader mapManager = new MapLoader();
    private final Stage stage = new Stage(new ScreenViewport());
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private int mapWidth;
    private int mapHeight;

    @Override
    public void show() {
        VisUI.load();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        camera.update();
        TiledMap tiledMap = new TmxMapLoader().load("maps/route223.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        mapManager.setUpMap(tiledMap);

        spriteBatch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("ui/tank.png"));

        playerShip = new PlayerShip(new Sprite(texture), mapManager);

        mapWidth = tiledMap.getProperties().get(WIDTH, Integer.class) * 32;
        mapHeight = tiledMap.getProperties().get(HEIGHT, Integer.class) * 32;

        GameUI gameUI = new GameUI(stage, playerShip);
        this.environmentManager = new EnvironmentManager(gameUI);
        this.cargoManager = new CargoManager(mapHeight / 32, playerShip, gameUI);

        ShipInputProcessor shipInputProcessor = new ShipInputProcessor(playerShip);
        Gdx.input.setInputProcessor(new InputMultiplexer(shipInputProcessor, stage));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.position.set(.getX(), sprite.getY(), 0);
        playerShip.update(camera, delta, environmentManager);
        environmentManager.update(camera, delta);
        cargoManager.update();

        updateCamera();
        tiledMapRenderer.setView(camera);

        tiledMapRenderer.render();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        playerShip.render(spriteBatch, shapeRenderer);
        cargoManager.render(spriteBatch);
        environmentManager.render(spriteBatch, shapeRenderer);
        spriteBatch.end();
        shapeRenderer.end();

        stage.draw();
        stage.act();

        handleInput();
    }

    private void updateCamera() {
        camera.position.set(playerShip.getX(), playerShip.getY(), 0);

        /*int mapLeft = 0;
        // The right boundary of the map (x + width)
        int mapRight = mapWidth;
        // The bottom boundary of the map (y)
        int mapBottom = 0;
        // The top boundary of the map (y + height)
        int mapTop = mapHeight;
        // The camera dimensions, halved
        float cameraHalfWidth = camera.viewportWidth * .5f;
        float cameraHalfHeight = camera.viewportHeight * .5f;

        // Move camera after player as normal

        float cameraLeft = camera.position.x - cameraHalfWidth;
        float cameraRight = camera.position.x + cameraHalfWidth;
        float cameraBottom = camera.position.y - cameraHalfHeight;
        float cameraTop = camera.position.y + cameraHalfHeight;

        // Horizontal axis
        if (mapWidth < camera.viewportWidth) {
            camera.position.x = mapRight / 2F;
        } else if (cameraLeft <= mapLeft) {
            camera.position.x = mapLeft + cameraHalfWidth;
        } else if (cameraRight >= mapRight) {
            camera.position.x = mapRight - cameraHalfWidth;
        }

        // Vertical axis
        if (mapHeight < camera.viewportHeight) {
            camera.position.y = mapTop / 2F;
        } else if (cameraBottom <= mapBottom) {
            camera.position.y = mapBottom + cameraHalfHeight;
        } else if (cameraTop >= mapTop) {
            camera.position.y = mapTop - cameraHalfHeight;
        }*/

        camera.update();
    }

    private void handleInput() {
        playerShip.move(environmentManager);

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            camera.zoom += 0.1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            camera.zoom -= 0.1;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
