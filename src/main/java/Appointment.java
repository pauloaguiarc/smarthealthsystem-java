import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Appointment implements Serializable {

    private String appointmentId;
    private String doctorId;
    private String patientId;
    private String reason;
    private LocalDateTime datetime;
    private boolean completion;
    @Serial
    private static final long serialVersionUID = 1L;

    public Appointment(String id, String doctor, String patient, String reason, LocalDateTime time) {
        this.appointmentId = id;
        this.doctorId = doctor;
        this.patientId = patient;
        this.reason = reason;
        this.datetime = time;
    }

    public String getAppointmentId() {
        return this.appointmentId;
    }
    public String getDoctorId() {
        return this.doctorId;
    }
    public String getPatientId() {
        return this.patientId;
    }
    public String getReason() {
        return this.reason;
    }
    public boolean isOverdue() {
        if ((!isCompleted()) && (getDateTime().isAfter(LocalDateTime.now()))) {
            return true;
        }
        return false;
    }
    public LocalDateTime getDateTime() {
        return this.datetime;
    }
    public boolean isCompleted() {
        return completion;
    }

    public void setAppointmentId(String id) {
        this.appointmentId = id;
    }
    public void setDoctorId(String id) {
        this.doctorId = id;
    }
    public void setPatientId(String id) {
        this.patientId = id;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public void markCompleted() { this.completion = true; }
    public void setDateTime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Appointment Reason: " + getReason() + ", Date & Time: " + getDateTime() + ", Completed: " + isCompleted();
    }
}
