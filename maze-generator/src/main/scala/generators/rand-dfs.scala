package generators
import scala.util.Random
import misc.*

case class RandomizedDFSGen(val rows: Int, val cols: Int)
    extends MazeGeneratorAbstract {
    var shouldBeRecursive: Boolean = true
    def getDims = (rows, cols)

    def getMaze(logger: Option[MapLogger]): MazeType = {
        var maze = Array.ofDim[Boolean](rows, cols)
        recursiveDFS((0, 0), maze)
        maze
    }

    private def recursiveDFS(cell: (Int, Int), maze: MazeType): Unit = {
        val (row, col) = cell
        maze(row)(col) = true
        val coords = List(0)
            .cross(List(-1, 1))
            .concat(List(-1, 1).cross(List(0)))
            .map((x, y) =>
                (
                  row + x,
                  col + y
                )
            )
            .filter((x, y) =>
                x > 0 && y > 0 && x < rows && y < rows && !maze(x)(y)
            )
        val shuffled_coords = Random.shuffle(coords.toList).toList
        shuffled_coords.foreach(c => recursiveDFS(c, maze))
    }
}
