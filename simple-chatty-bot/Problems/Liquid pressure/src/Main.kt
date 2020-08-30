import java.util.*

const val GRAVITY = 9.8

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    // write your code here

    val density = scanner.nextDouble()
    val height = scanner.nextDouble()

    fun calculateLiquidPressure() = GRAVITY * density * height

    println(calculateLiquidPressure())
}