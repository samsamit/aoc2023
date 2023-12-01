import requests.Response
import utils.GetDailyInput.getInput
object Main extends App {
  var data = getInput(1, null)
  print(data)
}