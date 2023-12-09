class D7Test extends org.scalatest.funsuite.AnyFunSuite {
  test("D7p1"){
    var input = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""

    var sol = 6440

    var res = D7.solve1(input)
    assert(res === sol)
  }

  test("D7p2"){
    var input = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""

  // var input = """J2334 1
  // 22334 1
  // J2233 1
  // 22233 1
  // J2345 1
  // 22345 1"""
    var sol = 5905

    var res = D7.solve2(input)
    assert(res === sol)
  }
}