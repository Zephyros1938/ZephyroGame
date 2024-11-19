package lib;

import java.nio.CharBuffer;

public class CharBufferUtils {
    public static void swap(CharBuffer cb, int from, int to) throws Exception {
        char t1 = cb.get(from);
        char t2 = cb.get(to);
        cb.put(to, t1);
        cb.put(from, t2);
    }
}