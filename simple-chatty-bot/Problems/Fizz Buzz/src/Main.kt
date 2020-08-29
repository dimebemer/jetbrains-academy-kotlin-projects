import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // write your code here
    val a = scanner.nextInt()
    val b = scanner.nextInt()

    for (i in a..b) {
        var fizzOrBuzz = false

        if (i % 3 == 0) {
            print("Fizz")
            fizzOrBuzz = true
        }

        if (i % 5 == 0) {
            print("Buzz")
            fizzOrBuzz = true
        }

        if (!fizzOrBuzz) print(i)

        println()
    }
}