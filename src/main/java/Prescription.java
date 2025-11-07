import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Prescription implements Serializable {

    private int prescriptionId;
    private int doctorId;
    private int patientId;
    private String medicationName;
    private String dosage;
    private String notes;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean refill;
    @Serial
    private static final long serialVersionUID = 1L;

    public Prescription(int preId, int docId, int patId, String name, String dosage, LocalDateTime start, LocalDateTime end) {
        this.prescriptionId = preId;
        this.doctorId = docId;
        this.patientId = patId;
        this.medicationName = name;
        this.dosage = dosage;
        this.startDate = start;
        this.endDate = end;
    }

    public Prescription(int preId, int docId, int patId, String name, String dosage, LocalDateTime start, LocalDateTime end, String notes) {
        this.prescriptionId = preId;
        this.doctorId = docId;
        this.patientId = patId;
        this.medicationName = name;
        this.dosage = dosage;
        this.startDate = start;
        this.endDate = end;
        this.notes = notes;
    }

    public int getPrescriptionId() { return this.prescriptionId; }
    public int getDoctorId() { return this.doctorId; }
    public int getPatientId() { return this.patientId; }
    public String getMedicationName() { return this.medicationName; }
    public String getDosage() { return this.dosage; }
    public String getNotes() { return this.notes; }
    public LocalDateTime getStartDate() { return this.startDate; }
    public LocalDateTime getEndDate() { return this.endDate; }
    public boolean isRefillNeeded() { return this.refill; }

    public void setPrescriptionId(int id) { this.prescriptionId = id; }
    public void setDoctorId(int id) { this.prescriptionId = id; }
    public void setPatientId(int id) { this.prescriptionId = id; }
    public void setMedicationName(String name) { this.medicationName = name; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setStartDate(LocalDateTime date) { this.startDate = date; }
    public void setEndDate(LocalDateTime date) { this.endDate = date; }
    public void setRefill(boolean value) {this.refill = value;}

    @Override
    public String toString() {
        return "Prescription Medication: " + getMedicationName() + ", Dosage: " + getDosage() + ", Start Date: " + getStartDate() + ", End Date: " + getEndDate() + ", Extra Notes: " + getNotes();
    }

    public boolean isExpired() {
        if (getEndDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }
}
