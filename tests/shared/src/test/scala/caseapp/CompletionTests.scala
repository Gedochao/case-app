package caseapp

import caseapp.core.complete.{Bash, CompletionItem, Fish, Zsh}
import utest._

object CompletionTests extends TestSuite {
  val tests = Tests {
    test("simple") {
      import CompletionDefinitions.Simple._
      test {
        val res = App.complete(Seq("-"), 0)
        val expected = List(
          CompletionItem("--value")
        )
        assert(res == expected)
      }
      test {
        val res = App.complete(Seq("--v"), 0)
        val expected = List(
          CompletionItem("--value")
        )
        assert(res == expected)
      }
      test {
        val res      = App.complete(Seq("--a"), 0)
        val expected = Nil
        assert(res == expected)
      }

      test {
        val res = App.complete(Seq("foo", "a", "--v"), 2)
        val expected = List(
          CompletionItem("--value")
        )
        assert(res == expected)
      }
    }

    test("multiple") {
      import CompletionDefinitions.Multiple._
      test {
        val res = App.complete(Seq("-"), 0)
        val expected = List(
          CompletionItem("--value", Some("A value"), List("-v")),
          CompletionItem("--other", None, List("-n"))
        )
        assert(res == expected)
      }
      test {
        val res = App.complete(Seq("--v"), 0)
        val expected = List(
          CompletionItem("--value", Some("A value"), Nil)
        )
        assert(res == expected)
      }
      test {
        val res      = App.complete(Seq("--a"), 0)
        val expected = Nil
        assert(res == expected)
      }

      test {
        val res = App.complete(Seq("aa", "./f", "--v"), 2)
        val expected = List(
          CompletionItem("--value", Some("A value"), Nil)
        )
        assert(res == expected)
      }
    }

    test("argument value") {
      import CompletionDefinitions.ArgCompletion._
      test {
        val res = App.complete(Seq("-"), 0)
        val expected = List(
          CompletionItem("--value", Some("A value"), List("-v")),
          CompletionItem("--other", None, List("-n"))
        )
        assert(res == expected)
      }
      test {
        val res = App.complete(Seq("--v"), 0)
        val expected = List(
          CompletionItem("--value", Some("A value"), Nil)
        )
        assert(res == expected)
      }
      test {
        val res = App.complete(Seq("-n", "32", "--value", ""), 3)
        val expected = List(
          CompletionItem("32000", None, Nil),
          CompletionItem("32001", None, Nil),
          CompletionItem("32002", None, Nil)
        )
        assert(res == expected)
      }
      test {
        val res = App.complete(Seq("-n", "41", "--value", ""), 3)
        val expected = List(
          CompletionItem("41000", None, Nil),
          CompletionItem("41001", None, Nil),
          CompletionItem("41002", None, Nil)
        )
        assert(res == expected)
      }
    }

    test("commands") {
      import CompletionDefinitions.Commands._
      test {
        val res = Prog.complete(Seq(""), 0)
        val expected = List(
          CompletionItem("back-tick", None, Nil),
          CompletionItem("first", None, Nil),
          CompletionItem("second", None, Nil)
        )
        assert(res == expected)
      }
      test {
        val res = Prog.complete(Seq("f"), 0)
        val expected = List(
          CompletionItem("first", None, Nil)
        )
        assert(res == expected)
      }

      test {
        val res = Prog.complete(Seq("first", "-"), 1)
        val expected = List(
          CompletionItem("--value", Some("A value"), List("-v")),
          CompletionItem("--other", None, List("-n"))
        )
        assert(res == expected)
      }

      test {
        val res = Prog.complete(Seq("second", "-"), 1)
        val expected = List(
          CompletionItem("--glob", Some("A pattern"), List("-g")),
          CompletionItem("--count", None, List("-d"))
        )
        assert(res == expected)
      }

      test("bash") {
        val res = Prog.complete(Seq("back-tick", "-"), 1)
        val expected = List(
          CompletionItem("--backtick", Some("A pattern with backtick `--`\nwith multiline"), Nil),
          CompletionItem("--count", None, List("-d"))
        )
        assert(res == expected)

        val compRely         = Bash.print(res)
        val expectedCompRely = """"--backtick  -- A pattern with backtick \`--\`"""".stripMargin

        assert(compRely.contains(expectedCompRely))
      }
      test("fish") {
        val res              = Prog.complete(Seq("back-tick", "-"), 1)
        val compRely         = Fish.print(res)
        val expectedCompRely = "--backtick\tA pattern with backtick `--`\n".stripMargin

        assert(compRely.contains(expectedCompRely))
      }
      test("zsh") {
        val res = Prog.complete(Seq("back-tick", "-"), 1)
        val expected = List(
          CompletionItem("--backtick", Some("A pattern with backtick `--`\nwith multiline"), Nil),
          CompletionItem("--count", None, List("-d"))
        )
        assert(res == expected)

        val compRely         = Zsh.print(res)
        val expectedCompRely = """"--backtick:A pattern with backtick \`--\`"""".stripMargin

        assert(compRely.contains(expectedCompRely))
      }
    }

    test("commands with default") {
      import CompletionDefinitions.CommandsWithDefault._
      test {
        val res = Prog.complete(Seq(""), 0)
        val expected = List(
          CompletionItem("first", None, Nil),
          CompletionItem("second", None, Nil)
        )
        assert(res == expected)
      }
      test {
        val res = Prog.complete(Seq("f"), 0)
        val expected = List(
          CompletionItem("first", None, Nil)
        )
        assert(res == expected)
      }

      test {
        val res = Prog.complete(Seq("-"), 0)
        val expected = List(
          CompletionItem("--value", Some("A value"), List("-v")),
          CompletionItem("--other", None, List("-n"))
        )
        assert(res == expected)
      }

      test {
        val res = Prog.complete(Seq("first", "-"), 1)
        val expected = List(
          CompletionItem("--value", Some("A value"), List("-v")),
          CompletionItem("--other", None, List("-n"))
        )
        assert(res == expected)
      }

      test {
        val res = Prog.complete(Seq("second", "-"), 1)
        val expected = List(
          CompletionItem("--glob", Some("A pattern"), List("-g")),
          CompletionItem("--count", None, List("-d"))
        )
        assert(res == expected)
      }
    }
  }
}
