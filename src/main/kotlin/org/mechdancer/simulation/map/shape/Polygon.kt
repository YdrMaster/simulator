package org.mechdancer.simulation.map.shape

import org.mechdancer.algebra.function.vector.minus
import org.mechdancer.algebra.implement.vector.Vector2D

/** 多边形 */
class Polygon(val vertex: List<Vector2D>) : Shape {
    override val size by lazy {
        if (vertex.size < 3) .0
        else {
            var last = vertex[0]
            vertex.drop(1)
                .sumByDouble {
                    val another = last
                    last = it
                    another cross it
                } / 2
        }
    }

    override operator fun contains(p: Vector2D): Boolean =
        if (vertex.size < 3) false
        else {
            val (_, y) = p
            var p0 = vertex.last()
            vertex
                .asSequence()
                .count {
                    val (_, y0) = p0
                    val (_, y1) = it
                    when (y) {
                        in y0..y1 -> p - p0 cross it - p0 >= 0
                        in y1..y0 -> p - it cross p0 - it >= 0
                        else      -> false
                    }.also { p0 = p }
                } % 2 == 1
        }

    fun intersect(segment: Segment): List<Vector2D> {
        var begin = vertex.last()
        return vertex.mapNotNull { end ->
            (begin..end intersect segment).also { begin = end }
        }
    }
}
