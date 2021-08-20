import java.util.HashMap;
import java.util.LinkedList;

/**
 * @Author coderMy
 * @Date 2021/8/18
 */
public class Test {
    public static void main(String[] args) {

        // 测试LFUCache
        LFUCache lfuCache = new LFUCache(5);
        lfuCache.set("1","111");
        lfuCache.set("2","222");
        lfuCache.set("3","333");
        lfuCache.set("4","444");
        lfuCache.set("4","4444444444");
        lfuCache.set("5","555");
        lfuCache.set("6","666");


        // 测试LRUCache
        LRUCache lruCache = new LRUCache(2);
        lruCache.put("1","11");
        lruCache.put("2","22");
        lruCache.put("3","33");
        System.out.println(lruCache);
    }
}
