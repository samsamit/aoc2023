import utils.GetDailyInput
import utils.Parse
import scala.collection.immutable

object D7 extends App {
  var n: Null = null
  var data = GetDailyInput.getInput(7)
  var res1 = D7.solve1(data)
  var res2 = D7.solve2(data)
  println(s"D7 part1 result: $res1")
  println(s"D7 part2 result: $res2")

  def solve1(input: String): Number = {
    var data = Parse.parseInput(input)
    var hands = data.map(new Hand(_)).sortWith(compareHands(_, _)).reverse.toList
    var winnings = hands.zipWithIndex.map(h => h._1.bid.toInt * (h._2 + 1))

    return winnings.sum
  }

  def solve2(input: String): Number = {
    var data = Parse.parseInput(input)
    var hands = data.map(new Hand(_, true)).sortWith(compareHands(_, _)).reverse.toList
    var winnings = hands.zipWithIndex.map(h => h._1.bid.toInt * (h._2 + 1))

    hands.foreach(h => println(h.cards))
    return winnings.sum
  }

  def compareHands(hand1: Hand, hand2: Hand): Boolean = {
    if(hand1.handRank > hand2.handRank) return true
    if(hand1.handRank < hand2.handRank) return false
    // compare card values
    for(i <- 0 to 4){
      if(hand1.cardValues(i) > hand2.cardValues(i)) return true
      if(hand1.cardValues(i) < hand2.cardValues(i)) return false
    }
    return false
  }
  
}

class Hand(cardsAndBid: String, joker: Boolean = false){
  var cards = cardsAndBid.split(" ").apply(0)
  var cardValues = if(joker) cards.toList.map(c => "J23456789TQKA".indexOf(c))
  else cards.toList.map(c => "23456789TJQKA".indexOf(c))
  var bid = cardsAndBid.split(" ").apply(1)
  var handRank = if (joker) getHandRankWithJoker(cards) else getHandRank(cards)

  def getHandRank(cards: String): Int = {
    var counts = cards.toList.groupBy(identity).map(_._2.size).toList
    // Five of a kind
    if(counts.contains(5)) return 7
    // Four of a kind
    if(counts.contains(4)) return 6
    // Full house
    if(counts.contains(3) && counts.contains(2)) return 5
    // three of a kind
    if(counts.contains(3)) return 4
    // two pair
    if(counts.count(_ == 2) == 2) return 3
    // one pair
    if(counts.count(_ == 2) == 1) return 2
    // high card
    return 1
  }

    def getHandRankWithJoker(cards: String): Int = {
      var jokerCount = cards.toList.count(_ == 'J')
      var cardsWithoutJokers = cards.toList.filter(_ != 'J')
      var cardCounts = cardsWithoutJokers.toList.groupBy(identity).map(_._2.size).toList.sortWith(_ > _)
    
      if(jokerCount == 0) return getHandRank(cards)
      if(jokerCount == 5) return 7
      cardCounts match {
        case head :: next => 
          println( s"head: $head, next: $next, jokerCount: $jokerCount, cards: $cards" )
          // Five of a kind
          if(head + jokerCount >= 5) return 7
          // Four of a kind
          if(head + jokerCount >= 4) return 6
          // Full house
          if(head + jokerCount >= 3 && next.head >= 2) return 5
          // three of a kind
          if(head + jokerCount >= 3) return 4
          // two pair
          if(head + jokerCount >= 2 && next.head >= 2) return 3
          // one pair
          if(head + jokerCount >= 2) return 2
          // high card
          return 1
        case immutable.Nil => 1
      }
    }
}
