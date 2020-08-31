object PlayingField {
    object Size {
        var width: Int = 0
            private set

        var height: Int = 0
            private set

        fun changeSize(width: Int, height: Int) {
            this.width = getPositiveValueOrZero(width)
            this.height = getPositiveValueOrZero(height)
        }

        private fun getPositiveValueOrZero(value: Int) = if (value > 0) value else 0
    }
}