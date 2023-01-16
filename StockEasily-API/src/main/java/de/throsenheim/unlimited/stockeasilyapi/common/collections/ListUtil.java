package de.throsenheim.unlimited.stockeasilyapi.common.collections;

import java.util.List;
import java.util.function.Predicate;

public class ListUtil {
    public static <T> List<T> getNewItems(List<T> requestItems, List<T> existingItems) {
        Predicate<T> notExists = element -> !existingItems.contains(element);
        return requestItems.stream().filter(notExists).toList();
    }

    public static <T> List<T> getUnusedItems(List<T> requestItems, List<T> existingItems) {
        Predicate<T> notExists = element -> !existingItems.contains(element);
        return requestItems.stream().filter(notExists).toList();
    }
}
