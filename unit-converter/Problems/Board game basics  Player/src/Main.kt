class Player(val id: Int, val name: String, val hp: Int) {
    companion object {
        private var nextId = 1

        fun create(name: String): Player = Player(nextId++, name, 100)
    }
}