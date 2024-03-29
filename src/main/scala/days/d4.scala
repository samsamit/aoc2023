import utils.Parse
import utils.GetDailyInput

object D4 extends App {
  var n: Null = null
  var data = GetDailyInput.getInput(4)
  var res1 = D4.solve1(data)
  var res2 = D4.solve2(data)
  println(s"D4 part1 result: $res1")
  println(s"D4 part2 result: $res2")

  def solve1(input: String): Number = {
    var data = Parse.parseInput(input)
    var cards = data.map(new Card(_))
    var points = cards.map(card => card.getCardPoints()).toList
    return points.sum
  }

  def solve2(input: String): Number = {
    var data = Parse.parseInput(input)
    var cards = data.map(new Card(_))

    var cardWins = List.range(0, cards.length).map(cards.apply(_).getWinningCardCount())

    var countMap: Map[Int, Int] = Map()

    cardWins.zipWithIndex.foreach(x => {
        var range = List.range(x._2+1, x._2+1 + x._1)

        var thisNumCount = countMap.getOrElse(x._2, 0) + 1
        countMap = countMap.updated(x._2, thisNumCount)
        
        range.foreach(y => {
            var prevCount = countMap.getOrElse(y, 0)
            var newCount = prevCount + (1*thisNumCount)
            countMap = countMap.updated(y, newCount)
        })
    })

    var count = countMap.map(x => x._2).sum
    return count
  }
}

class Card(cardString: String){
    var cardNumbers = cardString.split(":").apply(1)
    var winningNumbers = cardNumbers.split("\\|").apply(0).split(" ").filter(c => c.trim.forall(Character.isDigit) && c.length > 0).map(_.toInt).toList
    var numbers = cardNumbers.split("\\|").apply(1).split(" ").filter(c => c.trim.forall(Character.isDigit) && c.length > 0).map(_.toInt).toList

    def getCardPoints(): Int = {
        var winningNums = numbers.filter(n => winningNumbers.contains(n))
        return winningNums.length match {
            case 0 => 0
            case 1 => 1
            case length: Int => 1 * (scala.math.pow(2, length-1).toInt)
        }
    }

    def getWinningCardCount(): Int = {
        return numbers.filter(n => winningNumbers.contains(n)).length
    }
}