package tech.hiphone.commons.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

class CollectionUtilTest {

    @Test
    void testIsEmpty() {
        assertTrue(CollectionUtil.isEmpty(null));
    }

    @Test
    void testtopK() {
        List<Integer> array = Arrays.asList(2, 1, 8, 5, 9, 3);

        Collection<Integer> result = CollectionUtil.topK(array, 3, Integer::compareTo);

        List<Integer> expect = Arrays.asList(2, 1, 3);

        assertTrue(result.containsAll(expect));
        assertTrue(expect.containsAll(result));
    }

}
