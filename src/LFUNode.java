/**
 * @Author coderMy
 * @Date 2021/8/18
 */
public class LFUNode {

    private LFUNode preNode;
    private LFUNode nextNode;
    private Integer count;
    private Object key;
    private Object value;

    public LFUNode() {
    }

    public LFUNode(LFUNode preNode, LFUNode nextNode, Integer count, Object key, Object value) {
        this.preNode = preNode;
        this.nextNode = nextNode;
        this.count = count;
        this.key = key;
        this.value = value;
    }

    public LFUNode getPreNode() {
        return preNode;
    }

    public void setPreNode(LFUNode preNode) {
        this.preNode = preNode;
    }

    public LFUNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(LFUNode nextNode) {
        this.nextNode = nextNode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
