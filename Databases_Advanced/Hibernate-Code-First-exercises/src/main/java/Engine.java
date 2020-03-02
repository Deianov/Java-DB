import constants.PersistenceUnit;
import entities.hospital.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Engine implements Runnable {
    private final EntityManager entityManager;
    private final PersistenceUnit persistenceUnit;
    private final Scanner scanner;

    Engine(EntityManager entityManager, PersistenceUnit persistenceUnit) {
        this.entityManager = entityManager;
        this.persistenceUnit = persistenceUnit;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {

        switch (persistenceUnit){
            case code_first_ex :
                System.out.println("Exercise: Hibernate Code First");
                break;
            case gringotts :
                System.out.println("1. Gringotts Database");
                break;
            case sales :
                System.out.println("2. Sales Database");
                break;
            case university :
                System.out.println("3. University System");
                break;
            case hospital :
                System.out.println("4. Hospital Database");
                this.taskHospital();
                break;
            case payment :
                System.out.println("5. Bills Payment System");
                break;
        }
    }


    private void taskHospital(){
        initDatabaseDiagnosesAndMedicaments();

        Patient patient = setCurrentPatient();
        if(patient == null){
            return;
        }
        System.out.printf("%n***%n***%n***%n");
        System.out.println("[1]New patient review | [2]Patient history : ");
        String input = scanner.nextLine();
        int number;
        try {
            number = Integer.parseInt(input);

        }catch (NumberFormatException ex){
            number = 0;
        }

        // new patient review
        if (number == 1){

            entityManager.refresh(patient);
            entityManager.getTransaction().begin();

            Visitation visitation = new Visitation(patient);
            Prescription prescription = new Prescription(visitation);

            newPatientReview(patient, visitation, prescription);

            entityManager.persist(visitation);
            entityManager.persist(prescription);

            entityManager.flush();
            entityManager.getTransaction().commit();
        }
        // Patient history
        else if (number == 2){
            printPatientHistory(patient);
        }
    }

    private void printPatientHistory(Patient patient){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.printf("%n***%n***%n***%n");

        System.out.printf("History for patient : %s %s",
                patient.getFirstName(),
                patient.getLastName()
        );
        try {
            entityManager.createQuery("select v from Visitation v where v.patient=:patient", Visitation.class)
                    .setParameter("patient", patient)
                    .getResultList()
                    .forEach(visitation -> {
                        System.out.printf("%n%nVisitation : %s", visitation.getDate().format(formatter));
                        System.out.printf("%n\tDiagnose : %s", visitation.getPrescription().getDiagnose().getName());
                        System.out.printf("%n\tMedicaments:");
                        visitation.getPrescription().getMedicaments().forEach(medicament ->
                                System.out.printf("%n\t\t%s", medicament.getName())
                        );
                    });

        } catch (NoResultException ex){
            System.out.println("Not found visitation");
        }
    }

    private void newPatientReview(Patient patient, Visitation visitation, Prescription prescription){
        System.out.printf("%n***%n***%n***%n");
        Diagnose diagnose;
        try {
            System.out.println("Set diagnose index [1-220] : ");
            int indexDiagnose = Integer.parseInt(scanner.nextLine());

            diagnose = entityManager.find(Diagnose.class, indexDiagnose);
            if(diagnose == null){
                System.out.println("Not found diagnose index.");
            } else {
                prescription.setDiagnose(diagnose);
            }

        }catch (NumberFormatException e){
            System.out.println("Bad diagnose index.");
            return;
        }

        // add medicaments
        while (true){
            System.out.println("Add medicament index [0-20]");
            String input = scanner.nextLine();

            if (input.isEmpty()) break;

            try {
                int index = Integer.parseInt(input);

                Medicament medicament =
                        entityManager.find(Medicament.class, index);

                if(medicament == null){
                    System.out.println("Not found medicament with index : " + index);
                } else {
                    prescription.addMedicament(medicament);
                    System.out.println("Added medicament : " + medicament.getName());
                }

            }catch (NumberFormatException e){
                System.out.println("Bad index format.");
            }
        }
    }

    private void initDatabaseDiagnosesAndMedicaments() {
        try {
            entityManager.createQuery("select d from Diagnose d", Diagnose.class)
                    .setMaxResults(1)
                    .getSingleResult();

        } catch (NoResultException ex) {
            entityManager.getTransaction().begin();

            Arrays.stream(constants.Diagnoses.names)
                    .distinct()
                    .forEach(name -> entityManager.persist(new Diagnose(name)));

            entityManager.flush();
            entityManager.getTransaction().commit();
        }

        try {
            entityManager.createQuery("select m from Medicament m ", Medicament.class)
                    .setMaxResults(1)
                    .getSingleResult();

        } catch (NoResultException ex) {
            entityManager.getTransaction().begin();
            int grams = 1;
            for (int i = 0; i < 20; i++) {
                entityManager.persist(new Medicament("Aspirin " + grams++ + " mg."));
            }
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
    }

    private Patient setCurrentPatient(){

        Patient patient = null;

        try {
            patient =
            entityManager.createQuery("select v from Visitation v order by v.id desc", Visitation.class)
                    .setMaxResults(1)
                    .getSingleResult()
                    .getPatient();

        }catch (NoResultException ex){ }

        String firstName;
        String lastName;

        System.out.printf("%n***%n***%n***%n");
        if (patient == null){
            System.out.println("Enter patient name : ");
        } else {
            System.out.printf("Enter patient name <%s %s> : ",
                    patient.getFirstName(),
                    patient.getLastName());
        }
        String input = scanner.nextLine().trim();

        if (!input.isEmpty()){
            String[] data = input.split("\\s+");
            firstName = data.length > 1 ? data[0] : "";
            lastName = data.length > 1 ? data[1] : data[0];

            // get from database
            try {
                patient =
                entityManager.createQuery("select p from Patient p where p.firstName=:firstName and p.lastName=:lastName", Patient.class)
                        .setParameter("firstName", firstName)
                        .setParameter("lastName", lastName)
                        .getSingleResult();

            // create new patient
            }catch (NoResultException ex){
                entityManager.getTransaction().begin();
                patient = new Patient(firstName, lastName);
                entityManager.persist(patient);
                entityManager.flush();
                entityManager.getTransaction().commit();
            }
        }
        return patient;
    }
}
