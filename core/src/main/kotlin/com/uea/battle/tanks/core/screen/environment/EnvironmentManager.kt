package com.uea.battle.tanks.core.screen.environment

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.uea.battle.tanks.core.ship.PlayerShip.M_PI_2
import com.uea.battle.tanks.core.ui.GameUI

class EnvironmentManager(private val gameUI: GameUI) : Environment {

    private var wind: Wind? = null
    private var windManagerStateTime = 0F

    fun update(camera: OrthographicCamera, delta: Float) {
        windManagerStateTime += delta

        if (wind == null) {
            wind = Wind(-M_PI_2, 12F)
            gameUI.updateSpeedAndDirection(-M_PI_2 * MathUtils.radiansToDegrees, 4F)
        }

        wind?.update(camera, delta, this)
    }

    fun render(batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
        wind?.render(batch, shapeRenderer)
    }

    override val windVelocity: Float
        get() = wind?.velocity ?: 0F
    override var windRadians: Float
        get() = wind?.angle ?: 0F
        set(value) {
            wind?.angle = value
            gameUI.updateSpeedAndDirection(value * MathUtils.radiansToDegrees, 4F)
        }
}

interface Environment {
    val windVelocity: Float
    var windRadians: Float
}
