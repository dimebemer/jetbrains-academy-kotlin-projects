import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // write your code here
    val n = scanner.nextInt()

    var minimum = Int.MAX_VALUE

    repeat(n) {
        val num = scanner.nextInt()
        if (num < minimum) minimum = num
    }

    println(minimum)
}