package cz.upce.nnpia.utils;

import cz.upce.nnpia.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

class SortOrderUtilTest {

    @Test
    void multipleStringOrdersToSortOrder() {
        String[] input = new String[]{"name,asc", "price,desc"};
        List<Sort.Order> expected = List.of(
                new Sort.Order(Sort.Direction.ASC, "name"),
                new Sort.Order(Sort.Direction.DESC, "price")
        );
        Assertions.assertIterableEquals(expected, SortOrderUtil.stringPairsToOrders(input));
    }
    @Test
    void oneStringOrderToSortOrder(){
        String[] input = new String[]{"name", "asc"};
        List<Sort.Order> expected = List.of(
                new Sort.Order(Sort.Direction.ASC, "name")
        );
        Assertions.assertIterableEquals(expected, SortOrderUtil.stringPairsToOrders(input));
    }
    @Test
    void emptyStringOrderToSortOrder(){
        Assertions.assertIterableEquals(Collections.emptyList(), SortOrderUtil.stringPairsToOrders(null));
        Assertions.assertIterableEquals(Collections.emptyList(), SortOrderUtil.stringPairsToOrders(new String[]{}));
    }

    @Test
    void invalidStringOrderToSortOrder(){
        String[] invalidInput = new String[]{"name", "ascendant"};
        Assertions.assertThrows(ResourceNotFoundException.class, () -> SortOrderUtil.stringPairsToOrders(invalidInput));
    }
}