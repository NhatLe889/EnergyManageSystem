public class ExternalChainingMap<K, V> {

    private K key;
    private V value;
    private ExternalChainingMap<K, V> next;

    ExternalChainingMap(K key, V value) {
        this(key, value, null);
    }

    ExternalChainingMap(K key, V value,
                             ExternalChainingMap<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    K getKey() {
        return key;
    }


    V getValue() {
        return value;
    }


    public ExternalChainingMap<K, V> getNext() {
        return next;
    }


    void setKey(K key) {
        this.key = key;
    }

    void setValue(V value) {
        this.value = value;
    }

    void setNext(ExternalChainingMap<K, V> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        String key = this.key == null ? "null" : this.key.toString();
        String value = this.value == null ? "null" : this.value.toString();
        return String.format("(%s, %s)", key, value);
    }
}