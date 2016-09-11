package tutor

import tutor.utils.FileUtil
import tutor.utils.FileUtil._

case class CodebaseInfo(fileTypeNums: Map[String, Int], totalLineCount: Int, avgLineCount: Double,
                        longestFileInfo: SourceCodeInfo,
                        top10Files: Seq[SourceCodeInfo]
                       )

trait CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer =>

  def analyze(path: Path): CodebaseInfo = {
    val files = scan(path)
    val sourceCodeInfoes: Seq[SourceCodeInfo] = files.map(processFile)
    val avgLineCount = sourceCodeInfoes.map(_.count).sum.toDouble / files.length
    CodebaseInfo(countFileTypeNum(files), totalLineCount(sourceCodeInfoes),avgLineCount, longestFile(sourceCodeInfoes), top10Files(sourceCodeInfoes))
  }

  private[tutor] def countFileTypeNum(files: Seq[Path]): Map[String, Int] = {
    files.groupBy(FileUtil.extractExtFileName).mapValues(_.length)
  }

  private[tutor] def longestFile(sourceCodeInfos: Seq[SourceCodeInfo]): SourceCodeInfo = {
    sourceCodeInfos.sortBy(_.count).last
  }

  private[tutor] def top10Files(sourceCodeInfos: Seq[SourceCodeInfo]): Seq[SourceCodeInfo] = {
    sourceCodeInfos.sortBy(_.count).reverse.take(10)
  }

  private[tutor] def totalLineCount(sourceCodeInfos: Seq[SourceCodeInfo]): Int = {
    sourceCodeInfos.map(_.count).sum
  }
}
