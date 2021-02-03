package com.uea.battle.tanks.core.screen.environment

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.uea.battle.tanks.core.ship.Movable
import com.uea.battle.tanks.core.ship.Renderable
import java.util.*

class Wind(private var angle: Float, private var velocity: Float) : Movable, Renderable {

    private val windSprites: MutableList<Sprite> = ArrayList()

    init {
        initSpritesBasedOnOrientation()
    }

    fun updateOrientation(orientation: Orientation) {
        /*this.orientation = orientation
        initSpritesBasedOnOrientation()*/
    }

    private fun initSpritesBasedOnOrientation() {
        /*val windTexture = Texture(Gdx.files.internal("ui/tank.png"))
        windSprites.clear()

        val widthOfSprite = 32F
        val heightOfSprite = 32F

        when (orientation) {
            Orientation.LEFT_TO_RIGHT, Orientation.RIGHT_TO_LEFT -> {
                val spritesRequiredInLeftRightOrientation = Gdx.graphics.height / heightOfSprite

                for (i in 0 until spritesRequiredInLeftRightOrientation.toInt()) {
                    val windSprite = Sprite(windTexture)
                    windSprites.add(windSprite)

                    val xPosition = if (orientation == Orientation.RIGHT_TO_LEFT) {
                        Gdx.graphics.width.toFloat()
                    } else {
                        0F
                    }
                    val yPosition = i * heightOfSprite

                    windSprite.setPosition(xPosition, yPosition)
                }
            }
            Orientation.UP, Orientation.DOWN -> {
                val spritesRequiredInUpDownOrientation = Gdx.graphics.width / widthOfSprite

                for (i in 0 until spritesRequiredInUpDownOrientation.toInt()) {
                    val windSprite = Sprite(windTexture)
                    windSprites.add(windSprite)

                    val yPosition = if (orientation == Orientation.DOWN) {
                        Gdx.graphics.height.toFloat()
                    } else {
                        0F
                    }
                    val xPosition = i * widthOfSprite

                    windSprite.setPosition(xPosition, yPosition)
                }
            }
        }*/
    }

    override fun update(camera: OrthographicCamera, delta: Float, environment: Environment) {
        /*when (orientation) {
            Orientation.LEFT_TO_RIGHT -> {
                windSprites.forEach { it.translate(velocity, 0F) }
            }
            Orientation.RIGHT_TO_LEFT -> {
                windSprites.forEach { it.translate(-velocity, 0F) }
            }
            Orientation.DOWN -> {
                windSprites.forEach { it.translate(0F, -velocity) }
            }
            Orientation.UP -> {
                windSprites.forEach { it.translate(0F, velocity) }
            }
        }*/
    }

    override fun render(spriteBatch: SpriteBatch, shapeRenderer: ShapeRenderer) {
        /*windSprites.forEach {
            it.draw(spriteBatch)
        }*/
    }

    override fun getX() = error("get x cannot be invoked here.")

    override fun getY() = error("get x cannot be invoked here.")

    override fun getAngle() = angle

    override fun getVelocity() = velocity

    fun setAngle(angle: Float) {
        this.angle = angle
    }

    fun setVelocity(velocity: Float) {
        this.velocity = angle
    }
}
