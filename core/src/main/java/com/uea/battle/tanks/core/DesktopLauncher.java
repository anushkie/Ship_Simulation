package com.uea.battle.tanks.core;

import com.badlogic.gdx.Game;
import com.uea.battle.tanks.core.screen.ShipScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class DesktopLauncher extends Game {
	@Override
	public void create() {
		setScreen(new ShipScreen());
	}
}