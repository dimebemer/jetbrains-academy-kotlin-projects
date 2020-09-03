import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val word = scanner.next()

    val count = word.groupBy { it }
            .count { it.value.size == 1 }

    println(count)
}