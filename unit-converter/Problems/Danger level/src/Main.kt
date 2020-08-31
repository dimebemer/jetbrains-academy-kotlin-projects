enum class DangerLevel(private val lvl: Int) {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    fun getLevel() = lvl
}