import Currency.*
import java.util.Scanner

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    // put your code here
    val countryName1 = input.next()
    val countryName2 = input.next()

    val country1 = Country.byName(countryName1.toUpperCase())
    val country2 = Country.byName(countryName2.toUpperCase())

    println(country1 != null && country2 != null &&
            country1.currency == country2.currency)
}

enum class Currency {
    EUR, CFA, ECD, CAD, AUD, BRL
}

enum class Country(val currency: Currency) {
    GERMANY(EUR),
    MALI(CFA),
    DOMINICA(ECD),
    CANADA(CAD),
    SPAIN(EUR),
    AUSTRALIA(AUD),
    BRAZIL(BRL),
    SENEGAL(CFA),
    FRANCE(EUR),
    GRENADA(ECD),
    KIRIBATI(AUD);

    companion object {
        private val valuesMap = values()
                .map { it.name to it }
                .toMap()

        fun byName(name: String) = valuesMap[name]
    }
}