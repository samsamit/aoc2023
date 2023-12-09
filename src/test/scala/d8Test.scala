class D8Test extends org.scalatest.funsuite.AnyFunSuite {
  test("D8p1"){
    var input = """RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)"""

    var sol = 2

    var res = D8.solve1(input)
    assert(res === sol)
  }

  test("D8p2"){
    var input = """LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)"""
    var sol = 6

    var res = D8.solve2(input)
    assert(res === sol)
  }
}