// Initialization styles - Pages 333-
public class Initialization {

    private static class FieldType {}

    private static FieldType computeFieldValue() {
        return new FieldType();
    }


    // Normal initialization of an instance field - Page 282
    // Perfer this one
    private final FieldType field1 = computeFieldValue();

    // Lazy initialization of instance field - synchronized accessor - Page 333
    private FieldType field2;
    private synchronized FieldType getField2() {
        if (field2 == null)
            field2 = computeFieldValue();
        return field2;
    }


    // Lazy initialization holder class idiom for static fields - Page 334

    // If you need to use lazy initialization for performance on a static field, use
    // the lazy initialization holder class idiom.
    private static class FieldHolder {
        static final FieldType field = computeFieldValue();
    }

    private static FieldType getField() { return FieldHolder.field; }


    // Double-check idiom for lazy initialization of instance fields - Page 334

    // If you need to use lazy initialization for performance on an instance field,
    // use the double-check idiom. This idiom avoids the cost of locking when
    // accessing the field after initialization
    private volatile FieldType field4;

    // While you can apply the double-check idiom to static fields as well, there is
    // no reason to do so: the lazy initialization holder class idiom is a better choice.
    private FieldType getField4() {
        FieldType result = field4;
        if (result == null) { // First check (no locking)
            synchronized(this) {
                if (field4 == null) // Second check (with locking)
                    field4 = computeFieldValue();
                return field4;
            }
        }
        return result;
    }


    // Single-check idiom - can cause repeated initialization! - Page 334
    // Occasionally, you may need to lazily initialize an instance field
    // that can tolerate repeated initialization.
    private volatile FieldType field5;

    private FieldType getField5() {
        FieldType result = field5;
        if (result == null)
            field5 = result = computeFieldValue();
        return result;
    }

}
