import utils.Parse
import utils.GetDailyInput

object D3 extends App {

  var n: Null = null
  var data = GetDailyInput.getInput(3)
  var res1 = D3.solve1(data)
  var res2 = D3.solve2(data)
  println(s"D3 part1 result: $res1")
  println(s"D3 part2 result: $res2")

  class EngineParts(data: Array[String]){
    val parser = "[0-9]+|.|[=&#+\\/*%\\-@$]".r
    var rawMatrix = data.map(_.split("").toList).toList
    var matrix = data.map(parser.findAllIn(_).toList).toList
    println(s"rows: ${rawMatrix.length} cols: ${rawMatrix.apply(0).length}")

    def hasSymbolNeighbor(targetRow: Int, col: Int, colOffset: Int): Boolean = {
      var row = matrix.apply(targetRow)
      val num = matrix.apply(targetRow).apply(col)
      var actualCol = col+colOffset
      var cols = (actualCol-1, actualCol + num.length+1)
      if(targetRow - 1 >= 0 && checkRowForSymbol(targetRow-1, cols)) return true
      if(targetRow + 1 < matrix.length && checkRowForSymbol(targetRow+1, cols)) return true
      if(col - 1 >= 0 && row.apply(col-1).matches("[=&#+\\/*%\\-@$]")) return true
      if(col + 1 < row.length && row.apply(col+1).matches("[=&#+\\/*%\\-@$]")) return true
      return false

    }
    private def checkRowForSymbol(targetRow: Int, cols: (Int, Int)): Boolean = {
      var row = rawMatrix.apply(targetRow).slice(Math.max(0, cols._1), cols._2).toList
      var symbols = row.find(s => s.matches("[=&#+\\/*%\\-@$]"))
      return symbols match {
          case Some(value) => true
          case None => false
        }
    }
    def getAllSymbols(): String = {
      var symbolSet: Set[String] = Set()
      rawMatrix.flatMap(_.toList).toList.foreach(str => symbolSet = symbolSet.+(str))
      return symbolSet.toList.filter(s => !s.matches("[0-9]")).mkString
    }
    def getNumberNeighbors(row: Int, col: Int): Option[List[Int]] = {
      val cols = (Math.max(0,col-1), col+1)
      var nums :List[Int] = List()
      if(row - 1 >= 0){
        rawMatrix.apply(row-1).zipWithIndex.foreach(x => {
          if(x._2 >= cols._1 || x._2 <= cols._2){
            if(x._1.matches("[0-9]")){
              findNumber(row, x._2) match {
                case Some(value) => nums = nums.:+(value)
                case None => None
              }
            }
          }
        })
      }
      if(row + 1 <= matrix.length){
        rawMatrix.apply(row+1).zipWithIndex.foreach(x => {
          if(x._2 >= cols._1 || x._2 <= cols._2){
            if(x._1.matches("[0-9]")){
               findNumber(row, x._2) match {
                case Some(value) => nums = nums.:+(value)
                case None => None
              }
            }
          }
        })
      }
      if(col - 1 >= 0){
        rawMatrix.apply(row).zipWithIndex.foreach(x => {
        findNumber(row, col-1) match {
          case Some(value) => nums = nums.:+(value)
          case None => None
        }
        })
      }
      if(col + 1 <= rawMatrix.apply(row).length){
        rawMatrix.apply(row).zipWithIndex.foreach(x => {
          findNumber(row, col+1) match {
            case Some(value) => nums = nums.:+(value)
            case None => None
          }
        })
      }
      if(nums.length == 2){
        return Some(nums)
      }
      return None
    }
    private def findNumber(row: Int, col: Int): Option[Int] = {
      var targetRow = matrix.apply(row)
      var num = targetRow.zipWithIndex.find(n => n._2 >= col && n._1.length + n._2 <= col)
      return num match {
        case Some(value) => return Some(value._2)
        case None => None
      }
    }
  }

  def solve1(input: String): Number = {
    var data = Parse.parseInput(input)
    var parts = new EngineParts(data)
    println(parts.getAllSymbols())
    def checkRow(row: List[String], index: Int): List[Int] = {
      var colOffset: Int = 0
      
      def checkCell(num: String, row: Int, col: Int): Int = {
        if(num.matches("[0-9]+")){
          val hasNeighbor = parts.hasSymbolNeighbor(row, col, colOffset)
          colOffset += num.length-1
          return hasNeighbor match {
            case true => num.toInt
            case false => 0
          }
        }
        return 0
      }
      return row.zipWithIndex.map(col => checkCell(col._1, index, col._2))
    }

    var hasSymbol = parts.matrix.zipWithIndex.flatMap(row => checkRow(row._1, row._2)).filter(_ != 0)
    return hasSymbol.sum

  }

  def solve2(input: String): Number = {
    var data = Parse.parseInput(input)
     var parts = new EngineParts(data)
     var cellMap = parts.rawMatrix.zipWithIndex
      .flatMap(row => row._1.zipWithIndex
      .map(cell => new Cell(row._2, cell._2, cell._1, cell._1.matches("[=&#+\\/*%\\-@$]") match {
          case false => None
          case true => parts.getNumberNeighbors(row._2, cell._2)
        }
     )))

     cellMap.foreach(cell => println(cell.toString))
     var nums = cellMap.map(cell => cell.getNeighbourPower())

     println(nums)
     return nums.sum
  }
}

class Cell(row: Int, col: Int, value: String, neighbours: Option[List[Int]]){
  def getNeighbourPower(): Int = {
    var values = neighbours.getOrElse(List())
    if(values.length == 2) return values.product
    else return 0
  }
  override def toString = s"row: $row, col: $col, value: $value, neighbours: $neighbours"
}