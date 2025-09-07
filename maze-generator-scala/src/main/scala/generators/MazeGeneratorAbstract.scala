package generators
import misc.MapLogger

type MazeType = Array[Array[Boolean]]

abstract class MazeGeneratorAbstract {
    def getDims: (Int, Int);
    def getMaze(logger: Option[MapLogger]): MazeType;
}
