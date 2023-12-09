import utils.GetDailyInput
import utils.Parse
import scala.collection.immutable

object D8 extends App {
  var n: Null = null
  var data = GetDailyInput.getInput(8)
  var res1 = D8.solve1(data)
  var res2 = D8.solve2(data)
  println(s"D8 part1 result: $res1")
  println(s"D8 part2 result: $res2")

  def solve1(input: String): Number = {
    var data = Parse.parseInput(input)
    var (instructionString, mapStrings) = data.splitAt(data.indexWhere(_ == "")) match {
      case (a, b) => (a, b.filter(_ != "").toList)
    }
    var instructions = instructionString.mkString.replace("\n", "").trim.toList

    var map = getMap(mapStrings.toList)
    var next = "AAA"
    var count = 0
    while(next != "ZZZ"){
      var direction = instructions(count % instructions.length)
      var (left, right) = map(next)
      next = if (direction == 'R') right else left
      count += 1
    }
    return count
  }

  def solve2(input: String): Number = {
    var data = Parse.parseInput(input)
    var (instructionString, mapStrings) = data.splitAt(data.indexWhere(_ == "")) match {
      case (a, b) => (a, b.filter(_ != "").toList)
    }
    var instructions = instructionString.mkString.replace("\n", "").trim.toList
    println( instructions)
    var map = getMap(mapStrings.toList)
    var positions: List[(String, String)] = map.filter(x => x._1.charAt(2) == 'A')
        .map(x => (x._1, getNext(instructions(0), x._2))).toList
    var count = 1
    var run = true
    // This takes forever... Maybe I should have used a more advanced graph or tree system... 
    while(run){
      var direction = instructions(count % instructions.length)
      positions = positions.map(x => (x._2, getNext(direction, map(x._2)) ))
      
      var ready = positions.map(_._1.charAt(2)).forall(x => x == 'Z')
      if (ready) run = false
      else count += 1
    }
    return count
  }

  def getNext(dir: Char, lr: (String, String)): String = {
    return if (dir == 'R') lr._2 else lr._1
  }
  
  def getMap(input: List[String]): Map[String, (String, String)] = {
    var keyValues = input.map(_.split(" = ").toList).toList
    var mapStrings = keyValues.map( x => (x(0), x(1).split(", ").toList)).toList
    var map = mapStrings.map( x => x._1 -> (x._2(0).replace("(", ""), x._2(1).replace(")", ""))).toMap

    return map
  }
}

