package com.ant00000ny

operator fun Pair<Int, Int>.times(
    x: Int
): Pair<Int, Int> {
    return Pair(first * x, second * x)
}
