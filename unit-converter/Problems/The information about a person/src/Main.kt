import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    // put your code here
    val nameAndAge = scanner.nextLine()

    val data = nameAndAge.split(" ")

    val firstNameAbbrev = data[0].first()
    val lastName = data[1]
    val age = data[2].toInt()

    println("$firstNameAbbrev. $lastName, $age years old")
}