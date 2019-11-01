package org.mechdancer.simulation.map.shape

import org.mechdancer.algebra.function.vector.norm
import org.mechdancer.algebra.function.vector.times
import org.mechdancer.algebra.implement.vector.Vector2D
import org.mechdancer.geometry.angle.toRad
import org.mechdancer.geometry.angle.toVector
import kotlin.math.PI
import kotlin.math.sin
import kotlin.math.sqrt

/** 半径为 [radius] 的正圆 */
class Circle(val radius: Double, val sampleCount: Int = 16) : AnalyticalShape {
    override val size = PI * radius * radius
    override fun contains(p: Vector2D) = p.norm() < radius
    override fun sample() = sequence {
        // 步进角
        val theta = 2 * PI / sampleCount
        // 等价半径
        val equivalent = radius / sqrt(sin(theta) / theta)
        // 生成
        for (i in 0 until sampleCount)
            yield((i * theta).toRad().toVector() * equivalent)
    }
}
