package benchmark.dfs

import benchmark.*

@OptIn(ExperimentalStdlibApi::class)
fun depthManualDFS(t: Tree?): Int {
    if (t == null) return 0
    class Visit(val node: Tree, var depth: Int)
    val stack = ArrayList<Visit>()
    stack.add(Visit(t, 1))
    var maxDepth = 0
    while (stack.isNotEmpty()) {
        val v = stack.removeLast()
        maxDepth = maxOf(maxDepth, v.depth)
        v.node.left?.let { l -> stack.add(Visit(l, v.depth + 1)) }
        v.node.right?.let { r -> stack.add(Visit(r, v.depth + 1)) }
    }
    return maxDepth
}

