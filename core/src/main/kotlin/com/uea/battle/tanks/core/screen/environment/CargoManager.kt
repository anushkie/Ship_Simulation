package com.uea.battle.tanks.core.screen.environment

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Polygon
import com.uea.battle.tanks.core.sound.SoundPlayer
import com.uea.battle.tanks.core.ship.PlayerShip
import com.uea.battle.tanks.core.ui.GameUI
import java.util.*

class CargoManager(private val mapHeight: Int, playerShip: PlayerShip, private val gameUI: GameUI) {

    private val cargosOnMap = ArrayList<Cargo>()
    private var totalCargos = 0
    private var collectedCargos = 0
    private var cargoWeight = 1000

    init {
        for (locationsForCargo in locationsForCargos) {
            cargosOnMap += Cargo(locationsForCargo.first * 32, (mapHeight - 1 - locationsForCargo.second) * 32)
        }
        totalCargos = cargosOnMap.size
        gameUI.updateCollectedCargoCount(collectedCargos, totalCargos, cargoWeight)

        playerShip.registerPositionCallback {
            for (cargo in cargosOnMap) {

                if (cargo.isColliding(playerShip.boundingPolygon) && collectedCargos <= 5) {
                    cargo.collected = true
                    collectedCargos++
                    cargoWeight += 150
                    gameUI.updateCollectedCargoCount(collectedCargos, totalCargos, cargoWeight)
                    soundPlayer.playCargoCollectSound()
                }
            }
        }
    }

    fun update() {
        cargosOnMap.removeIf { it.collected }
    }

    fun render(batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
        cargosOnMap.forEach { it.render(batch, shapeRenderer) }
    }

    companion object {
        private val locationsForCargos = setOf(
                13 to 122,
                19 to 117,
                21 to 81,
                21 to 105,
                28 to 93,
                12 to 68,
                26 to 56,
                8 to 47,
                19 to 89,
                12 to 104,
                29 to 122

        )
        private val soundPlayer = SoundPlayer.INSTANCE
    }
}

class Cargo(x: Int, y: Int) {

    var collected = false
    private val sprite = Sprite(Texture("ui/cargo.png"))
    private val boundingPolygon = createBoundingPolygon(x.toFloat(), y.toFloat())

    init {
        sprite.setPosition(x.toFloat(), y.toFloat())
    }

    fun render(batch: SpriteBatch, shapeRenderer: ShapeRenderer) {
        sprite.draw(batch)

        //shapeRenderer.polygon(boundingPolygon.transformedVertices)
    }

    fun isColliding(otherEntity: Polygon): Boolean {
        return Intersector.overlapConvexPolygons(otherEntity, boundingPolygon)
    }

    private fun createBoundingPolygon(x: Float, y: Float): Polygon {
        //This polygon well is created by the internet.! DO NOT CHANGE IT
        val polygon = Polygon(floatArrayOf(
                0F, 0F,
                32F, 0F,
                32F, 26F,
                0F, 26F
        ))

        polygon.setOrigin(sprite.originX, sprite.originY)
        polygon.setPosition(x, y)
        polygon.rotation = sprite.rotation

        return polygon
    }
}