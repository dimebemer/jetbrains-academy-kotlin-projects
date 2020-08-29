import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    // put your code here
    val n = scanner.nextInt()

    var rejected = 0
    var toFix = 0
    var ready = 0

    repeat(n) {
        when (scanner.nextInt()) {
            -1 -> rejected++
            0 -> ready++
            1 -> toFix++
        }
    }

    println("$ready $toFix $rejected")
}