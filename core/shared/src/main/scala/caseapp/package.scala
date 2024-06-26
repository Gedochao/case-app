/** caseapp root package
  *
  * Simply importing things directly under this package should make it possible not to have to
  * import things from [[caseapp.core]].
  */
package object caseapp {

  type ExtraName = Name
  val ExtraName = Name

  type Parser[T] = core.parser.Parser[T]
  val Parser = core.parser.Parser

  type Help[T] = core.help.Help[T]
  val Help = core.help.Help

  type CaseApp[T] = core.app.CaseApp[T]
  val CaseApp = core.app.CaseApp

  type Command[T]         = core.app.Command[T]
  type CommandsEntryPoint = core.app.CommandsEntryPoint

  type RemainingArgs = core.RemainingArgs
  val RemainingArgs = core.RemainingArgs

  type Last[T] = core.argparser.Last[T]
  val Last = core.argparser.Last

  // Running into weird errors with this one when using Tag.of, so let's use newtype below instead
  // type @@[+T, Tag] = shapeless.tag.@@[T, Tag]
  // object Tag {
  //   def of[Tag] = shapeless.tag[Tag]
  //   def unwrap[T, Tag](t: T @@ Tag): T = t.asInstanceOf[T]
  // }

  // Custom tag implementation, see above for more details
  final case class @@[T, Tag](value: T) extends AnyVal
  type Tag = caseapp.annotation.Tag
  object Tag {
    def apply(name: String): Tag =
      caseapp.annotation.Tag(name)
    def unapply(tag: Tag): Option[String] =
      Some(tag.name)

    final class TagBuilder[Tag] {
      def apply[T](t: T): T @@ Tag = @@(t)
    }

    def of[Tag]: TagBuilder[Tag]       = new TagBuilder[Tag]
    def unwrap[T, Tag](t: T @@ Tag): T = t.value
  }

  type Counter = core.Counter

}
