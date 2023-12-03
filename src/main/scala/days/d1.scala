import utils.Parse
import scala.util.matching.Regex
import utils.GetDailyInput
import java.util.regex.Pattern
import scala.collection.mutable.ListBuffer

object D1 extends App {
  var n: Null = null
  var data = GetDailyInput.getInput(1)
  var res1 = D1.solve1(data)
  var res2 = D1.solve2(data)
  println(s"D1 part1 result: $res1")
  println(s"D1 part2 result: $res2")

  def solve1(input: String): Number = {
    var data = Parse.parseInput(input)
    var numbers = data.map(findNumbers(_)).toList
    val total = numbers.sum
    return total
  }

  def solve2(input: String): Number = {
    var data = Parse.parseInput(input)
    var numbers = data.map(findNumbers(_, true)).toList
    val total = numbers.sum
    return total
  }

  def findNumbers(str: String, useWords: Boolean = false): Int = {
    val regex = raw"(?:one|two|three|four|five|six|seven|eight|nine|ten|\d)"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(str)

   val matches = ListBuffer[String]()
    while(matcher.find()){
      val numberMatch = matcher.group()
     matches += numberMatch
      // Adjust the start position to allow overlapping matches
      matcher.region(matcher.start() + 1, str.length())
    }
     val numbers = useWords match {
      case true => wordsToNumbers(matches.toList)
      case false => matches.filter(s => s matches("([0-9])")).map(_.toInt)
    }
    val first = numbers.take(1)(0)
    val last = numbers.takeRight(1)(0)
    return (first.toString + last.toString).toInt
  }

  def wordsToNumbers(strings: List[String]): List[Int] = {
    var numbers = strings.map(_ match {
      case "one" => 1
      case "two" => 2
      case "three" => 3
      case "four" => 4
      case "five" => 5
      case "six" => 6
      case "seven" => 7
      case "eight" => 8
      case "nine" => 9
      case s: String => s.toInt
    })
    return numbers
  }
  
}