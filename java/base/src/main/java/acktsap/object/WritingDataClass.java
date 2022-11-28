package acktsap.object;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Write javadoc.
 *
 * @author Taeik Lim
 */
public class WritingDataClass {

    // make a static factory class for readability
    public static WritingDataClass of(int intValue, long longValue, List<Object> list) {
        return new WritingDataClass(intValue, longValue, list);
    }

    // make it immutable using final
    protected final int intValue;
    protected final long longValue;
    protected final List<Object> list;

    // hide constructor
    private WritingDataClass(int intValue, long longValue, List<Object> list) {
        this.intValue = intValue;
        this.longValue = longValue;
        // make list immutable
        this.list = Collections.unmodifiableList(list);
    }

    // define getters

    public int getIntValue() {
        return intValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public List<Object> getList() {
        return list;
    }

    // define toString, equals, hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WritingDataClass that = (WritingDataClass) o;
        return intValue == that.intValue &&
            longValue == that.longValue &&
            Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intValue, longValue, list);
    }

    @Override
    public String toString() {
        return "WritingDataClass{" +
            "intValue=" + intValue +
            ", longValue=" + longValue +
            ", list=" + list +
            '}';
    }
}
