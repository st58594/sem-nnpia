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
                    sortPairs.add(new Order(StringEnumUtil.searchEnum(Sort.Direction.class, sortParams[1]), sortParams[0]));
                }
            } else {
                sortPairs.add(new Order(StringEnumUtil.searchEnum(Sort.Direction.class, strOrderPairs[1]), strOrderPairs[0]));
            }
        }
        return sortPairs;
    }
}
