package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.Ordonnance;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.entities.enums.TypeConsultation;
import ma.CabinetDentaire.repository.api.IConsultationRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class ConsultationRepo implements IConsultationRepo {
    private static final File CONSULTATION_TABLE = new File("src/main/data/consultations.txt");
    private static DossierMedicalRepo dossierMedicalRepo;

    public void setDossierMedicalRepo(DossierMedicalRepo dossierMedicalRepo) {
        this.dossierMedicalRepo = dossierMedicalRepo;
    }
    private Consultation mapToConsultation(String fileLine) throws DaoException {
        try {
            // ID|TYPE|DATE|ORDONNANCE_ID|DOSSIERMEDICALE_ID
            StringTokenizer st = new StringTokenizer(fileLine, "\\|");

            String value = st.nextToken();
            Long id = Long.parseLong(value);

            value = st.nextToken();
            TypeConsultation type = TypeConsultation.valueOf(value);

            value = st.nextToken();
            LocalDate date = LocalDate.parse(value);

            value = st.nextToken();
//            Long ordonnanceId = (value.equals("null") ? null : Long.parseLong(value));
//            Ordonnance ordonnance = (value.equals("null") ? null : ordonnanceRepo.findById(ordonnanceId));
//            System.out.println(ordonnance);


            value = st.nextToken();
            Long dossierId = Long.parseLong(value);
            DossierMedicale dossier = dossierMedicalRepo.findById(dossierId);

            Consultation consultation = new Consultation(id, type, date, null, dossier);
            //dossier.addConsultation(consultation);

            return consultation;
        } catch (Exception e) {
            throw new DaoException("Error mapping Consultation: " + e.getMessage());
        }
    }

    private String mapToLine(Consultation consultation) {
        // ID|TYPE|DATE|ORDONNANCE_ID|DOSSIERMEDICALE_ID
        return consultation.getIdConsultation() + "|" +
                consultation.getTypeConsultation() + "|" +
                consultation.getDateConsultation() + "|" +
                (consultation.getOrdonnance() == null ? "null" : consultation.getOrdonnance().getIdOrdonnance()) + "|" +
                consultation.getDossierMedicale().getId() +
                System.lineSeparator();
    }

    @Override
    public List<Consultation> findAll() throws DaoException {
        try {
            List<String> lines = Files.readAllLines(CONSULTATION_TABLE.toPath());
            return lines.stream().skip(1).map(line -> {
                try {
                    return mapToConsultation(line);
                } catch (DaoException e) {
                    throw new RuntimeException("Error in Consultation data: " + e.getMessage());
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new DaoException("Error reading Consultation data: " + e.getMessage());
        }
    }

    @Override
    public Consultation findById(Long id) throws DaoException {
        return findAll()
                .stream()
                .filter(consultation -> consultation.getIdConsultation().equals(id))
                .findFirst()
                .orElseThrow(() -> new DaoException("Consultation not found with ID: " + id));
    }

    @Override
    public List<Consultation> findByDossier(DossierMedicale dossier) throws DaoException {
        return findAll()
                .stream()
                .filter(consultation -> consultation.getDossierMedicale().getId().equals(dossier.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Consultation save(Consultation consultation) throws DaoException {
        try {
            Long maxId = findAll().stream().mapToLong(Consultation::getIdConsultation).max().orElse(0L);
            consultation.setIdConsultation(maxId + 1);
            Files.writeString(
                    CONSULTATION_TABLE.toPath(),
                    mapToLine(consultation),
                    StandardOpenOption.APPEND
            );
            return consultation;
        } catch (IOException e) {
            throw new DaoException("Error saving Consultation: " + e.getMessage());
        }
    }

    @Override
    public void update(Consultation consultation) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(CONSULTATION_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(CONSULTATION_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if (currentID.equals(consultation.getIdConsultation().toString())) {
                    writer.write(mapToLine(consultation));
                    isUpdated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new DaoException("Error updating Consultation: " + e.getMessage());
        }

        if (!CONSULTATION_TABLE.delete())
            throw new DaoException("Error deleting original Consultation file");
        if (!tempFile.renameTo(CONSULTATION_TABLE))
            throw new DaoException("Error renaming temporary Consultation file");
        if (!isUpdated)
            throw new DaoException("Consultation with ID " + consultation.getIdConsultation() + " not found");
    }

    @Override
    public void delete(Consultation consultation) throws DaoException {
        deleteById(consultation.getIdConsultation());
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(CONSULTATION_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(CONSULTATION_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if (id.toString().equals(currentID)) {
                    isDeleted = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new DaoException("Error deleting Consultation: " + e.getMessage());
        }

        if (!CONSULTATION_TABLE.delete())
            throw new DaoException("Error deleting original Consultation file");
        if (!tempFile.renameTo(CONSULTATION_TABLE))
            throw new DaoException("Error renaming temporary Consultation file");
        if (!isDeleted)
            throw new DaoException("Consultation with ID " + id + " not found");
    }
}