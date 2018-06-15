package org.openjdk.jmh.samples;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class JMHSample_32_Exceptions {

    private static TestClass testClass = new TestClass();

    static class TestClass {
        Object object;

        public String toStringWithNPE() {
            return object.toString();
        }

        public String toStringWithoutNPE() {
            if (object == null) {
                return "";
            }
            return object.toString();
        }
    }

    @Benchmark
    @Measurement(iterations = 20000, time = 200, timeUnit = TimeUnit.MILLISECONDS)
    @Warmup(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
    public void withException() {
        try {
            testClass.toStringWithNPE();
        } catch (NullPointerException e) {

        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 20000, time = 200, timeUnit = TimeUnit.MILLISECONDS)
    public void withoutException() {
        testClass.toStringWithoutNPE();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + JMHSample_32_Exceptions.class.getSimpleName() + ".*")
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
