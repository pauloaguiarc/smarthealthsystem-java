import java.io.Serial;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.Serializable;

public class Doctor implements Serializable {

    private String id;
    private String name;
    private int age;
    private String specialization;
    private String contactNumber;
    private String email;
    private LocalDateTime registrationDate;
    @Serial
    private static final long serialVersionUID = 1L;

    public Doctor(String id, String name, String spec, int age, String contact, String email) {
        this.id = id;
        this.name = name;
        this.specialization = spec;
        this.age = age;
        this.contactNumber = contact;
        this.email = email;
        this.registrationDate = LocalDateTime.now();
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public String getSpecialization() { return this.specialization; }
    public int getAge() { return this.age; }
    public String getContact() { return this.contactNumber; }
    public String getEmail() { return this.email; }
    public String getRegistrationDate() { return this.registrationDate.toString(); }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setAge(int age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
    public void setContact(String contact) { this.contactNumber = contact; }

    @Override
    public String toString() {
        return ("Doctor ID: " + getId() + ", Name: " + getName() + ", Specialization: " + getSpecialization());
    }
}
