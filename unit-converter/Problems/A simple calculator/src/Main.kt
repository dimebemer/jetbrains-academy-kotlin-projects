import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // write your code here
    val expr = scanner.nextLine().split(" ")
    val a = expr[0].toLong()
    val operation = expr[1]
    val b = expr[2].toLong()

    println(when (operation) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> if (b == 0L) "Division by 0!" else a / b
        else -> "Unknown operator"
    })
}