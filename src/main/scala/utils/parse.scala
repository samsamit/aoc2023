package utils

import scala.util.matching.Regex

object Parse{
    
    def parseInput(data: String ,separator: Option[Regex] = None): Array[String] = {
        var lines = data.linesIterator.toArray
        var parsedLines = separator match {
            case Some(s) => lines.flatMap(s.findAllIn(_).toArray)
            case None => lines.map(_.trim)
        }
        return parsedLines
    }
}