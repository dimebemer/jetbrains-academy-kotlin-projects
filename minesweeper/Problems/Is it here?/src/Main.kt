import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // write your code here
    val size = scanner.nextInt()
    val array = IntArray(size) { scanner.nextInt() }
    val element = scanner.nextInt()

    println(if (array.contains(element)) "YES" else "NO")
}