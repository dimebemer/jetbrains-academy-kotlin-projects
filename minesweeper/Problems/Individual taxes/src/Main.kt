import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // write your code here
    val n = scanner.nextInt()
    val incomes = IntArray(n) { scanner.nextInt() }
    val taxes = IntArray(n) { scanner.nextInt() }

    val companyWhoPaysMore = incomes
            .zip(taxes)
            .withIndex()
            .maxBy { it.value.first * it.value.second }!!
            .index + 1

    println(companyWhoPaysMore)
}