package benchmark.state

import benchmark.*

@OptIn(ExperimentalStdlibApi::class)
fun depthManualState(t: Tree?): Int {
    if (t == null) return 0
    class Frame(val node: Tree, var state: Int = 0, var depth: Int = 1)
    val stack = ArrayList<Frame>()
    val root = Frame(t)
    stack.add(root)
    while (stack.isNotEmpty()) {
        val frame = stack.last()
        when (frame.state++) {
            0 -> frame.node.left?.let { l -> stack.add(Frame(l)) }
            1 -> frame.node.right?.let { r -> stack.add(Frame(r)) }
            2 -> {
                stack.removeLast()
                stack.lastOrNull()?.let { p -> p.depth = maxOf(p.depth, frame.depth + 1) }
            }
        }
    }
    return root.depth
}

