package cz.upce.nnpia.utils;

import cz.upce.nnpia.exceptions.ResourceNotFoundException;

public class EnumUtil {

    /**
     * Convert the raw string to the instance of the Enum<T>
     * @param enumType example: Sort.Direction.class
     * @param search example: "asc"
     * @return example: Sort.Direction.ASC
     * @param <T> example: Sort.Direction
     */
    public static <T extends Enum<?>> T getEnumByString(Class<T> enumType, String search) throws ResourceNotFoundException{
        for (T each : enumType.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(search) == 0)
                return each;
        }
        throw new ResourceNotFoundException("The enum \""+search+"\" not found ");
    }
}
