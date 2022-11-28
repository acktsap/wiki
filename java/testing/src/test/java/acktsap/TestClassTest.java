package acktsap;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Builders;
import net.jqwik.api.Combinators;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class TestClassTest {
    @RepeatedTest(1000)
    @Test
    void before() {
        Arbitrary<Integer> nullable = Arbitraries.integers().injectNull(1.0d);

        Arbitrary<HashMap<Object, Object>> combined = Combinators.withBuilder(HashMap::new)
            .use(nullable).in((map, value) -> {
                map.put("key", "value");
                return map;
            })
            .build();

        assert combined.sample().get("key").equals("value");
    }

    @RepeatedTest(1000)
    @Test
    void after() {
        Arbitrary<Integer> nullable = Arbitraries.integers().injectNull(1.0d);

        Arbitrary<HashMap<Object, Object>> combined = Builders.withBuilder(HashMap::new)
            .use(nullable).in((map, value) -> {
                map.put("key", "value");
                return map;
            })
            .build();

        assert combined.sample().get("key").equals("value");
    }
}
