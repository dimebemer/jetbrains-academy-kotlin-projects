val fixed = intArrayOf(1, 10, 100)
var counter = 0

val numbers = IntArray(100) {
    if (++counter in fixed) counter else 0
}