package com.uea.battle.tanks.core.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.uea.battle.tanks.core.ship.PlayerShip;

public class ShipInputProcessor implements InputProcessor {

    private final PlayerShip playerShip;

    public ShipInputProcessor(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }

    @Override
    public boolean keyDown(int keycode) {
        /*if (keycode == Input.Keys.UP) {
            playerShip.acceleratorPressed();
            return true;
        }
        if (keycode == Input.Keys.DOWN) {
            playerShip.reverseAcceleratorPressed();
            return true;
        }*/

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        /*if (keycode == Input.Keys.UP || keycode == Input.Keys.DOWN) {
            playerShip.acceleratorReleased();
            return true;
        }*/
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
