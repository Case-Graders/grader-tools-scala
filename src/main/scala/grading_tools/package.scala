import java.io.File
import scala.sys.process._
import scala.util.Try

/**
 * grading_tools
 * User: Brendan Higgins
 * Date: 11/15/13
 * Time: 8:45 PM
 */
package object grading_tools {
  def inflateProject(baseDirectory: File, zipFile: File): Unit = Try {
    unzipFile(zipFile.toString, baseDirectory.toString)
    baseDirectory.listFiles().foreach(moveToAttemptDir(baseDirectory))
  }

  private def unzipFile(zipFile: String, outDirectory: String): Unit = Seq("unzip", zipFile, "-d", outDirectory).!!

  private def moveToAttemptDir(baseDir: File)(file: File): Unit = {
    val regex = ("^" + baseDir + """_(?<case_id>[a-zA-Z0-9]+)_(?<attempt>attempt_[0-9]+)(?<filename>.*)$""").r
    for {
      regex(caseID, attempt, fileName) <- regex findFirstIn file.toString
      cleanFileName <- cleanFileName(fileName)
      caseIDDirectory = new File(baseDir, caseID)
      caseIDDirectory.mkdir()
      attemptDirectory: File = new File(caseIDDirectory, attempt)
      attemptDirectory.mkdir()
      file.renameTo(new File(attempt, cleanFileName))
    } yield ()
  }

  private def cleanFileName(fileName: String): Option[String] =
    if (fileName == ".txt")
      Some("attempt.txt")
    else if (fileName.startsWith("_"))
      Some(fileName.substring(1))
    else
      None
}
