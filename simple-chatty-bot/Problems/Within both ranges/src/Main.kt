import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // write your code here
    val n1 = scanner.nextInt()
    val n2 = scanner.nextInt()
    val n3 = scanner.nextInt()
    val n4 = scanner.nextInt()
    val n5 = scanner.nextInt()

    println(n5 in n1..n2 && n5 in n3..n4)
}