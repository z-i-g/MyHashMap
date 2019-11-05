/**
 * Класс реализации собственной HashMap
 * @autor Айрат Загидуллин
 * @version 1.0
 */
public class MyHashMap {

    private int defaultCapacity;
    private Entry[] table;
    private int count = 0;
    private float loadFactor = 0.75f;

    public MyHashMap() {
        defaultCapacity = 16;
        table = new Entry[defaultCapacity];
    }

    /**
     * Внутренний класс, для хранения пар ключ-значение
     */
    public class Entry {
        Object key;
        Object value;
        int hash;
        Entry next;

        public Entry(int hash, Object key, Object value, Entry next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }
    }

    /**
     * Проверка наличия переданног ключа
     * @param key - ключ
     * @return true если переданный ключ есть в коллекции
     */
    public boolean containsKey(Object key) {
        Entry entry = getEntry(hash(key), key);
        return entry != null;
    }

    /**
     * Удаление элемента по ключу
     * @param key - ключ
     * @return true если удаение успешно
     */
    public boolean remove(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, defaultCapacity);
        Entry entry = table[indexFor(hash, defaultCapacity)];
        if(entry != null) {
            return removeEntry(entry, key, index);
        } else {
            return false;
        }
    }

    private boolean removeEntry(Entry entry, Object key, int index) {
        Entry prev = entry;
        Entry current = entry;
        while(!(key.equals(current.key))) {
            if (current.next == null) {
                return false;
            } else {
                prev = current;
                current = current.next;
            }
        }
        if(entry == current) {
            entry = entry.next;
            table[index] = entry;
            count--;
            return true;
        } else {
            prev.next = current.next;
            count--;
        }
        return true;
    }

    /**
     * Добавление элемента в коллекцию, если key одинаковые, то value перезаписывается
     * @param key - ключ
     * @param value - значение
     * @exception IllegalArgumentException - если ключ null
     */
    public void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть null!");
            }
        
        if((defaultCapacity * loadFactor) < count) {
            transfer(resize());
        }

        int hash = hash(key);
        int tableIndex = indexFor(hash, defaultCapacity);

        if(table[tableIndex] != null) {
            for(Entry entry = table[tableIndex]; entry != null; entry = entry.next) {
                if(entry.hash == hash && (entry.key == key || key.equals(entry.key))) {
                    entry.value = value;
                } else {
                    addEntry(hash, key, value, tableIndex, true);
                }
            }
        } else {
            addEntry(hash, key, value, tableIndex, true);
        }
    }

    /**
     * Увеличение размера коллекции при loadFactor > 0.75
     * @return Entry[] - массив с увеличенным размером
     */
    private Entry[] resize () {
        defaultCapacity *= 2;
        return new Entry[defaultCapacity];
    }

    /**
     * Копирует элементы в newTable[]
     * @param newTable - новый массив
     */
    private void transfer(Entry[] newTable) {
        Entry[] tempTable = table;
        table = newTable;
        Entry entry;

        for(int i = 0; i < tempTable.length; i++ ) {
            if(tempTable[i] != null) {
                int tableIndex = indexFor(tempTable[i].hash, defaultCapacity);
                entry = tempTable[i];
                if(entry.next == null) {
                    table[tableIndex] = tempTable[i];
                } else {
                    for(Entry entryFor = entry; entry != null; entry = entry.next) {
                        int newIndex = indexFor(entryFor.hash, defaultCapacity);
                        addEntry(entryFor.hash, entryFor.key
                                , entryFor.value, newIndex, false);
                    }
                }
            }
        }
    }

    /**
     * Получение value ко key, если value не найден, то null
     * @param key - ключ
     * @return value
     */
    public Object get(Object key) {
        int hash = hash(key);
        Entry entry;
        return (entry = getEntry(hash, key)) == null ? null : entry.value;
    }

    private Entry getEntry(int hash, Object key) {
        int index = indexFor(hash, defaultCapacity);

        for (Entry entry = table[index]; entry != null ; entry = entry.next) {
            if(entry.hash == hash && (entry.key == key || key.equals(entry.key))) {
                return entry;
            }
        }
        return null;
    }

    private void addEntry(int hash, Object key, Object value, int index, boolean flag) {
        Entry entry = table[index];
        table[index] = new Entry(hash, key, value, entry);
        if(flag) {
            count++;
        }
    }

    private int indexFor(int hash, int tableLenght) {
        return hash & (tableLenght - 1);
    }

    private int hash(Object key) {
        int hash = key.hashCode();
        return 31 * hash + 17;
    }

    /**
     * Количество элементов в коллекции
     * @return count - количество
     */
    public int size() {
        return count;
    }

        public static void main(String[] args) {

    }

}
