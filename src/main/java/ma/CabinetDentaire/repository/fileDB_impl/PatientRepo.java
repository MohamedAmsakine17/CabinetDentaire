package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.repository.api.IPatientRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class PatientRepo implements IPatientRepo {

    private static final File PATIENT_TABLE = new File("src/main/data/patients.txt");

    private Patient mapToPatient(String fileLine) throws DaoException {
       try{
           //ID|NOM|PRENOM|EMAIL|CIN|SEXE
           StringTokenizer st = new StringTokenizer(fileLine, "\\|");

           String value = st.nextToken();
           Long id = Long.parseLong(value);
           value = st.nextToken();
           String nom = (value.equals("null") ? null : value);
           value = st.nextToken();
           String prenom = (value.equals("null") ? null : value);
           value = st.nextToken();
           String email = (value.equals("null") ? null :value);
           value = st.nextToken();
           String cin = (value.equals("null") ? null : value);
           value = st.nextToken();
           Sexe sexe = (value.equals("null") ? null : (value.equals("Homme") ? Sexe.HOMME : Sexe.FEMME ));;

           Patient patient = new Patient(id,nom,prenom,email,cin,sexe);
           return patient;
       } catch (NumberFormatException e){
           throw new DaoException(e);
       }
    }

    private String mapToLine(Patient patient){
        Long id = patient.getId();
        String nom = patient.getNom();
        String prenom = patient.getPrenom();
        String email = patient.getEmail();
        String cin = patient.getCin();
        Sexe sexe = patient.getSexe();

        return id + "|" +
                (nom    == null ? "null": nom)              + "|" +
                (prenom == null ? "null" : prenom)          + "|" +
                (email  == null ? "null" : email)           + "|" +
                (cin    == null ? "null" : cin)             + "|" +
                (sexe   == null ? "null" : sexe.toString()) +
                System.lineSeparator();
    }

    @Override
    public Patient findByCIN(String cin) throws DaoException {
        return findAll().
                stream().
                filter(patient -> patient.getCin().equals(cin)).
                findFirst().
                orElseThrow(()  -> new DaoException("Patient not found"));
    }

    @Override
    public List<Patient> findByCINLike(String keyword) throws DaoException {
        return findAll().
                stream().
                filter(patient -> patient.getCin().contains(keyword)).
                toList();
    }

    @Override
    public List<Patient> findBySexe(Sexe sexe) throws DaoException {
        return findAll().
                stream().
                filter(patient -> patient.getSexe().equals(sexe)).
                toList();
    }

    @Override
    public List<Patient> findAll() throws DaoException {
        try {
            List<String> lines = Files.readAllLines(PATIENT_TABLE.toPath());
            return lines.stream().skip(1).map(line -> {
                try {
                    return mapToPatient(line);
                } catch (DaoException e) {
                   throw new RuntimeException("Donnee patient errone dans la base de donnee!!");
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new DaoException("Probleme dans la lectures des donnees patients!");
        }
    }

    @Override
    public Patient findById(Long id) throws DaoException {
        return findAll().
                stream().
                filter(patient -> patient.getId().equals(id)).
                findFirst().
                orElseThrow(()  -> new DaoException("Patient not found"));
    }

    @Override
    public Patient save(Patient element) throws DaoException {
        try {
            Long maxId = findAll().stream().mapToLong(Patient::getId).max().orElse(0L);
            element.setId(++maxId);
            Files.writeString(
                                PATIENT_TABLE.toPath(),
                                mapToLine(element),
                                StandardOpenOption.APPEND
            );
            return null;
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Patient element) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(PATIENT_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(PATIENT_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));)
        {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if(currentID.equals(element.getId().toString())) {
                    writer.write(mapToLine(element));
                    isUpdated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            throw new DaoException(e);
        }

        if(!PATIENT_TABLE.delete())
            throw new DaoException("Probleme tech dans la modification du patient");
        if(!tempFile.renameTo(PATIENT_TABLE))
            throw new DaoException("Probleme tech dans la modification du patient");
        if(!isUpdated)
            throw new DaoException("Patient n'" + element.getId() + " ");

    }

    @Override
    public void delete(Patient element) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(PATIENT_TABLE.getAbsolutePath() + ".tmp");

        if(!PATIENT_TABLE.delete())
            throw new DaoException("Probleme tech dans la suppression du patient");
        if(!tempFile.renameTo(PATIENT_TABLE))
            throw new DaoException("Probleme tech dans la suppression du patient");
        if(!isDeleted)
            throw new DaoException("Patient n'" + element.getId() + " ");
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(PATIENT_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(PATIENT_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));)
        {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if(id.toString().equals(currentID)) {
                    isDeleted = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            throw new DaoException(e);
        }

        if(!PATIENT_TABLE.delete())
            throw new DaoException("Probleme tech dans la suppression du patient");
        if(!tempFile.renameTo(PATIENT_TABLE))
            throw new DaoException("Probleme tech dans la suppression du patient");
        if(!isDeleted)
            throw new DaoException("Patient n'" + id + " ");
    }

    public static void main(String[] args) throws DaoException {
        //new PatientRepo().findAll().forEach(System.out::println);
        //new PatientRepo().findBySexe(Sexe.HOMME).forEach(System.out::println);
        //new PatientRepo().findByCINLike("A6").forEach(System.out::println);

        var p = new Patient(null, "Jahili","Ayoub" , "AyoubJahil@gmail.com","A6789",Sexe.HOMME);
        new PatientRepo().save(p);

        new PatientRepo().findAll().forEach(System.out::println);
        new PatientRepo().update(new Patient(7L, "amsa","koko" , "koko@gmail.com","LB2432",Sexe.FEMME));
    }
}
