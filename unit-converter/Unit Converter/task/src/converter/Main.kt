package converter

import java.util.*

private const val UNKNOWN_UNIT = "???"

interface Unit {
    val factor: Double?
    val abbrevs: Array<String>
    val singular: String
    val plural: String

    fun convert(value: Double, newUnit: Unit): Double {
        if (this.enumType() != newUnit.enumType()) {
            throw IllegalArgumentException()
        }

        return this.factor!! * value / newUnit.factor!!
    }

    fun getNameByAmplitude(amplitude: Double) =
            if (amplitude == 1.0) singular else plural
}

enum class LengthUnit(
        override val abbrevs: Array<String>,
        override val singular: String,
        override val plural: String,
        override val factor: Double
) : Unit {
    METER(arrayOf("m"), "meter", "meters", 1.0),
    KILOMETER(arrayOf("km"), "kilometer", "kilometers", 1000.0),
    CENTIMETER(arrayOf("cm"), "centimeter", "centimeters", 0.01),
    MILLIMETER(arrayOf("mm"), "millimeter", "millimeters", 0.001),
    MILE(arrayOf("mi"), "mile", "miles", 1609.35),
    YARD(arrayOf("yd"), "yard", "yards", 0.9144),
    FOOT(arrayOf("ft"), "foot", "feet", 0.3048),
    INCH(arrayOf("in"), "inch", "inches", 0.0254);

    companion object {
        private val valuesMap = buildValuesMap(values())
        fun byName(name: String) = valuesMap[name.toLowerCase()]
    }
}

enum class WeightUnit(
        override val abbrevs: Array<String>,
        override val singular: String,
        override val plural: String,
        override val factor: Double
) : Unit {
    GRAM(arrayOf("g"), "gram", "grams", 1.0),
    KILOGRAM(arrayOf("kg"), "kilogram", "kilograms", 1000.0),
    MILLIGRAM(arrayOf("mg"), "milligram", "milligrams", 0.001),
    POUND(arrayOf("lb"), "pound", "pounds", 453.592),
    OUNCE(arrayOf("oz"), "ounce", "ounces", 28.3495);

    companion object {
        private val valuesMap = buildValuesMap(values())
        fun byName(name: String) = valuesMap[name.toLowerCase()]
    }
}

enum class TemperatureUnit(
        override val abbrevs: Array<String>,
        override val singular: String,
        override val plural: String,
        override val factor: Double?
) : Unit {
    CELSIUS(arrayOf("C", "dc", "celsius"),
            "degree Celsius", "degrees Celsius", null) {
        override fun convert(value: Double, newUnit: Unit) = when (newUnit) {
            CELSIUS -> value
            FAHRENHEIT -> value * 9 / 5 + 32
            KELVIN -> value + 273.15
            else -> throw IllegalArgumentException()
        }
    },
    FAHRENHEIT(arrayOf("F", "df", "fahrenheit"),
            "degree Fahrenheit", "degrees Fahrenheit", null) {
        override fun convert(value: Double, newUnit: Unit) = when (newUnit) {
            CELSIUS -> (value - 32) * 5 / 9
            FAHRENHEIT -> value
            KELVIN -> (value + 459.67) * 5 / 9
            else -> throw IllegalArgumentException()
        }
    },
    KELVIN(arrayOf("K"), "kelvin", "kelvins", null) {
        override fun convert(value: Double, newUnit: Unit) = when (newUnit) {
            CELSIUS -> value - 273.15
            FAHRENHEIT -> value * 9 / 5 - 459.67
            KELVIN -> value
            else -> throw IllegalArgumentException()
        }
    };

    companion object {
        private val valuesMap = buildValuesMap(values())
        fun byName(name: String) = valuesMap[name.toLowerCase()]
    }
}

fun <T : Unit> buildValuesMap(values: Array<T>) = values
        .flatMap {
            val abbrevsMap = it.abbrevs
                    .map { abbrev -> abbrev.toLowerCase() to it }
                    .toTypedArray()

            listOf(
                    *abbrevsMap,
                    it.singular.toLowerCase() to it,
                    it.plural.toLowerCase() to it
            )
        }
        .toMap()

fun main() {

    val scanner = Scanner(System.`in`)

    fun requestValueToConvert(): List<String> {
        print("Enter what you want to convert (or exit): ")
        return scanner.nextLine().split(" ")
    }

    var input = requestValueToConvert()

    while (input.singleOrNull() != "exit") {
        try {
            val value = input.first().toDouble()

            val originalUnit: Unit? = getUnitByName(input[1])
                    ?: getUnitByName("${input[1]} ${input[2]}")

            val newUnit: Unit? = getUnitByName(input.last())
                    ?: getUnitByName("${input[input.lastIndex - 1]} ${input.last()}")

            when {
                originalUnit == null || newUnit == null || originalUnit.enumType() != newUnit.enumType() -> {
                    println("Conversion from ${originalUnit?.plural ?: UNKNOWN_UNIT} " +
                            "to ${newUnit?.plural ?: UNKNOWN_UNIT} is impossible")
                }
                originalUnit is LengthUnit && value < 0 ->
                    println("Length shouldn't be negative")
                originalUnit is WeightUnit && value < 0 ->
                    println("Weight shouldn't be negative")
                else -> {
                    val newValue = originalUnit.convert(value, newUnit)
                    val oldName = originalUnit.getNameByAmplitude(value)
                    val newName = newUnit.getNameByAmplitude(newValue)

                    println("$value $oldName is $newValue $newName")
                }
            }
        } catch (e: Exception) {
            println("Parse error")
        }

        input = requestValueToConvert()
    }
}

fun getUnitByName(name: String) =
        LengthUnit.byName(name) as Unit?
                ?: WeightUnit.byName(name)
                ?: TemperatureUnit.byName(name)

fun Any.enumType() = this::class.supertypes[0]