import java.io.{FileOutputStream, FileInputStream, File}
import java.nio.file.Files
import scala.sys.process._
import utils._


/**
 * grading_tools
 * User: Brendan Higgins
 * Date: 11/15/13
 * Time: 11:01 PM
 */
package object filegrader {
  val attemptGradeFileName = "GRADE_FILE"
  val attemptGradeFileTemplateName = "GRADE_FILE_TEMPLATE"
  def openWithEditorAndBlock(files: File*): Unit = {
    val console = System.console()
    (Seq("vim", "-O") ++ files.map(_.toString)) #> console.writer() #< input !
  }

  def gradeAttempt(attemptDirectory: File): Unit = for {
    attemptGradeFile <- writeAttemptGradeFile(attemptDirectory)
    file <- attemptDirectory.listFiles()
  } openWithEditorAndBlock(file, attemptGradeFile)

  def writeAttemptGradeFile(attemptDirectory: File): Option[File] = errorToOption {
    val attemptGradeFile = new File(attemptDirectory, attemptGradeFileName)
    val attemptGradeFileTemplate = new File(attemptGradeFileTemplateName)
    Files.copy(attemptGradeFileTemplate.toPath, attemptGradeFile.toPath)
    attemptGradeFile
  }
}
