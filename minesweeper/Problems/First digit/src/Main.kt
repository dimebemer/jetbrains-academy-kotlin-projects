import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val word = scanner.next()

    println(word.first { it.isDigit() })
}