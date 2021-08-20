import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author coderMy
 * @Date 2021/8/18
 */
public class LRUCache extends LinkedHashMap {

    private Integer maxCount = 10;

    public LRUCache() {
    }

    public LRUCache(Integer maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size()>maxCount;
    }
}
