/**
 * grading_tools
 * User: Brendan Higgins
 * Date: 11/15/13
 * Time: 11:17 PM
 */
package object utils {
  def errorToOption[T](r: => T): Option[T] = try {
    Some(r)
  } catch {
    case _: Throwable => None
  }
}
