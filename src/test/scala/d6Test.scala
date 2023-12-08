class D6Test extends org.scalatest.funsuite.AnyFunSuite {
  test("D6p1"){
    var input = """Time:      7  15   30
Distance:  9  40  200"""
    var sol = 288

    var res = D6.solve1(input)
    assert(res === sol)
  }

  test("D6p2"){
    var input2 = """Time:      7  15   30
Distance:  9  40  200"""
    var sol2 = 71503

    var res2 = D6.solve2(input2)
    assert(res2 === sol2)
  }
}