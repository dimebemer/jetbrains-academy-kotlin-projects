import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // write your code here
    val phrase = scanner.nextLine()
    val i = scanner.nextInt()

    println("Symbol # $i of the string \"$phrase\" is '${phrase[i - 1]}' ")
}