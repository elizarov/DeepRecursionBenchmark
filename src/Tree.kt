package benchmark

class Tree(val left: Tree?, val right: Tree?)

val expectedDepth = 100_000 // expected depth

val deepTree = generateSequence(Tree(null, null)) { prev ->
    Tree(prev, null)
}.take(expectedDepth).last()

fun depthRecursive(t: Tree?): Int =
    if (t == null) 0 else maxOf(
        depthRecursive(t.left), // recursive call one
        depthRecursive(t.right) // recursive call two
    ) + 1

fun depthRecursiveOpt(t: Tree): Int =
    maxOf(
        t.left?.let { l -> depthRecursiveOpt(l) } ?: 0,
        t.right?.let { r -> depthRecursiveOpt(r) } ?: 0
    ) + 1
