package test.java;

import org.example.KMP;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class KMPTest {

    @Test
    public void testBasicMatch() {
        List<Integer> result = KMP.search("abcabcabc", "abc");
        assertEquals(List.of(0, 3, 6), result);
    }

    @Test
    public void testNoMatch() {
        List<Integer> result = KMP.search("aaaaaa", "b");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSingleMatch() {
        List<Integer> result = KMP.search("thequickbrownfox", "quick");
        assertEquals(List.of(3), result);
    }
}
