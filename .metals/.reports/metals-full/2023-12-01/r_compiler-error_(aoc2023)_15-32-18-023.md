file://<WORKSPACE>/src/main/scala/utils/getInput.scala
### java.lang.NullPointerException: Cannot invoke "scala.reflect.internal.Types$Type.typeSymbol()" because "tp" is null

occurred in the presentation compiler.

action parameters:
offset: 436
uri: file://<WORKSPACE>/src/main/scala/utils/getInput.scala
text:
```scala
package utils

object GetDailyInput {
    var sessionToken = "53616c7465645f5fdbc2dd8d821bdc26118959590b5f73b1d6ad200e3bb0de7cbe88669f5a2be0d5e5c4a88ff153bc3af610ce2875d3069e1a6c14e7fc0e1aaa"

    def getInput(day: Int): String = {
        var r = requests.get(s"https://adventofcode.com/2022/day/$day/input", headers = Map("Cookie" -> s"session=$sessionToken"))
        var data = r.text
        return data
    }

    def parseInput()@@
}

```



#### Error stacktrace:

```
scala.reflect.internal.Definitions$DefinitionsClass.isByNameParamType(Definitions.scala:428)
	scala.reflect.internal.TreeInfo.isStableIdent(TreeInfo.scala:140)
	scala.reflect.internal.TreeInfo.isStableIdentifier(TreeInfo.scala:113)
	scala.reflect.internal.TreeInfo.isPath(TreeInfo.scala:102)
	scala.tools.nsc.interactive.Global.stabilizedType(Global.scala:974)
	scala.tools.nsc.interactive.Global.typedTreeAt(Global.scala:822)
	scala.meta.internal.pc.SignatureHelpProvider.signatureHelp(SignatureHelpProvider.scala:23)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$signatureHelp$1(ScalaPresentationCompiler.scala:282)
```
#### Short summary: 

java.lang.NullPointerException: Cannot invoke "scala.reflect.internal.Types$Type.typeSymbol()" because "tp" is null