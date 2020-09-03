import java.util.Scanner

private const val SIZE = 5
private const val SHIPS = 3

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)

    val battlefield = Array(SIZE) { Array(SIZE) { false } }

    repeat(SHIPS) {
        val x = input.nextInt()
        val y = input.nextInt()

        battlefield[x - 1][y - 1] = true
    }

    val rows = (1..SIZE).toMutableList()
    val cols = (1..SIZE).toMutableList()

    for (x in 0 until SIZE) {
        for (y in 0 until SIZE) {
            if (battlefield[x][y]) {
                rows.remove(x + 1)
                cols.remove(y + 1)
            }
        }
    }

    println(rows.joinToString(" "))
    println(cols.joinToString(" "))
}