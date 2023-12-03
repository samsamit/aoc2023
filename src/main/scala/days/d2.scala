import utils.Parse
import scala.util.matching.Regex
import utils.GetDailyInput
import java.util.regex.Pattern
import scala.collection.mutable.ListBuffer

object D2 extends App {
  var n: Null = null
  var data = GetDailyInput.getInput(2)
  var res1 = D2.solve1(data)
  var res2 = D2.solve2(data)
  println(s"D2 part1 result: $res1")
  println(s"D2 part2 result: $res2")

  def solve1(input: String): Number = {
    var data = Parse.parseInput(input)
    var gamesData = data.map(_.stripMargin('|').replaceAll(raw"^(Game \d:)", "").trim).map(_.split(";").map(_.trim.split(",").map(_.trim).toList).toList).toList
    var games = gamesData.zipWithIndex.map {case(g,i) => {
      var gameFailed = checkGame(g)
      gameFailed match {
        case true => 0
        case false => i+1
      }
    }}
    return games.sum
  }

  def solve2(input: String): Number = {
    var data = Parse.parseInput(input)
    var gamesData = data.map(_.stripMargin('|').replaceAll(raw"^(Game \d+:)", "").trim).map(_.split(";").map(_.trim.split(",").map(_.trim).toList).toList).toList
    var games = gamesData.zipWithIndex.map {case(g,i) => {
      g.foreach(t => println(s"turns: $t"))
      var reds = g.flatMap(turns => turns.filter(t => t.matches(".*red.*"))).map(raw"\d+".r findFirstMatchIn _).map(_.get.toString.toInt).max
      var blues = g.flatMap(turns => turns.filter(t => t.matches(".*blue.*"))).map(raw"\d+".r findFirstMatchIn _).map(_.get.toString.toInt).max
      var greens = g.flatMap(turns => turns.filter(t => t.matches(".*green.*"))).map(raw"\d+".r findFirstMatchIn _).map(_.get.toString.toInt).max
      reds * blues * greens
    }}
    return games.sum
  }

  def checkGame(game: List[List[String]]): Boolean = {
    var turns = game.map(checkTurns(_))
    return turns.contains(true)
  }

  def checkTurns(turns: List[String]): Boolean = {
    var turn = turns.map(checkTurn(_))
    return turn.contains(true)
  }

  def checkTurn(gameTurn: String): Boolean = {
    val pattern = """(\d+)\s+(\w+)""".r
    val matches = pattern.findAllMatchIn(gameTurn).toList
    val colorNumberMap: List[Boolean] = matches.map { matchResult =>
      val number = matchResult.group(1).toInt
      val color = matchResult.group(2)
      color match {
        case "red" => number > 12
        case "blue" => number > 14
        case "green" => number > 13
        case _ => false
      }
    }
    return colorNumberMap.contains(true)
  }
}
