class D3Test extends org.scalatest.funsuite.AnyFunSuite {
  test("d3p1"){
    var input = """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598.."""
    var sol = 4361

    var res = D3.solve1(input)
    assert(res === sol)
  }

  test("d3p2"){
    var input = """
467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598.."""
    var sol = 467835

    var res = D3.solve2(input)
    assert(res === sol)
  }
}