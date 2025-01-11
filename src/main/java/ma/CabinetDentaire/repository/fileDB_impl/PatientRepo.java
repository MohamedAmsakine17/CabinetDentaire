package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.GroupeSanguin;
import ma.CabinetDentaire.entities.enums.Mutuelle;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.entities.enums.StatutPaiment;
import ma.CabinetDentaire.repository.api.IPatientRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class PatientRepo implements IPatientRepo {

    private static final File PATIENT_TABLE = new File("src/main/data/patients.txt");

    private Patient mapToPatient(String fileLine) throws DaoException {
       try{
           //ID|NOM|PRENOM|EMAIL|CIN|SEXE
           //ID|NOM|PRENOM|CIN|ADRESSE|TELEPHONE|EMAIL|PFP|DATA_NAISSANCE|SEXE|GROUP_SANGUIN|MUTUELLE|PROFESSION|DOSSIERMEDICALE

           StringTokenizer st = new StringTokenizer(fileLine, "\\|");

           String value = st.nextToken();
           Long id = Long.parseLong(value);
           value = st.nextToken();
           String nom = (value.equals("null")? null : value);
           value = st.nextToken();
           String prenom = (value.equals("null")? null : value);
           value = st.nextToken();
           String cin = (value.equals("null")? null : value);
           value = st.nextToken();
           String adresse = (value.equals("null")? null : value);
           value = st.nextToken();
           String telephone = (value.equals("null")? null : value);
           value = st.nextToken();
           String email = (value.equals("null")? null : value);
           value = st.nextToken();
           String pfp = (value.equals("null")? null : value);
           value = st.nextToken();
           LocalDate data_de_naissance = LocalDate.parse(value);
           value = st.nextToken();
           Sexe sexe = (value.equals("null") ? null : (value.equals("Homme") ? Sexe.HOMME : Sexe.FEMME ));;
           value = st.nextToken();
           GroupeSanguin groupeSanguin = (value.equals("null") ? null :
                   (value.equals("A_NEGATIF") ? GroupeSanguin.A_NEGATIF
                           : value.equals("AB_NEGATIF") ? GroupeSanguin.AB_NEGATIF
                           : value.equals("B_NEGATIF") ? GroupeSanguin.B_NEGATIF
                           : value.equals("O_NEGATIF") ? GroupeSanguin.O_NEGATIF
                           : value.equals("AB_POSITIF") ? GroupeSanguin.AB_POSITIF
                           : value.equals("A_POSITIF") ? GroupeSanguin.A_POSITIF
                           : value.equals("B_POSITIF") ? GroupeSanguin.B_POSITIF
                           : GroupeSanguin.O_POSITIF));;
           value = st.nextToken();
           Mutuelle mutuelle = (value.equals("null") ? null :
                   (value.equals("CIMR") ? Mutuelle.CIMR
                           : value.equals("CNAM") ? Mutuelle.CNAM
                           : value.equals("CNSS") ? Mutuelle.CNSS
                           : Mutuelle.CNOPS));
           value = st.nextToken();
           String profession = (value.equals("null")? null : value);

           Patient patient = new Patient(id,nom,prenom,cin,adresse,telephone,email,pfp,data_de_naissance,sexe,groupeSanguin,mutuelle,profession);

           return patient;
       } catch (NumberFormatException e){
           throw new DaoException(e);
       }
    }

    private String mapToLine(Patient patient) throws DaoException {
        //ID|NOM|PRENOM|CIN|ADRESSE|TELEPHONE|EMAIL|PFP|DATA_NAISSANCE|SEXE|GROUP_SANGUIN|MUTUELLE|PROFESSION|DOSSIERMEDICALE

        Long id = patient.getId();
        String nom = patient.getNom();
        String prenom = patient.getPrenom();
        String cin = patient.getCin();
        String adresse = patient.getAdresse();
        String telephone = patient.getTelephone();
        String email = patient.getEmail();
        String pfp = patient.getPhotoDeProfile();
        LocalDate data_de_naissance = patient.getDataDeNaissance();
        Sexe sexe = patient.getSexe();
        GroupeSanguin groupeSanguin = patient.getGroupeSanguin();
        Mutuelle mutuelle = patient.getMutuelle();
        String profession = patient.getProfession();


        return id + "|" +
                (nom    == null ? "null": nom)              + "|" +
                (prenom == null ? "null" : prenom)          + "|" +
                (cin  == null ? "null" : cin)               + "|" +
                (adresse == null ? "null" : adresse)        + "|" +
                (telephone == null ? "null" : telephone)    + "|" +
                (email  == null ? "null" : email)           + "|" +
                (pfp == null ? "null" : pfp)                + "|" +
                (data_de_naissance == null ? "null" : data_de_naissance.toString())  + "|" +
                (sexe   == null ? "null" : (sexe.equals(Sexe.FEMME) ? "Femme" : "Homme")) + "|" +
                (groupeSanguin == null ? "null" : groupeSanguin.toString()) + "|" +
                (mutuelle == null ? "null" : mutuelle.toString()) + "|" +
                (profession == null ? "null" : profession)   +
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
    public List<Patient> findUnderAge(int age) throws DaoException {
        return findAll().
                stream().
                filter(patient -> Period.between(patient.getDataDeNaissance(), LocalDate.now()).getYears() <= age).
                toList();
    }

    @Override
    public List<Patient> findByNameLike(String keyword) throws DaoException {
        return findAll().
                stream().
                filter(patient -> (patient.getNom() + patient.getPrenom()).toLowerCase().contains(keyword)).
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
            return element;
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
}
