package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.GroupeSanguin;
import ma.CabinetDentaire.entities.enums.Mutuelle;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.repository.api.IDossierMedicalRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.StringTokenizer;

public class DossierMedicalRepo implements IDossierMedicalRepo {
    private static final File DOSSIER_MEDICAL_FILE = new File("src/main/data/dossier_medical.txt");

    private Patient mapToPatient(String fileLine) throws DaoException {
        try{
            // ID|PATIENT_ID|NUMERO_DOSSIER|DATE_CREATION|DENTIST_ID
            StringTokenizer st = new StringTokenizer(fileLine, "\\|");

            String value = st.nextToken();
            Long id = Long.parseLong(value);
            value = st.nextToken();
            Long patientId = Long.parseLong(value);
            value = st.nextToken();
            String numeroDossier = (value.equals("null") ? null : value);
            value = st.nextToken();
            LocalDate dateCreation = LocalDate.parse(value);
            value = st.nextToken();
            Long dentisetId = Long.parseLong(value);
            return
            //return new Patient(id,nom,prenom,cin,adresse,telephone,email,pfp,data_de_naissance,sexe,groupeSanguin,mutuelle,profession,dossierMedicale);
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
    public List findAll() throws DaoException {
        return List.of();
    }

    @Override
    public Object findById(Object o) throws DaoException {
        return null;
    }

    @Override
    public Object save(Object element) throws DaoException {
        return null;
    }

    @Override
    public void update(Object element) throws DaoException {

    }

    @Override
    public void delete(Object element) throws DaoException {

    }

    @Override
    public void deleteById(Object o) throws DaoException {

    }
}
