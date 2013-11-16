import filegrader.gradeAttempt
import java.io.File

/**
 * grading_tools
 * User: Brendan Higgins
 * Date: 11/15/13
 * Time: 11:36 PM
 */
object FileGrader {
  def main(args: Array[String]): Unit =
    if (args.length != 1)
      println("Please provide only the name of the attempt directory")
    else
      gradeAttempt(new File(args(0)))
}
