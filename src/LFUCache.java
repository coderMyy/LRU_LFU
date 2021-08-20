import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author coderMy
 * @Date 2021/8/18
 */
public class LFUCache {

    // 哈希表1 ： key为常规的key , value为node节点(包含key,value,使用频率次数,实际上只使用到value和频率)
    private HashMap<Object,LFUNode> cache = new HashMap<>();
    // 哈希表2 ： key为频率次数 ，value为node节点(包含key,value,使用频率次数，实际上只使用到key)
    private HashMap<Integer,LinkedList> rateCache = new HashMap<>();
    // 默认初始化频率
    private static Integer DEFAULT_RATE = 1;
    // 最大容量
    private Integer cap = 100;

    public LFUCache() {
    }

    public LFUCache(Integer cap) {
        if (cap<=0){
            throw new RuntimeException("不合法的容量");
        }
        this.cap = cap;
    }

    /**
     * 添加缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(Object key,Object value){

        // 已经存在,覆盖
        LFUNode lfuNode;
        if ((lfuNode = cache.get(key))!=null){

            // 该key使用频率+1
            Integer count = lfuNode.getCount()+1;
            // 创建新node进行覆盖
            LFUNode newNode = new LFUNode(null,null,count,key,value);
            cache.put(key,newNode);
            // cache中存在该key，rateCache中key对应的链表中必然存在该节点，转移到rateCache的key+1对应的链表中
            // 转移,如果频率哈希表中不存在即将要转移的链表
            moveNode(newNode,count);
            // 移除原有链表中的node
            removeOld(lfuNode);
        }else {

            // 判断是否超出容量,超出移除使用频率最低的key
            if (cache.size()>=cap){

                Set<Integer> keys = rateCache.keySet();
                Integer first = keys.stream()
                        .sorted(Comparator.comparingInt(a -> a))
                        .collect(Collectors.toList())
                        .stream().findFirst()
                        .get();
                LFUNode firstNode = (LFUNode)rateCache.get(first).getFirst();
                remove(firstNode.getKey());
            }

            // 创建新node进行追加
            LFUNode newNode = new LFUNode(null,null,DEFAULT_RATE,key,value);
            cache.put(key,newNode);
            LinkedList rateNodeList;
            if ((rateNodeList = rateCache.get(DEFAULT_RATE))==null){
                LinkedList list = new LinkedList();
                list.addLast(newNode);
                rateCache.put(DEFAULT_RATE,list);
            }else {
                rateNodeList.addLast(newNode);
            }
        }
        return true;
    }


    /**
     * 获取缓存
     * @param key
     * @return
     */
    public Object get(Object key){

        LFUNode lfuNode = cache.get(key);

        if (lfuNode==null) return null;

        Object value = lfuNode.getValue();

        Integer count = lfuNode.getCount()+1;

        // 移除原有链表中的node
        removeOld(lfuNode);

        // 增加频率
        lfuNode.setCount(count);

        // 转移,如果频率哈希表中不存在
        moveNode(lfuNode,count);

        return value;
    }


    /**
     * 移除缓存
     * @param key
     * @return
     */
    public boolean remove(Object key){

        LFUNode lfuNode = cache.get(key);

        if (lfuNode==null) return false;

        removeOld(lfuNode);
        // 移除cache哈希表
        return cache.remove(key)!=null;
    }


    /**
     * 移除频率哈希表中原有的
     * @param lfuNode
     */
    private void removeOld(LFUNode lfuNode){

        // 移除原有链表中的node
        LinkedList list = rateCache.get(lfuNode.getCount());

        list.remove(lfuNode);

    }

    /**
     * 转移
     * @param newNode
     * @param key
     */
    private void moveNode(LFUNode newNode,Integer key){
        if (rateCache.get(key)==null){
            // 创建链表
            LinkedList list = new LinkedList();
            list.add(newNode);
            // 以频率为key存入频率哈希表
            rateCache.put(key,list);
        }else {
            LinkedList list = rateCache.get(key);
            list.addLast(newNode);
        }
    }

    public Integer getCap() {
        return cap;
    }

    public void setCap(Integer cap) {
        if (cap<=0){
            throw new RuntimeException("不合法容量");
        }
        this.cap = cap;
    }
}
