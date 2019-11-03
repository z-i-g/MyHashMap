import com.sun.deploy.security.SelectableSecurityManager;

import java.util.HashMap;

public class MyHashMap {

    private int defaultCapacity;
    private Entry[] table;
    private int count = 0;
    private float loadFactor = 0.75f;

    public MyHashMap() {
        defaultCapacity = 16;
        table = new Entry[defaultCapacity];
    }


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

    public boolean containsKey(Object key) {
        Entry e = getEntry(hash(key), key);
        return e != null;
//        int hash = hash(key);
//        Entry e = table[indexFor(hash, defaultCapacity)];
//        do {
//            if(e != null) {
//                if((e.key == key || key.equals(e.key))) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else
//                return false;
//        } while(e.next != null);

    }

    // удаление элемента из коллекции
    public boolean remove(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, defaultCapacity);
        Entry e = table[indexFor(hash, defaultCapacity)];
        if(e != null) {
            return removeEntry(e, key, index);
        } else
            return false;
    }

    // true если удаление успешно
    private boolean removeEntry(Entry e, Object key, int index) {
        Entry prev = e;
        Entry current = e;
        while(!( (key.equals((current.key))))) {
            if (current.next == null) { //если элемент не найден
                return false;
            } else {
                prev = current;
                current = current.next;
            }
        }
        if(e == current) {
//            System.out.println("removed finished = " + current.key);
            e = e.next;
            table[index] = e;
            return true;
        } else {
            prev.next = current.next;
            return true;
        }
    }

    // добавление в коллекцию
    public void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть null!");
            }

        if((defaultCapacity * loadFactor) < count) {
            transfer(resize());
        }

        int hash = hash(key);
        int tableIndex = indexFor(hash, defaultCapacity);

        // если ячейка не пустая
        if(table[tableIndex] != null) {
            Entry e = table[tableIndex];
            // проходим по всему списку
            while(e != null) {
                //если есть совпадения, элемент перезаписывается
                if(e.hash == hash && (e.key == key || key.equals(e.key))) {
                    e.value = value;
                } else {
                    addEntry(hash, key, value, tableIndex, true);
                }
                e = e.next;
            }
        } else {
            addEntry(hash, key, value, tableIndex, true);

        }
    }

    // создание нового массива x2
    private Entry[] resize () {
        defaultCapacity *= 2;
        Entry[] newTable;
        return newTable = new Entry[defaultCapacity];
    }

    // копирование Entry в новый массив
    private void transfer(Entry[] newTable) {
        Entry[] tempTable = table;
        table = newTable;
        Entry e;
        for(int i = 0; i < tempTable.length; i++ ) {
            if(tempTable[i] != null) {
                int tableIndex = indexFor(tempTable[i].hash, defaultCapacity);
                e = tempTable[i];
                if(e.next == null) {
                    table[tableIndex] = tempTable[i];
                } else {
                    while(e != null) {
                        int newIndex = indexFor(e.hash, defaultCapacity);
                        addEntry(e.hash, e.key
                                , e.value, newIndex, false);
                        e = e.next;
                    }
                }

            }
        }
    }

    // возвращает value
    public Object get(Object key) {
        int hash = hash(key);
        Entry e;
        return (e = getEntry(hash, key)) == null ? null : e.value;
    }

    // возвращает Entry
    private Entry getEntry(int hash, Object key) {
        Entry e;
        int index = indexFor(hash, defaultCapacity);
        e = table[index];
        while (e != null) {
            if(e.hash == hash && (e.key == key || key.equals(e.key))) {
                return e;
            } else {
                e = e.next;
            }
        }
        return null;
    }


    // добавляет Entry
    private void addEntry(int hash, Object key, Object value, int index, boolean flag) {
        Entry e = table[index];
        table[index] = new Entry(hash, key, value, e);
        if(flag) {
            count++;
        }
    }

    // вычисление индекса в массиве Entry
    private int indexFor(int hash, int tableLenght) {
        return hash & (tableLenght - 1);
    }

    // вычисление hashcode
    private int hash(Object key) {
        int hash = key.hashCode();
        return 31 * hash + 17;
    }

    // возвращает количество элементов в hashmap
    public int size() {
        return count;
    }

        public static void main(String[] args) {

    }

}
