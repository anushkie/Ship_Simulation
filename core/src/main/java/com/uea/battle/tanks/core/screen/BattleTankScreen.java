package com.uea.battle.tanks.core.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.uea.battle.tanks.core.tank.Direction;
import com.uea.battle.tanks.core.tank.EnemyShip;
import com.uea.battle.tanks.core.tank.PlayerShip;
import com.uea.battle.tanks.core.tank.Ship;

import java.util.ArrayList;
import java.util.List;

public class BattleTankScreen implements Screen {

    private TiledMap tiledMap;

    private OrthographicCamera camera;

    private TiledMapRenderer tiledMapRenderer;

    private SpriteBatch spriteBatch;

    private Ship playerShip;

    private final List<Ship> enemyShip;

    private Texture texture;

    public BattleTankScreen() {
        enemyShip = new ArrayList<>();
    }

    @Override
    public void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        camera.update();
        tiledMap = new TmxMapLoader().load("maps//riverMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        spriteBatch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("ui//shipSmall.png"));
        playerShip = new PlayerShip(new Sprite(texture));
        for (int i = 0; i <20 ; i++) {
            enemyShip.add(new EnemyShip(new Sprite(texture)));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.position.set(.getX(), sprite.getY(), 0);
        playerShip.update(camera, delta);
        for (Ship ship : enemyShip) {
            ship.update(camera, delta);
        }
        camera.position.set(playerShip.getX(), playerShip.getY(),0);
        camera.update();
        tiledMapRenderer.setView(camera);

        tiledMapRenderer.render();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        playerShip.render(spriteBatch);
        for (Ship ship : enemyShip) {
            ship.render(spriteBatch);
        }
        spriteBatch.end();
        handleInput();
    }

    private void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerShip.move(Direction.LEFT);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerShip.move(Direction.RIGHT);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            playerShip.move(Direction.UP);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            playerShip.move(Direction.DOWN);
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
