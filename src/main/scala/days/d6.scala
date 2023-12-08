import utils.GetDailyInput
import utils.Parse
import scala.annotation.meta.getter
object D6 extends App {
  var n: Null = null
  var data = GetDailyInput.getInput(6)
  var res1 = D6.solve1(data)
  var res2 = D6.solve2(data)
  println(s"D6 part1 result: $res1")
  println(s"D6 part2 result: $res2")

  def solve1(input: String): Number = {
    var data = Parse.parseInput(input)
    var times = "[0-9]+".r.findAllIn(data.apply(0)).toList.map(_.toLong)
    var distances = "[0-9]+".r.findAllIn(data.apply(1)).toList.map(_.toLong)
    var races = times.zip(distances).map(x => new Race(x._1, x._2))

    var sols = races.map(r => r.getPossibleRecords())
    println(sols)
    return sols.product
  }

  def solve2(input: String): Number = {
    var data = Parse.parseInput(input)
    var time = data.apply(0).split(":").apply(1).replace(" ","").toLong
    var distance = data.apply(1).split(":").apply(1).replace(" ","").toLong
    var race = new Race(time, distance)

    var sols = race.getPossibleRecords()
    return sols

    return 0
  }

}

// r: 9 t: 7
// r: 40 t: 15

class Race(time: Long, record: Long){
  def getPossibleRecords(): Long = {
    var low = 1L
    var high = time
    while (low < high) {
      val mid = low + Math.floor((high - low) / 2).toLong
      if (((time - mid) * mid) < record) low = mid + 1
      else high = mid
    }
    
    val minTime = if(((time - low) * low) == record) low+1 else low

    low = 1L
    high = time 
    var count: Int = 0
    while (low < high && count < 100) {
      val mid = low + Math.floor((high - low + 1) / 2).toLong
      var x = mid * (time - mid)
      if (x > record || x <= 0) low = mid
      else high = mid - 1
      count += 1
    }
    
    val maxTime = if(((time - low) * low) == record) low-1 else low
    maxTime - minTime + 1
  }
}
