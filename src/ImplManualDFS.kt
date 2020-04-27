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

@OptIn(ExperimentalStdlibApi::class)
fun depthManualDFSFast(t: Tree?): Int {
    if (t == null) return 0
    class Visit(val node: Tree, var depth: Int)
    var ss = 16
    var stack = arrayOfNulls<Tree>(ss)
    var depth = IntArray(ss)
    stack[0] = t
    depth[0] = 1
    var sp = 1
    var maxDepth = 0
    while (sp > 0) {
        sp--
        val v = stack[sp]!!
        val d = depth[sp]
        maxDepth = maxOf(maxDepth, d)
        if (sp + 2 > ss) {
            ss *= 2
            stack = stack.copyOf(ss)
            depth = depth.copyOf(ss)
        }
        v.left?.let { l ->
            stack[sp] = l
            depth[sp] = d + 1
            sp++
        }
        v.right?.let { r ->
            stack[sp] = r
            depth[sp] = d + 1
            sp++
        }
    }
    return maxDepth
}
