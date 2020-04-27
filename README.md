# Deep recursion benchmark

Benchmarking various approaches to deep recursion computations of depth on deep tree of 100K nodes.

```kotlin
class Tree(val left: Tree?, val right: Tree?)
val n = 100_000 // tree depth
val deepTree = generateSequence(Tree(null, null)) { prev ->
    Tree(prev, null)
}.take(n).last()
```

The basic recursive algorithm:

```kotlin
fun depthRecursive(t: Tree?): Int =
    if (t == null) 0 else maxOf(
        depthRecursive(t.left), // recursive call one
        depthRecursive(t.right) // recursive call two
    ) + 1
```

All measured algorithms:

* `recursive` and `recursiveOpt` are baseline implementations. 
  `Opt` version avoids an extra leaf call for `null` reference.
  They are running for 100K nodes tree without `StackOverflowError` due to `-Xss64m` option.
* `manualState` is a manual translation of state machine.
  It should be compared to `Opt` version, since it does not use stack for leaf calls.
* `manualDFS` is an optimized implementation via graph DFS that does not need to maintain state.
  It should be compared to `Opt` version, since it does not use stack for leaf calls.
* `corouitiesBasic` and `coroutinesBasicOpt` are simple implementations
  as explained in [Deep recursion with coroutines](https://medium.com/@elizarov/deep-recursion-with-coroutines-7c53e15993e3)
  story.
* `coroutinesFull` and `coroutinesFullOpt` use a productized version with support for a mutual recursion
  from [this gist](https://gist.github.com/elizarov/861dda8c3e8c5ee36eaa6db4ad996568).

Results:

```text
# CPU: Intel(R) Core(TM) i7-6700K CPU @ 4.00GHz
# VM version: JDK 11.0.6, Java HotSpot(TM) 64-Bit Server VM, 11.0.6+8-LTS
# VM options: -Xss64m

Benchmark                                  Mode  Cnt  Score   Error  Units
DeepRecursionBenchmark.coroutinesBasic     avgt   20  4.314 ± 0.040  ms/op
DeepRecursionBenchmark.coroutinesBasicOpt  avgt   20  2.387 ± 0.078  ms/op
DeepRecursionBenchmark.coroutinesFull      avgt   20  4.589 ± 0.097  ms/op
DeepRecursionBenchmark.coroutinesFullOpt   avgt   20  2.294 ± 0.056  ms/op
DeepRecursionBenchmark.manualDFS           avgt   20  0.611 ± 0.002  ms/op
DeepRecursionBenchmark.manualState         avgt   20  1.423 ± 0.008  ms/op
DeepRecursionBenchmark.recursive           avgt   20  0.603 ± 0.008  ms/op
DeepRecursionBenchmark.recursiveOpt        avgt   20  0.433 ± 0.006  ms/op
```