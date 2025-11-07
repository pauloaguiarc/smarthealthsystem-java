import java.io.Serial;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.Serializable;

public class Patient implements Serializable {

    private String id;
    private String name;
    private int age;
    private List<String> medicalHistory = new ArrayList<>();
    private String contactNumber;
    private String address;
    private LocalDateTime registrationDate;
    private List<Prescription> prescriptions = new ArrayList<>();
    @Serial
    private static final long serialVersionUID = 1L;

    public Patient(String id, String name, int age, String address, String contact) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.contactNumber = contact;
        this.registrationDate = LocalDateTime.now();
    }

    public Patient(String id, String name, int age, String address, String contact, List<String> medHist) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.contactNumber = contact;
        this.registrationDate = LocalDateTime.now();
        this.medicalHistory = medHist;
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public int getAge() { return this.age; }
    public String getAddress() { return this.address; }
    public String getContact() { return this.contactNumber; }
    public String getRegistrationDate() { return this.registrationDate.toString(); }
    public List<String> getMedicalHistory() {
        return medicalHistory;
    }
    public List<Prescription> getRecentPrescriptions() {
        return prescriptions;
    }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setAddress(String address) { this.address = address; }
    public void setContact(String contact) { this.contactNumber = contact; }
    public void addMedicalHistory(String history) { medicalHistory.add(history); }
    public void addPrescription(Prescription prescr) { prescriptions.add(prescr); }

    @Override
    public String toString() {
        return ("Patient ID: " + getId() + ", Name: " + getName());
    }
}
