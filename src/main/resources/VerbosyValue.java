public class VerbosyValue {
    private final boolean isChar;
    private final int value;

    private VerbosyValue(boolean isChar, int value) {
        this.isChar = isChar;
        this.value = value;
    }

    public static VerbosyValue fromChar(char value) {
        return new VerbosyValue(true, value);
    }

    public static VerbosyValue fromInt(int value) {
        return new VerbosyValue(false, value);
    }

    @Override
    public String toString() {
        if (isChar) {
            return Character.toString((char)value);
        } else {
            return Integer.toString(value);
        }
    }

    public boolean isChar() {
        return isChar;
    }

    public int getValue() {
        return value;
    }
}
