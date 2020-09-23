import java.util.ArrayList;
public class PHashtable {
    private ArrayList[] table;
    private int maxSize = 0;
    public PHashtable (int capacity) {
        table = new ArrayList[getNextPrime(capacity)];
    }
    public Patient get(String name) {
        int length = table.length;
        int val = name.hashCode() % length;
        while (val < 0) {
            val = val + table.length;
        }
        ArrayList<Patient> arr = table[val];
        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).name().equals(name)) {
                    return arr.get(i);
                }
            }
        }
        return null;
    }
    public void put(Patient p) {
        int length = table.length;
        int name = p.name().hashCode();
        int htable = name % length;
        while (htable < 0) {
            htable = htable + table.length;
        }
        if (table[htable] == null) {
            table[htable] = new ArrayList<Patient>();
        }
        table[htable].add(p);
        maxSize++;
        return;
    }
    public Patient remove(String name) {
        if(name == null) {
            return null;
        }
        int length = table.length;
        int n = name.hashCode();
        int htable = n % length;
        while (htable < 0) {
            htable = htable + table.length;
        }
        ArrayList<Patient> arr = table[htable];
        Patient patient = null;
        if(arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                String name_at_index = arr.get(i).name();
                if (name_at_index.equals(name)) {
                    patient = arr.get(i);
                    arr.remove(i);
                    maxSize--;
                    break;
                }
            }
            return patient;
        }
        return null;
    }
    public int size() {
        return maxSize;
    }
    public ArrayList<Patient>[] getArray() {
        return table;
    }
    private int getNextPrime(int num) {
        if (num == 2 || num == 3)
            return num;
        int rem = num % 6;
        switch (rem) {
            case 0:
            case 4:
                num++;
                break;
            case 2:
                num += 3;
                break;
            case 3:
                num += 2;
                break;
        }
        while (!isPrime(num)) {
            if (num % 6 == 5) {
                num += 2;
            } else {
                num += 4;
            }
        }
        return num;
    }
    private boolean isPrime(int num) {
        if(num % 2 == 0){
            return false;
        }
        int x = 3;
        for(int i = x; i < num; i+=2){
            if(num % i == 0){
                return false;
            }
        }
        return true;
    }
}
      

