package com.uea.battle.tanks.core.screen.wind

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
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

    fun render(batch: SpriteBatch) {
        cargosOnMap.forEach { it.render(batch) }
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
        sprite.color = Color.BLACK
    }

    fun render(batch: SpriteBatch) {
        sprite.draw(batch)
    }

    fun isColliding(otherEntity: Polygon): Boolean {
        return Intersector.overlapConvexPolygons(otherEntity, boundingPolygon)
    }

    private fun createBoundingPolygon(x: Float, y: Float): Polygon {
        //This polygon well is created by the internet.! DO NOT CHANGE IT
        val polygon = Polygon(floatArrayOf(27f, 58f, 12f, 60f, 6f, 53f, 4f, 45f, 7f, 43f, 8f, 23f, 5f, 21f, 5f, 12f, 11f, 9f, 26f, 7f, 31f, 13f, 37f, 14f, 42f, 8f, 60f, 8f, 64f, 17f, 57f, 21f, 53f, 27f, 54f, 38f, 58f, 45f, 64f, 50f, 61f, 57f, 43f, 60f, 35f, 51f))

        polygon.setOrigin(sprite.originX, sprite.originY)
        polygon.setPosition(x, y)
        polygon.rotation = sprite.rotation

        return polygon
    }
}