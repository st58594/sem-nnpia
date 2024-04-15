package cz.upce.nnpia.utils;

public class StringEnumUtil {

    /**
     * Convert the raw string to the instance of the Enum<T>
     * @param enumType example: Sort.Direction.class
     * @param search example: "asc"
     * @return example: Sort.Direction.ASC
     * @param <T> example: Sort.Direction
     */
    public static <T extends Enum<?>> T searchEnum(Class<T> enumType, String search){
        for (T each : enumType.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(search) == 0)
                return each;
        }
        return null;
    }
}
