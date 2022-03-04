import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Util {
    public static HashMap<Integer, VerbosyValue> memory = new HashMap<>();
    public static VerbosyValue current;

    public static Integer resolveParameter(int parameter, boolean isPointer) {
        if (isPointer) {
            var pointee = memory.get(parameter);
            if (current != null && pointee != null) {
                return pointee.getValue();
            } else {
                return null;
            }
        } else {
            return parameter;
        }
    }

    private static final Scanner input = new Scanner(System.in);

    public static VerbosyValue getInput(boolean readInt, boolean readSpaceAsZero) {
        try {
            if (readInt) {
                return VerbosyValue.fromInt(input.nextInt());
            }
        } catch (InputMismatchException ex) {
            // do nothing
        }
        char nextChar = input.findWithinHorizon(".", 0).charAt(0);
        if (nextChar == ' ' && readSpaceAsZero) {
            return VerbosyValue.fromInt(0);
        } else {
            return VerbosyValue.fromChar(nextChar);
        }
    }
}
