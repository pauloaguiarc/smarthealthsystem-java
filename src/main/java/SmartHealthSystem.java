import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.Serializable;

/**
 * Class representing a smart health system
 * {@code PetCareScheduler} class represents the main controller for the Smart Health Management system. It manages doctor and patients registrations, appointment scheduling, data persistence, and report generation for all registered patients.
 * @author Paulo Aguiar
 */
public class SmartHealthSystem implements Serializable {

    //Private fields for data structures storing the patients, doctors and appointments, and the scanner taking user input in
    private static HashMap<String, Patient> patients = new HashMap<>();
    private static HashMap<String, Doctor> doctors = new HashMap<>();
    private static HashMap<String, Appointment> appointments = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    @Serial
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        //Start the program system by loading pre-existing data, if applicable
        loadData();

        boolean running = true;
        while(running) {
            System.out.println("\uD83C\uDFE5 Smart Health & Appointment Management System");
            System.out.println("------------------");
            System.out.println("1 - Register a patient");
            System.out.println("2 - Register a doctor");
            System.out.println("3 - Schedule an appointment");
            System.out.println("4 - Display records");
            System.out.println("5 - Generate report");
            System.out.println("6 - Exit");
            System.out.println("-------------------");
            System.out.println("Select your option: ");
            int option = 0;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid, try again.");
            }
            switch(option) {
                case 1:
                    registerPatient();
                    continue;
                case 2:
                    registerDoctor();
                    scheduleAppointment();
                    continue;
                case 3:
                    displayRecords();
                    continue;
                case 4:
                    generateReport();
                    continue;
                case 6:
                    running = false;
                    storeData();
                    System.out.println("Thank you very much for using our Pet Care Scheduler!");
                    break;
                default:
                    break;
            }
        }

        scanner.close();

    }

    /**
     * Registers a Patient object into the system
     * <p>User must provide information regarding a unique ID number, a name, species, age, owner name and contact info to successfully add a new pet to the database.
     */
    public static void registerPatient() {
        String id;
        try {
            System.out.println("Patient Unique ID: ");
            id = scanner.nextLine();
        }
        catch(NullPointerException | InputMismatchException e) {
            System.out.println("Invalid input, ending session.");
            return;
        }
        if (patients.containsKey(id)) {
            System.out.println("Duplicate IDs, please enter a different ID for pet.");
            return;
        }
        System.out.println("Patient Name: ");
        String name = scanner.nextLine();
        int age;
        try {
            System.out.println("Patient Age: ");
            age = scanner.nextInt();
        }
        catch(NumberFormatException | InputMismatchException e) {
            System.out.println("Invalid input, ending session.");
            return;
        }
        scanner.nextLine();
        System.out.println("Patient Address: ");
        String address = scanner.nextLine();
        System.out.println("Patient Phone Contact: ");
        String contact = scanner.nextLine();
        System.out.println("Would you like to add any medical history for this patient right now or save it for later (Y/N): ");
        String choice = scanner.nextLine().toUpperCase();
        if (choice == "Y") {
            boolean go = true;
            List<String> medHist = new ArrayList<>();
            while(go) {
                System.out.println("Medical History (one item at a time): ");
                medHist.add(scanner.nextLine());
                System.out.println("Finished and exit? (Y/N) ");
                String choice2 = scanner.nextLine().toUpperCase();
                if (choice2 == "Y") {
                    go = false;
                }
            }
            patients.put(id, new Patient(id, name, age, address, contact, medHist));
            System.out.println("Patient registered successfully!");
            return;
        }
        patients.put(id, new Patient(id, name, age, address, contact));
        System.out.println("Patient registered successfully!");
    }

    public static void registerDoctor() {
        String id;
        try {
            System.out.println("Doctor Unique ID: ");
            id = scanner.nextLine();
        }
        catch(NullPointerException | InputMismatchException e) {
            System.out.println("Invalid input, ending session.");
            return;
        }
        if (doctors.containsKey(id)) {
            System.out.println("Duplicate IDs, please enter a different ID for doctor.");
            return;
        }
        System.out.println("Doctor Name: ");
        String name = scanner.nextLine();
        int age;
        try {
            System.out.println("Doctor Age: ");
            age = scanner.nextInt();
        }
        catch(NumberFormatException | InputMismatchException e) {
            System.out.println("Invalid input, ending session.");
            return;
        }
        scanner.nextLine();
        System.out.println("Doctor Specialization: ");
        String spec = scanner.nextLine();
        System.out.println("Doctor Phone Contact: ");
        String contact = scanner.nextLine();
        System.out.println("Doctor Email: ");
        String email = scanner.nextLine();
        doctors.put(id, new Doctor(id, name, spec, age, contact, email));
        System.out.println("Doctor registered successfully!");
    }

    /**
     * Schedules an appointment for a pet in the system
     * <p>User must provide information regarding an ID number that traces back to a pet stored in the database, as well as an appointment type, and date and time in the future, to successfully schedule a new appointment to a pet in the system.
     */
    public static void scheduleAppointment() {
        System.out.println("Patient ID for appointment setting: ");
        String id = scanner.nextLine();
        System.out.println("Doctor ID for appointment setting: ");
        String idi = scanner.nextLine();

        if(!patients.containsKey(id) || !doctors.containsKey(idi)) {
            System.out.println("Invalid ID provided.");
            return;
        }

        LocalDateTime datetime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.println("Appointment date (yyyy-MM-dd HH:mm): ");
            datetime = LocalDateTime.parse(scanner.nextLine(), formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format.");
            return;
        }

        for(String key: appointments.keySet()) {
            if(appointments.get(key).getDoctorId().equals(idi) && appointments.get(key).getDateTime().equals(datetime)) {
                System.out.println("Doctor " + doctors.get(idi).getName() + " is not available at that time - no overlapping appointments." );
                return;
            }
        }

        for(String key: appointments.keySet()) {
            if(appointments.get(key).getPatientId().equals(id) && appointments.get(key).getDateTime().equals(datetime)) {
                System.out.println("Patient " + doctors.get(idi).getName() + " is already booked for that time - no overlapping appointments." );
                return;
            }
        }

        System.out.println("Appointment reason: ");
        String reason = scanner.nextLine();

        try {
            if(datetime.isAfter(LocalDateTime.now())) {
                String finalId = idGenerator();
                appointments.put(finalId, new Appointment(finalId, id, idi, reason, datetime));
                System.out.println("Appointment scheduled successfully.");
                return;
            }
        }
        catch (NullPointerException e) {
            System.out.println("Error in appointment scheduling. Try again later!");
            return;
        }
        System.out.println("Appointments scheduling unsuccessfull - appointments can only be scheduled for the future.");
    }

    /**
     * Displays the records of patients and appointments in the system
     * <p>User must provide information regarding a unique ID number, a name, species, age, owner name and contact info to successfully add a new pet to the database.
     */
    public static void displayRecords() {
        System.out.println("1 - View all registered patients");
        System.out.println("2 - View all registered doctors");
        System.out.println("3 - All appointments for a specific patient");
        System.out.println("4 - All appointments for a specific doctor");
        System.out.println("5 - Upcoming appointments for all doctors");
        System.out.println("6 - Overdue or missed appointments");
        System.out.println("7 - All prescriptions for a specific patient");
        System.out.println("8 - Expired prescriptions");
        System.out.println("9 - Prescriptions requiring a refill soon");
        System.out.println("-------------------");
        System.out.println("Select your option: ");
        int option = 0;
        try {
            option = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid, try again.");
        }
        switch(option) {
            case 1:
                System.out.println("Patients registered: ");
                for(String p: patients.keySet()) {
                    System.out.println(patients.get(p).toString());
                }
            case 2:
                System.out.println("Doctors registered: ");
                for(String d: doctors.keySet()) {
                    System.out.println(doctors.get(d).toString());
                }
                break;
            case 3:
                System.out.println("Patient ID for appointment showing: ");
                String id = scanner.nextLine();
                if(!patients.containsKey(id)) {
                    System.out.println("No patients with such ID on system.");
                    return;
                }
                System.out.println("All appointments for " + patients.get(id).getName() + ": ");
                for (String a: appointments.keySet()) {
                    if(appointments.get(a).getPatientId() == id) {System.out.println(appointments.get(a).toString());}
                }
                break;
            case 4:
                System.out.println("Doctor ID for appointment showing: ");
                String idt = scanner.nextLine();
                if(!patients.containsKey(idt)) {
                    System.out.println("No doctors with such ID on system.");
                    return;
                }
                System.out.println("All appointments for " + doctors.get(idt).getName() + ": ");
                for (String a: appointments.keySet()) {
                    if(appointments.get(a).getDoctorId() == idt) {System.out.println(appointments.get(a).toString());}
                }
                break;
            case 5:
               System.out.println("All upcoming appointments:");
                for(String a: appointments.keySet()) {
                    if(appointments.get(a).getDateTime().isAfter(LocalDateTime.now())) {
                        System.out.println(appointments.get(a).toString());
                    }
                }
                break;
            case 6:
                System.out.println("Overdue or missed appointments:");
                for(String a: appointments.keySet()) {
                    Appointment temp = appointments.get(a);
                    if(temp.getDateTime().isBefore(LocalDateTime.now()) && !temp.isCompleted()) {
                        System.out.println(appointments.get(a).toString());
                    }
                }
                break;
            case 7:
                System.out.println("Patient ID for prescription showing: ");
                String ide = scanner.nextLine();
                if(!patients.containsKey(ide)) {
                    System.out.println("No patients with such ID on system.");
                    return;
                }
                System.out.println("All prescriptions for " + patients.get(ide).getName() + ": ");
                for(Prescription pr: patients.get(ide).getRecentPrescriptions()) {
                    System.out.println(pr);
                }
                break;
            case 8:
                System.out.println("Expired prescriptions:");
                for(String pa: patients.keySet()) {
                    for(Prescription pr: patients.get(pa).getRecentPrescriptions()) {
                        if(pr.getEndDate().isAfter(LocalDateTime.now())) {
                            System.out.println(pr);
                        }
                    }
                }
                break;
            case 9:
                System.out.println("Prescriptions needing a refill soon:");
                for(String pa: patients.keySet()) {
                    for(Prescription pr: patients.get(pa).getRecentPrescriptions()) {
                        if(pr.isRefillNeeded()) {
                            System.out.println(pr);
                        }
                    }
                }
                break;
            default:
                System.out.println("Invalid selection!");
                break;
        }
    }

    /**
     * Stores the pet scheduler system data into a file in the system
     *
     */
    public static void storeData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("patients.ser"))) {
            out.writeObject(patients);
        }
        catch (IOException e) {
            System.out.println("Error in saving system data.");
            e.printStackTrace();
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("doctors.ser"))) {
            out.writeObject(doctors);
        }
        catch (IOException e) {
            System.out.println("Error in saving system data.");
            e.printStackTrace();
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("appointments.ser"))) {
            out.writeObject(appointments);
        }
        catch (IOException e) {
            System.out.println("Error in saving system data.");
            e.printStackTrace();
        }
        System.out.println("Pet System Data saved successfully!");
    }

    /**
     * Generates a full report of upcoming appointments for next week, as well as patients that are overdue for a vet visit
     *
     */
    public static void generateReport() {
        //total patient doctors appointment, most common seeked speciliaziation (by appointment count)

        System.out.println("Doctor Workload Summary: ");

        ArrayList<String> specs = new ArrayList<>();
        for (String d: doctors.keySet()) {
            int countdoc = 0;
            System.out.println(doctors.get(d).getName() + ":");
            for(String a: appointments.keySet()) {
                if(appointments.get(a).getDoctorId() == d) {
                    countdoc++;
                }
                specs.add(doctors.get(appointments.get(a).getDoctorId()).getSpecialization());
            }
            System.out.println("Number of appointments - " + countdoc);
        }
        System.out.println("Most sought specialization: (FEATURE UNDER DEVELOPMENT)");

        System.out.println("------------------------------ ");
        System.out.println("Patient Visit Frequency: ");

        int paticount = 0;
        for (String p: patients.keySet()) {
            for (String a: appointments.keySet()) {
                if(appointments.get(a).getPatientId() == p) {
                    paticount++;
                }
            }
            System.out.println(patients.get(p).getName() + " - " + paticount);
        }
    }

    /**
     * Loads the pet scheduler system data from a file in the system
     *
     */
    public static void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("patients.ser"))) {
            patients = (HashMap<String, Patient>) in.readObject();
            System.out.println("Patients Data loaded successfully!");
        }
        catch (FileNotFoundException e) {
            System.out.println("No saved data for patients found. Starting a new fresh system database.");
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in loading patients system data.");
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("doctors.ser"))) {
            doctors = (HashMap<String, Doctor>) in.readObject();
            System.out.println("Doctors Data loaded successfully!");
        }
        catch (FileNotFoundException e) {
            System.out.println("No saved data for doctors found. Starting a new fresh system database.");
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in loading doctors system data.");
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("appointments.ser"))) {
            appointments = (HashMap<String, Appointment>) in.readObject();
            System.out.println("Appointments Data loaded successfully!");
        }
        catch (FileNotFoundException e) {
            System.out.println("No saved data for appointments found. Starting a new fresh system database.");
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in loading appointments system data.");
        }
    }

    //This ID Generator code clearly does not follow usual and recommended standards, however as it is my first experience building something of the kind, I decided to build something functional out of my own knowledge only
    public static String idGenerator() {
        int found = 0;
        for(int i = 1; i < 199999999; i++) {
            if (!(appointments.containsKey(i))) {
                found = i;
                break;
            }
        }
        return Integer.toString(found);
    }

}
