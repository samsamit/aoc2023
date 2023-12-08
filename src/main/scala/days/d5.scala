import utils.GetDailyInput
import utils.Parse
import scala.annotation.meta.getter
object D5 extends App {
  var n: Null = null
  var data = GetDailyInput.getInput(5)
  var res1 = D5.solve1(data)
  var res2 = D5.solve2(data)
  println(s"D5 part1 result: $res1")
  println(s"D5 part2 result: $res2")

  def solve1(input: String): Number = {
    var data = Parse.parseInput(input)
    var seeds = getNumberListFromLine(data.apply(0).split(":").apply(1))
    var maps = getMapsFromData(data.drop(1).toList)

    var minLocation: Long = 0
    seeds.foreach(seed => {
        var soil = tryConversion(seed, maps.getOrElse("seed-to-soil map:", List()))
        var fertilizer = tryConversion(soil, maps.getOrElse("soil-to-fertilizer map:", List()))
        var water = tryConversion(fertilizer, maps.getOrElse("fertilizer-to-water map:", List()))
        var light = tryConversion(water, maps.getOrElse("water-to-light map:", List()))

        var temperature = tryConversion(light, maps.getOrElse("light-to-temperature map:", List()))
        var humidity = tryConversion(temperature, maps.getOrElse("temperature-to-humidity map:", List()))
        var location = tryConversion(humidity, maps.getOrElse("humidity-to-location map:", List()))

        minLocation = if (location < minLocation || minLocation == 0) location else minLocation
    })
    return minLocation
  }

  def solve2(input: String): Number = {
    var data = Parse.parseInput(input)
    var seedsData = getNumberListFromLine(data.apply(0).split(":").apply(1))

    var seedSettings: List[(Long, Long)] = List()
    def traverseSeedSettings(index: Int){
        var seedStart = seedsData.apply(index)
        var seedEnd = seedsData.apply(index+1) - seedStart
        seedSettings = seedSettings.concat(List((seedStart, seedEnd)))
        traverseSeedSettings(index+2)
    }
    traverseSeedSettings(0)

    return 0
  }

  def getNumberListFromLine(line: String): List[Long] = {
    return line.split(" ").filter(x => x != "").map(_.trim.toLong).toList
  }

  def tryConversion(id: Long, maps: List[NumsMap]): Long = {
    maps.foreach(map => {
        var res = map.convert(id)
        if(res.isDefined){
            return res.get
        }
    })
    return id
  }

  def getMapsFromData(data: List[String]): Map[String, List[NumsMap]] = {
    var maps: Map[String, List[NumsMap]] = Map()
    var takeName = false
    var name = ""
    data.foreach(line => {
        if(line.length == 0 && !takeName){
            takeName = true
        }
        else if(takeName){
            maps = maps.updated(line, List())
            name = line
            takeName = false
        }
        else if(!takeName){
            var numbers = getNumberListFromLine(line)
            var prevMap = maps.getOrElse(name, List())
            var nums = new NumsMap(numbers)
            var newMap = prevMap.appended(nums)
            maps = maps.updated(name, newMap)
        }
    })
    return maps
  }
}

class NumsMap(setup: List[Long]){
    private var destStart = setup.apply(0)
    private var sourceStart = setup.apply(1)
    private var loop = setup.apply(2)
    
    def convert(id: Long): Option[Long] = {
        if(id < sourceStart || id > sourceStart+loop){
            return None
        }
        var diff = id - sourceStart
        var res = destStart + diff
        return Some(res)
    }
}