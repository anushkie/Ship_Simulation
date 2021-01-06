package com.uea.battle.tanks.core.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundPlayer {

    public static SoundPlayer INSTANCE = new SoundPlayer();

    private SoundPlayer() {
    }

    public void playSound(String soundName) {
    }

    public void playCargoCollectSound() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/cargo_collected.wav"));
        sound.play();
    }
}
