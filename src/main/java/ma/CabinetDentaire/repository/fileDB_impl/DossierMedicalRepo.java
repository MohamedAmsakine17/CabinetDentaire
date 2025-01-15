package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.StatutPaiment;
import ma.CabinetDentaire.repository.api.IDossierMedicalRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DossierMedicalRepo implements IDossierMedicalRepo {
    private static final File DOSSIER_MEDICAL_FILE = new File("src/main/data/dossier_medical.txt");
    private static PatientRepo patientRepo;

    public PatientRepo getPatientRepo() {
        return patientRepo;
    }

    public void setPatientRepo(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    private DossierMedicale mapToDossier(String fileLine) throws DaoException {
        try{
            // ID|PATIENT_ID|DATE_CREATION|Statut_Paiment
            StringTokenizer st = new StringTokenizer(fileLine, "\\|");

            String value = st.nextToken();
            Long id = Long.parseLong(value);
            // set patient
            value = st.nextToken();
            Long patientId = Long.parseLong(value);
            Patient patient = patientRepo.findById(patientId);

            value = st.nextToken();
            LocalDate dateCreation = LocalDate.parse(value);

            // set dentist

            value = st.nextToken();
            StatutPaiment statutPaiment = (value.equals("null") ? null :
                    (value.equals("en_attente") ? StatutPaiment.EN_ATTENTE
                            : value.equals("paye") ? StatutPaiment.PAYE
                            : StatutPaiment.IMPAYE));

            DossierMedicale dossierMedicale =  new DossierMedicale(id,patient,dateCreation,statutPaiment);

            return dossierMedicale;
        } catch (NumberFormatException e){
            throw new DaoException(e);
        }
    }

    private String mapToLine(DossierMedicale dossierMedicale){
        // ID|PATIENT_ID|DATE_CREATION|Statut_Paiment

        Long id = dossierMedicale.getId();
        Long patientId = dossierMedicale.getPatient().getId();
        LocalDate dateCreation = dossierMedicale.getDateCreation();
        StatutPaiment statutPaiment = dossierMedicale.getStatutPaiment();

        return id + "|" + patientId + "|" + dateCreation + "|" + statutPaiment + System.lineSeparator();

    }

    @Override
    public List<DossierMedicale> findAll() throws DaoException {
        List<DossierMedicale> dossierMedicales = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(DOSSIER_MEDICAL_FILE))){
            reader.lines().skip(1).forEach(line->{
                try {
                    DossierMedicale dossierMedicale = mapToDossier(line);
                    dossierMedicales.add(dossierMedicale);
                } catch (DaoException e) {
                    System.err.println(e.getMessage());
                }
            });
            return dossierMedicales;
        } catch (IOException e){
            throw new DaoException(e);
        }
    }

    @Override
    public DossierMedicale findById(Long id) throws DaoException {
        return findAll()
                .stream()
                .filter(dossierMedicale -> dossierMedicale.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new DaoException("Aucun dossier trouvé ayant le n°" + id));
    }

    @Override
    public DossierMedicale save(DossierMedicale element) throws DaoException {
       try{
            Long maxId = findAll().stream().mapToLong(DossierMedicale::getId).max().orElse(0L);
            element.setId(maxId + 1);
           Files.writeString(
                   DOSSIER_MEDICAL_FILE.toPath(),
                   mapToLine(element),
                   StandardOpenOption.APPEND
           );
           return element;
       } catch (IOException e){
           throw new DaoException(e);
       }
    }

    @Override
    public void update(DossierMedicale element) throws DaoException {

    }

    @Override
    public void delete(DossierMedicale element) throws DaoException {

    }

    @Override
    public void deleteById(Long id) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(DOSSIER_MEDICAL_FILE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(DOSSIER_MEDICAL_FILE));
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

        if(!DOSSIER_MEDICAL_FILE.delete())
            throw new DaoException("Probleme tech dans la suppression du patient");
        if(!tempFile.renameTo(DOSSIER_MEDICAL_FILE))
            throw new DaoException("Probleme tech dans la suppression du patient");
        if(!isDeleted)
            throw new DaoException("Patient n'" + id + " ");
    }

    @Override
    public DossierMedicale findByPatientId(Long id) throws DaoException {
        return findAll()
                .stream()
                .filter(d -> d.getPatient().getId().equals(id))
                .findFirst()
                .orElseThrow(()->new DaoException("Aucun dossier Dp" + id));
    }
}
