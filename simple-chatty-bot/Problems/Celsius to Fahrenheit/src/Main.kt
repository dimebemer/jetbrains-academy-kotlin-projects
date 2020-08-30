import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    // put your code here

    val celsius = scanner.nextDouble()
    val fahrenheit = celsius * 1.8 + 32

    println(fahrenheit)
}