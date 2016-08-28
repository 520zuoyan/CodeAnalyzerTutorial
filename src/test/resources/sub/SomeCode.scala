package tutor

class SourceCode(val path: String, val name: String, private val lines: List[String]) {
  def count: Int = lines.length
}

object SourceCode {
  def fromFile(path: Path): SourceCode = {
    import scala.io._

    val source = Source.fromFile(path)
    val lines = source.getLines.toList
    new SourceCode(path, extractLocalPath(path), lines)
  }

}