class City(val name: String) {
    var population: Int = 0
        set(value) = when {
            value < 0 -> field = 0
            value > 50_000_000 -> field = 50_000_000
            else -> field = value
        }
}