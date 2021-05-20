package com.hong.mobilecity;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;

/**
 * 测试工具
 * @author HongYi@10004580
 * @createTime 2021年04月26日 19:53:00
 */
public class TestBenchMarks {
    public enum ChannelState {
        CONNECTED, DISCONNECTED, SENT, RECEIVED, CAUGHT
    }

    @State(Scope.Benchmark)
    public static class ExecutionPlan {
        @Param({ "1000000" })
        public int size;
        public ChannelState[] states = null;

        @Setup
        public void setUp() {
            ChannelState[] values = ChannelState.values();
            states = new ChannelState[size];
            Random random = new Random(System.currentTimeMillis());
            for (int i = 0; i < size; i++) {
                int nextInt = random.nextInt(1000000);
                if (nextInt > 100) {
                    states[i] = ChannelState.RECEIVED;
                } else {
                    states[i] = values[nextInt % values.length];
                }
            }
        }
    }

    @Fork(value = 5)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void benchSiwtch(ExecutionPlan plan, Blackhole bh) {
        int result = 0;
        for (int i = 0; i < plan.size; ++i) {
            switch (plan.states[i]) {
                case CONNECTED:
                    result += ChannelState.CONNECTED.ordinal();
                    break;
                case DISCONNECTED:
                    result += ChannelState.DISCONNECTED.ordinal();
                    break;
                case SENT:
                    result += ChannelState.SENT.ordinal();
                    break;
                case RECEIVED:
                    result += ChannelState.RECEIVED.ordinal();
                    break;
                case CAUGHT:
                    result += ChannelState.CAUGHT.ordinal();
                    break;
            }
        }
        bh.consume(result);
    }

    @Fork(value = 5)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void benchIfAndSwitch(ExecutionPlan plan, Blackhole bh) {
        int result = 0;
        for (int i = 0; i < plan.size; ++i) {
            ChannelState state = plan.states[i];
            if (state == ChannelState.RECEIVED) {
                result += ChannelState.RECEIVED.ordinal();
            } else {
                switch (state) {
                    case CONNECTED:
                        result += ChannelState.CONNECTED.ordinal();
                        break;
                    case SENT:
                        result += ChannelState.SENT.ordinal();
                        break;
                    case DISCONNECTED:
                        result += ChannelState.DISCONNECTED.ordinal();
                        break;
                    case CAUGHT:
                        result += ChannelState.CAUGHT.ordinal();
                        break;
                }
            }
        }
        bh.consume(result);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(TestBenchMarks.class.getName()+".*")
                .warmupIterations(2).measurementIterations(2).forks(1).build();
        new Runner(options).run();
    }

}
