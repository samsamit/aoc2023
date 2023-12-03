

package utils

object GetDailyInput {
    var sessionToken = "53616c7465645f5fdbc2dd8d821bdc26118959590b5f73b1d6ad200e3bb0de7cbe88669f5a2be0d5e5c4a88ff153bc3af610ce2875d3069e1a6c14e7fc0e1aaa"

    def getInput(day: Int):String = {
        var r = requests.get(s"https://adventofcode.com/2023/day/$day/input", headers = Map("Cookie" -> s"session=$sessionToken"))
        return r.text
    }
}