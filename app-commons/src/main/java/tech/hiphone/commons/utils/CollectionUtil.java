package tech.hiphone.commons.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    // 前几个 comparator -1 的在前面
    public static <E, C extends Collection<E>> Collection<E> topK(C collection, int k, Comparator<E> comparator) {
        PriorityQueue<E> priorityQueue = new PriorityQueue<>((e1, e2) -> {
            return -comparator.compare(e1, e2);
        });
        for (E e : collection) {
            if (priorityQueue.size() < k) {
                priorityQueue.add(e);
            } else {
                if (comparator.compare(e, priorityQueue.peek()) < 0) {
                    priorityQueue.poll();
                    priorityQueue.add(e);
                }
            }
        }
        return priorityQueue;
    }

}
