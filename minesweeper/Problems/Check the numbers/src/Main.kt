import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // write your code here
    val size = scanner.nextInt()
    val array = IntArray(size) { scanner.nextInt() }
    val n = scanner.nextInt()
    val m = scanner.nextInt()

    for (i in 0 until array.lastIndex) {
        val a = array[i]
        val b = array[i + 1]

        if (a == n && b == m || a == m && b == n) {
            println("NO")
            return
        }
    }

    println("YES")
}