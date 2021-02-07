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
import com.uea.battle.tanks.core.screen.environment.CargoManager;
import com.uea.battle.tanks.core.screen.environment.EnvironmentManager;
import com.uea.battle.tanks.core.ship.PlayerShip;
import com.uea.battle.tanks.core.ui.GameUI;

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
        Texture texture = new Texture(Gdx.files.internal("ui/shipSprite.png"));

        playerShip = new PlayerShip(new Sprite(texture), mapManager, stage);

        mapWidth = tiledMap.getProperties().get(WIDTH, Integer.class) * 32;
        mapHeight = tiledMap.getProperties().get(HEIGHT, Integer.class) * 32;

        GameUI gameUI = new GameUI(stage, playerShip);
        this.environmentManager = new EnvironmentManager(gameUI);
        this.cargoManager = new CargoManager(mapHeight / 32, playerShip, gameUI);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage));
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
        cargoManager.render(spriteBatch, shapeRenderer);
        environmentManager.render(spriteBatch, shapeRenderer);
        spriteBatch.end();
        shapeRenderer.end();

        stage.draw();
        stage.act();

        handleInput();
    }

    private void updateCamera() {
        camera.position.set(playerShip.getX(), playerShip.getY(), 0);
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
