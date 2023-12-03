class D1Test extends org.scalatest.funsuite.AnyFunSuite {
  test("d1p1"){
    var input = """1abc2
    pqr3stu8vwx
    a1b2c3d4e5f
    treb7uchet"""
    var sol = 142

    var res = D1.solve1(input)
    assert(res === sol)
  }

  test("d1p2"){
    var input2 = """two1nine
      eightwothree
      abcone2threexyz
      xtwone3four
      4nineeightseven2
      zoneight234
      7pqrstsixteen"""
    var sol2 = 281

    var res2 = D1.solve2(input2)
    assert(res2 === sol2)
  }
}