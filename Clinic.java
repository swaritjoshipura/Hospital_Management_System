public class Clinic {
    private NewPatientQueue pq;
    private int er_threshold;
    private int capacity;
    private int processed = 0;
    private int seenByDoctor = 0;
    private int sentToER = 0;
    private int walkedOut = 0;
    private long clock = 0;
    private final int holder = -1;

    public Clinic(int cap, int er_threshold) {
        pq = new NewPatientQueue(cap);
        this.capacity = cap;

        this.er_threshold = er_threshold;
        this.clock = System.currentTimeMillis();
    }

    public int er_threshold() {
        return this.er_threshold;
    }

    public int capacity() {
        return this.capacity;
    }

    public String process(String name, int urgency) {
        if (name != null) {
            processed++;
            Patient patient = new Patient(name, urgency, (System.currentTimeMillis() - this.clock));
            if (urgency > er_threshold) {
                sendToER(patient);
                return null;
            }
            if (pq.insert(patient) != holder) {
                return patient.name();
            }
            else {
                return accomodate(patient);
            }
        }
        return null;
    }
    public String accomodate(Patient patient) {
        Patient top = pq.getMax();
        if (patient.compareTo(top) < 0) {
            sendToER(top);
            String c = top.name();
            pq.remove(c);
            pq.insert(patient);
            return c;
        }
        sendToER(patient);
        return null;

    }
    public String seeNext() {
        if (pq.size() > 0) {
            Patient p = pq.getMax();
            if (p == null) {
                return null;
            }
            else {
                pq.remove(p.name());
                seeDoctor(p);
                return p.name();
            }
        }
        return null;
    }
    public boolean handle_emergency(String name, int urgency) {
        pq.update(name, urgency);
        Patient patient = pq.create_npq(name);
        if (urgency >= er_threshold) {
            sendToER(patient);
           if(patient == null) {
               return false;
           }
           else {
               return true;
           }
        }
        return false;
    }
    public void walk_out(String name) {
        if (name == null) {
            return;
        }
        Patient patient = pq.remove(name);
        if (patient == null) {
            return;
        }
        walkedOut++;
    }
    private void sendToER(Patient p) {
        System.out.println("Patient " + p + " sent to ER.");
        sentToER++;
    }
    private void seeDoctor(Patient p) {
        System.out.println("Patient " + p + " is seeing a doctor.");
        seenByDoctor++;
    }

    public int processed() {
        return processed;
    }

    public int sentToER() {
        return sentToER;
    }

    public int seenByDoctor() {
        return seenByDoctor;
    }

    public int walkedOut() {
        return walkedOut;
    }
}
