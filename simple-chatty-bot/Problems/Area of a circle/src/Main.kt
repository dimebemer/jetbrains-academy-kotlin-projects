import java.util.*
import kotlin.math.pow

const val PI = 3.1415

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    // write your code here

    val radius = scanner.nextDouble()

    fun calculateCircleArea() = PI * radius.pow(2)

    println(calculateCircleArea())
}