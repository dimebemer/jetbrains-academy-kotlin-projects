data class City(val name: String) {
    var degrees: Int = 0
        set(value) =
            if (value >= -92 && value <= 57) {
                field = value
            } else {
                field = CityData.averageTempByName(name)
            }
}

fun main() {
    val first = readLine()!!.toInt()
    val second = readLine()!!.toInt()
    val third = readLine()!!.toInt()
    val firstCity = City("Dubai")
    firstCity.degrees = first
    val secondCity = City("Moscow")
    secondCity.degrees = second
    val thirdCity = City("Hanoi")
    thirdCity.degrees = third

    val cities = listOf(firstCity, secondCity, thirdCity)

    val coldest = cities
            .minBy { it.degrees }
            ?.takeIf {
                cities.filter { c -> c.degrees == it.degrees }.size == 1
            }

    print(coldest?.name ?: "neither")
}

enum class CityData(val averageTemp: Int) {
    DUBAI(30),
    MOSCOW(5),
    HANOI(20);

    companion object {
        fun averageTempByName(name: String) = valueOf(name.toUpperCase()).averageTemp
    }
}