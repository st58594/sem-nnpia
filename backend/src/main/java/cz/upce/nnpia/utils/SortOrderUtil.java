package cz.upce.nnpia.utils;


import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SortOrderUtil {

    /**
     * Convert a raw string order to the Sort.Order
     * @param strOrderPairs is the raw string order. Example: ["property, order", "property, order" ...] or sort just for 1 property ["property", "order"]
     * @return List of the Sort.Order or the empty list of the Sort.Order
     */
    public static List<Order> stringPairsToOrders(@Nullable String[] strOrderPairs){
        List<Order> sortPairs = new ArrayList<>();
        if (strOrderPairs != null && strOrderPairs.length > 0){
            if (strOrderPairs[0].contains(",")){
                for (String sortOrder : strOrderPairs) {
                    String[] sortParams = sortOrder.split(",");
                    for (int i = 0; i < sortParams.length; i+=2) {
                        sortPairs.add(new Order(EnumUtil.getEnumByString(Sort.Direction.class, sortParams[i+1]), sortParams[i]));
                    }
                }
            } else {
                sortPairs.add(new Order(EnumUtil.getEnumByString(Sort.Direction.class, strOrderPairs[1]), strOrderPairs[0]));
            }
        }
        return sortPairs;
    }
}
