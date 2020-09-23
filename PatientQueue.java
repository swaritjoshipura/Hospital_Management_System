public class PatientQueue {
    private Patient[] array;
    private int maxSize = 0;
    private final int holder = -1;

    public PatientQueue(int capacity)
    {
        array = new Patient[capacity];
    }
    public int insert(Patient p) {
        if (maxSize != array.length) {
            array[maxSize] = p;
            p.setPosInQueue(maxSize++);
            int capacity = (maxSize + holder);
            while (capacity > 0) {
                if (array[capacity].compareTo(array[(capacity + holder)/2]) <0) {
                    break;
                }
                capacity = operate_on_capacity(capacity);
            }
            return capacity;
        }
        return holder;
    }
    public int operate_on_capacity(int capacity) {
        Patient first = array[capacity];
        array[capacity] = array[(capacity + holder)/2];
        array[capacity].setPosInQueue(capacity);
        array[(capacity + holder)/2] = first;
        array[(capacity + holder)/2].setPosInQueue((capacity + holder)/2);
        capacity = ((capacity + holder)/2);
        return capacity;
    }
    public Patient delMax() {
        Patient patient = array[0];
        if (patient != null) {
            patient.setPosInQueue(holder);
        }
        if (maxSize > 0) {
            array[0] = array[maxSize - 1];
            array[0].setPosInQueue(0);
            array[maxSize-1] = null;
            maxSize--;
            operate();
        }
        return patient;
    }
    public void operate() {
        int count = 0;
        int valTil = (maxSize + count) / 2;
        while (count <= (valTil)) {
            int prev = ((2 * count) - holder);
            int next = ((2 * count) + 2);
            int val = prev;
            if ((prev < maxSize) && (next < maxSize)) {
               if ((array[prev].compareTo(array[next])) < 0){
                    val = next;
                }
            }
            boolean f = val < maxSize;
            if (!f || (array[count].compareTo(array[val]) > holder + 1)) {
                break;
            }
                Patient stack = array[count];
                array[count] = array[val];
                array[count].setPosInQueue(count);
                array[val] = stack;
                array[val].setPosInQueue(val);
                count = val;

        }
    }
    public Patient getMax() {
        return array[0];
    }
    public int size()
    {
        return maxSize;
    }
    public boolean isEmpty() {
        if(maxSize == 0) {
            return true;
        }
        return false;
    }
    public Patient[] getArray() {
        return array;
    }
}
