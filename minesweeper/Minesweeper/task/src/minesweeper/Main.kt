package minesweeper

import minesweeper.CellState.*
import minesweeper.Game.Companion.height
import minesweeper.Game.Companion.width
import minesweeper.GameCommand.FREE
import minesweeper.GameCommand.MINE
import java.util.*
import kotlin.math.abs

enum class CellState(val symbol: String) {
    HIDDEN("."),
    FREED("/"),
    MARKED("*"),
    MINED("X")
}

enum class GameCommand {
    FREE, MINE;

    companion object {
        fun by(command: String) = valueOf(command.toUpperCase())
    }
}

class Cell(
        private val x: Int,
        private val y: Int,
        val hasMine: Boolean
) {
    var state = HIDDEN
        private set

    var surroundingMines = 0
    val hasSurroundingMines
        get() = surroundingMines > 0

    val symbol
        get() = when (state) {
            HIDDEN -> HIDDEN.symbol
            MARKED -> MARKED.symbol
            MINED -> MINED.symbol
            FREED -> if (hasSurroundingMines) surroundingMines.toString() else FREED.symbol
        }

    private val left by lazy { x == 0 }
    private val right by lazy { x == width - 1 }
    private val bottom by lazy { y == 0 }
    private val top by lazy { y == height - 1 }

    private val cornered by lazy { (left || right) && (bottom || top) }
    private val sided by lazy { (left || right) xor (bottom || top) }

    private val nextX by lazy { abs(x - 1) }
    private val leftX by lazy { x - 1 }
    private val rightX by lazy { x + 1 }

    private val nextY by lazy { abs(y - 1) }
    private val upY by lazy { y + 1 }
    private val downY by lazy { y - 1 }

    val surroundings: Set<Pair<Int, Int>> = when {
        cornered -> setOf(
                Pair(nextX, y),
                Pair(x, nextY),
                Pair(nextX, nextY)
        )
        left || right -> setOf(
                Pair(x, upY),
                Pair(x, downY),
                Pair(nextX, upY),
                Pair(nextX, downY),
                Pair(nextX, y)
        )
        bottom || top -> setOf(
                Pair(leftX, y),
                Pair(rightX, y),
                Pair(leftX, nextY),
                Pair(rightX, nextY),
                Pair(x, nextY)
        )
        else -> setOf(
                Pair(x, downY),
                Pair(x, upY),
                Pair(leftX, y),
                Pair(leftX, downY),
                Pair(leftX, upY),
                Pair(rightX, y),
                Pair(rightX, downY),
                Pair(rightX, upY)
        )
    }

    fun mark() {
        state = MARKED
    }

    fun unmark() {
        state = HIDDEN
    }

    fun free() {
        state = FREED
    }

    fun exposeMine() {
        if (hasMine) state = MINED
    }
}

class Game(
        private val mines: Int
) {

    private val mineField: Array<Array<Cell?>>
    private var gameStarted = false

    val victory: Boolean
        get() {
            val byMine = mineField.flatten()
                    .mapNotNull { it }
                    .groupBy { it.hasMine }

            if (byMine.isEmpty()) return false

            val mined = byMine[true]
            val empty = byMine[false]

            fun allEmptyCellsFreed() = empty!!.all { it.state == FREED }
            fun allMinesMarked() = mined!!.all { it.state == MARKED }
            fun noEmptyCellsMarked() = empty!!.none { it.state == MARKED }

            return allEmptyCellsFreed() || (allMinesMarked() && noEmptyCellsMarked())
        }

    init {
        mineField = initField()
    }

    private fun initField() = Array(height) { arrayOfNulls<Cell>(width) }

    private fun startGame(initialX: Int, initialY: Int) {
        val randoms = (0 until height * width - 1)
                .shuffled()
                .iterator()

        fun isAMine(x: Int, y: Int): Boolean {
            if (initialX == x && initialY == y) return false
            return randoms.next() < mines
        }

        for (y in 0 until height) {
            for (x in 0 until width) {
                mineField[y][x] = Cell(x, y, isAMine(x, y))
            }
        }

        mineField.forEach { row ->
            row.map { cell ->
                cell!!.surroundingMines = cell.surroundings
                        .count { fetchCellByPos(it).hasMine }
            }
        }

        gameStarted = true
    }

    fun printField() {
        fun columns() = " |${(1..width).joinToString("")}|"
        fun row(i: Int, content: String) = "$i|$content|"
        fun line() = "—│${(1..width).joinToString("") { "—" }}|"
        fun String.print() = println(this)

        println()

        columns().print()
        line().print()

        mineField.forEachIndexed { i, row ->
            val content = row.joinToString("") { cell -> cell?.symbol ?: HIDDEN.symbol }
            row(i + 1, content).print()
        }

        line().print()
    }

    fun play(x: Int, y: Int, command: GameCommand) {
        if (!gameStarted) startGame(x, y)

        when (command) {
            FREE -> explore(x, y)
            MINE -> mark(x, y)
        }
    }

    private fun explore(initialX: Int, initialY: Int) {
        val initialCell = fetchCellByPos(initialY, initialX)

        if (initialCell.hasMine) lose()

        val cellsToShow = mutableSetOf(initialCell)
        val exploredCells = mutableSetOf(initialCell)
        var cellsToExplore = setOf<Cell>()

        if (!initialCell.hasSurroundingMines)
            cellsToExplore = setOf(initialCell)

        while (cellsToExplore.isNotEmpty()) {
            val exploring = cellsToExplore
                    .flatMap { it.surroundings }
                    .map { fetchCellByPos(it) }
                    .toSet()
                    .filterNot { it in exploredCells }

            exploredCells.addAll(exploring)
            cellsToShow.addAll(exploring)
            cellsToExplore = exploring
                    .filterNot { it.hasSurroundingMines }
                    .toSet()
        }

        cellsToShow.forEach { it.free() }
    }

    private fun lose() {
        mineField.flatten()
                .requireNoNulls()
                .filter { it.hasMine }
                .forEach { it.exposeMine() }

        throw GameOverException("You stepped on a mine and failed!")
    }

    private fun mark(x: Int, y: Int) {
        val cell = fetchCellByPos(y, x)

        if (cell.state == HIDDEN) cell.mark()
        else if (cell.state == MARKED) cell.unmark()
    }

    private fun fetchCellByPos(y: Int, x: Int) = mineField[y][x]!!

    private fun fetchCellByPos(pos: Pair<Int, Int>): Cell {
        val x = pos.first
        val y = pos.second
        return mineField[y][x]!!
    }

    companion object {
        const val height = 9
        const val width = 9
    }
}

class GameOverException(override val message: String) : Exception(message)

fun main() {
    val scanner = Scanner(System.`in`)

    print("How many mines do you want on the field? ")
    val game = Game(scanner.nextInt())

    while (!game.victory) {
        game.printField()
        print("Set/unset mines marks or claim a cell as free: ")

        val x = scanner.nextInt() - 1
        val y = scanner.nextInt() - 1
        val command = scanner.next()

        try {
            game.play(x, y, GameCommand.by(command))
        } catch (e: GameOverException) {
            println(e.message)
            return
        }
    }

    println("Congratulations! You found all the mines!")
}
