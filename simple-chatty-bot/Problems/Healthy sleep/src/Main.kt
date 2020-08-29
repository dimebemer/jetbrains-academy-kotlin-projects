import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // write your code here
    val min = scanner.nextInt()
    val max = scanner.nextInt()
    val ann = scanner.nextInt()

    println(when {
        ann in min..max -> "Normal"
        ann < min -> "Deficiency"
        else -> "Excess"
    })
}