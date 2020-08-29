import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // write your code here
    val n = scanner.nextInt()

    var max = 1
    var product = 0

    repeat(n) {
        val num = scanner.nextInt()
        val newProduct = num * max

        if (newProduct > product) {
            product = newProduct
            if (num > max) max = num
        }
    }

    println(product)
}