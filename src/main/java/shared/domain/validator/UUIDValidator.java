package shared.domain.validator;

import java.util.regex.Pattern;

public class UUIDValidator {
    private static final Pattern PATTERN = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

    public static boolean isValid(String uuidString){
        return PATTERN.matcher(uuidString).matches();
    }
}
