import java.util.*

// write your code here

/* Do not change code below */
fun main() {
    val scanner = Scanner(System.`in`)
    val a = scanner.nextLong()
    val b = scanner.nextLong()
    println(divide(a, b))
}

fun divide(a: Long, b: Long) = a.toDouble() / b
