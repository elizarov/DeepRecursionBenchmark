package benchmark

import benchmark.basic.*
import benchmark.dfs.*
import benchmark.full.*
import benchmark.state.*
import org.openjdk.jmh.annotations.*
import java.util.concurrent.*

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 6, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class DeepRecursionBenchmark {
    @Benchmark
    fun recursive() {
        check(depthRecursive(deepTree) == expectedDepth)
    }

    @Benchmark
    fun recursiveOpt() {
        check(depthRecursiveOpt(deepTree) == expectedDepth)
    }

    @Benchmark
    fun manualDFS() {
        check(depthManualDFS(deepTree) == expectedDepth)
    }

    @Benchmark
    fun manualState() {
        check(depthManualState(deepTree) == expectedDepth)
    }

    @Benchmark
    fun coroutinesBasic() {
        check(depthCoroutinesBasic(deepTree) == expectedDepth)
    }

    @Benchmark
    fun coroutinesBasicOpt() {
        check(depthCoroutinesBasicOpt(deepTree) == expectedDepth)
    }

    @Benchmark
    fun coroutinesFull() {
        check(depthCoroutinesFull(deepTree) == expectedDepth)
    }

    @Benchmark
    fun coroutinesFullOpt() {
        check(depthCoroutinesFullOpt(deepTree) == expectedDepth)
    }
}

// test
fun main() {
    with(DeepRecursionBenchmark()) {
        recursive()
        recursiveOpt()
        manualDFS()
        manualState()
        coroutinesBasic()
        coroutinesBasicOpt()
        coroutinesFull()
        coroutinesFullOpt()
    }
}