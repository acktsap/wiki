package acktsap.pattern.classandobjects;

import acktsap.Block;

public class EnumTest {

    enum Source {
        AAA("source.aaa"),
        BBB("source.bbb");

        private final String code;

        Source(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    enum Target {
        AAA("target.aaa"),
        BBB("target.bbb");

        private final String code;

        Target(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    public static void main(String[] args) {

        Block.d("Enum conversion").p(() -> {
            for (Source source : Source.values()) {
                System.out.printf("from '%s' to '%s'%n", source, Target.valueOf(source.name()));
            }
        });
    }
}
