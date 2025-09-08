package jscode
import misc._

import indigo.*
import indigo.syntax.*
import scala.scalajs.js.annotation.JSExportTopLevel

type CoordData = (Int, Int)

class TheGame(inputData: CoordData)
    extends IndigoSandbox[CoordData, TheGame.Model] {

    override def config: GameConfig = GameConfig.default
        .withViewport(400, 400)
        .withFrameRateLimit(15)
        .noResize

    val assetName = AssetName("dots")

    override def assets: Set[AssetType] = Set.empty

    override def fonts: Set[FontInfo] = Set.empty

    override def animations: Set[Animation] = Set.empty

    override def shaders: Set[Shader] = Set.empty

    override def setup(
        assetCollection: AssetCollection,
        dice: Dice
    ): Outcome[Startup[CoordData]] =
        Outcome(Startup.Success(inputData))

    override def initialModel(inputData: CoordData): Outcome[TheGame.Model] =
        val (x, y) = inputData
        Outcome(
          TheGame.Model(
            Array.ofDim(x, y)
          )
        )

    override def updateModel(
        context: FrameContext[CoordData],
        model: TheGame.Model
    ): GlobalEvent => Outcome[TheGame.Model] =
        _ => Outcome(model)

    override def present(
        context: FrameContext[CoordData],
        model: TheGame.Model
    ): Outcome[SceneUpdateFragment] =
        val xdim = model.array.length
        val ydim = model.array(0).length
        val width = config.viewport.width / xdim
        val height = config.viewport.height / ydim
        val boxDims = Rectangle(1, 1, width / 2, height / 2)
        Outcome(
          SceneUpdateFragment(
            (Range(0, xdim)
                .cross(Range(0, ydim)))
                .map((x, y) =>
                    Shape
                        .Box(boxDims, Fill.Color(RGBA.White))
                        .moveTo(
                          x * height + height / 4,
                          y * width + width / 4
                        )
                )
                .toList
                .toBatch
          )
        )
}

object TheGame {

    case class Model(array: Array[Array[Int]])

}
