package board

fun createSquareBoard(width: Int): SquareBoard =
  object : SquareBoard {
    override val width: Int = width

    private val board: Array<Array<Cell>> =
      (1..width).map { i -> (1..width).map { j -> Cell(i, j) }.toTypedArray() }.toTypedArray()

    override fun getCellOrNull(i: Int, j: Int): Cell? = when {
      (i in 1..width) && (j in 1..width) -> board[i - 1][j - 1]
      else -> null
    }

    override fun getCell(i: Int, j: Int): Cell = when {
      i <= width && j <= width -> board[i - 1][j - 1]
      else -> throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> =
      (1..width).flatMap { i -> (1..width).map { j -> board[i - 1][j - 1] } }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
      jRange.filter { it <= width }.map { j -> board[i - 1][j - 1] }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
      iRange.filter { it <= width }.map { i -> board[i - 1][j - 1] }

    override fun Cell.getNeighbour(direction: Direction): Cell? = when (direction) {
      Direction.RIGHT -> getCellOrNull(i, j + 1)
      Direction.LEFT -> getCellOrNull(i, j - 1)
      Direction.UP -> getCellOrNull(i - 1, j)
      Direction.DOWN -> getCellOrNull(i + 1, j)
    }
  }

private class GameBoardImpl<T>(squareBoard: SquareBoard) : GameBoard<T>, SquareBoard by squareBoard {
  private val data = getAllCells().associateWith { null as T? }.toMutableMap()

  override fun get(cell: Cell): T? = data[cell]

  override fun set(cell: Cell, cell2Data: T?): Unit = cell2Data?.let { data[cell] = cell2Data } ?: Unit

  override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
    data.toList().filter { predicate(it.second) }.map { it.first }

  override fun find(predicate: (T?) -> Boolean): Cell? =
    data.toList().find { predicate(it.second) }?.first

  override fun any(predicate: (T?) -> Boolean): Boolean = data.any { predicate(it.value) }

  override fun all(predicate: (T?) -> Boolean): Boolean = data.all { predicate(it.value) }
}

fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(createSquareBoard(width))
