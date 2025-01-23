package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.InterventionMedecin;
import ma.CabinetDentaire.repository.api.IInterventionMedecinRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class InterventionMedecinRepo implements IInterventionMedecinRepo {
    private static final File INTERVENTION_TABLE = new File("src/main/data/interventions.txt");

    private InterventionMedecin mapToIntervention(String fileLine) throws DaoException {
        try {
            StringTokenizer st = new StringTokenizer(fileLine, "\\|");

            //ID|DENT|PRIX_PATIENT|NOTE_MEDECIN|CONSULTATION_ID|ACTE_ID

            Long id = Long.parseLong(st.nextToken());
            Long dent = Long.parseLong(st.nextToken());
            Double prixPatient = Double.parseDouble(st.nextToken());
            String noteMedecin = st.nextToken();
            Long consultationId = Long.parseLong(st.nextToken());
            Long acteId = Long.parseLong(st.nextToken());

            Consultation consultation = AppFactory.getConsultationRepo().findById(consultationId);

            Acte acte = AppFactory.getActeRepo().findById(acteId);

            return new InterventionMedecin(id, dent, prixPatient, noteMedecin, consultation, acte);
        } catch (Exception e) {
            throw new DaoException("Error mapping InterventionMedecin: " + e.getMessage());
        }
    }

    private String mapToLine(InterventionMedecin intervention) {
        return intervention.getId() + "|" +
                intervention.getDent() + "|" +
                intervention.getPrixPatient() + "|" +
                intervention.getNoteMedecin() + "|" +
                intervention.getConsultation().getIdConsultation() + "|" +
                intervention.getActe().getId() +
                System.lineSeparator();
    }

    @Override
    public List<InterventionMedecin> findAll() throws DaoException {
        try {
            List<String> lines = Files.readAllLines(INTERVENTION_TABLE.toPath());
            return lines.stream().skip(1).map(line -> {
                try {
                    return mapToIntervention(line);
                } catch (DaoException e) {
                    throw new RuntimeException("Error in InterventionMedecin data: " + e.getMessage());
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new DaoException("Error reading InterventionMedecin data: " + e.getMessage());
        }
    }

    @Override
    public InterventionMedecin findById(Long id) throws DaoException {
        return findAll()
                .stream()
                .filter(intervention -> intervention.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new DaoException("InterventionMedecin not found with ID: " + id));
    }

    @Override
    public InterventionMedecin save(InterventionMedecin intervention) throws DaoException {
        try {
            Long maxId = findAll().stream().mapToLong(InterventionMedecin::getId).max().orElse(0L);
            intervention.setId(maxId + 1);
            Files.writeString(
                    INTERVENTION_TABLE.toPath(),
                    mapToLine(intervention),
                    StandardOpenOption.APPEND
            );
            return intervention;
        } catch (IOException e) {
            throw new DaoException("Error saving InterventionMedecin: " + e.getMessage());
        }
    }

    @Override
    public void update(InterventionMedecin intervention) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(INTERVENTION_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(INTERVENTION_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if (currentID.equals(intervention.getId().toString())) {
                    writer.write(mapToLine(intervention));
                    isUpdated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new DaoException("Error updating InterventionMedecin: " + e.getMessage());
        }

        if (!INTERVENTION_TABLE.delete())
            throw new DaoException("Error deleting original InterventionMedecin file");
        if (!tempFile.renameTo(INTERVENTION_TABLE))
            throw new DaoException("Error renaming temporary InterventionMedecin file");
        if (!isUpdated)
            throw new DaoException("InterventionMedecin with ID " + intervention.getId() + " not found");
    }

    @Override
    public void delete(InterventionMedecin intervention) throws DaoException {
        deleteById(intervention.getId());
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(INTERVENTION_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(INTERVENTION_TABLE));
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
            throw new DaoException("Error deleting InterventionMedecin: " + e.getMessage());
        }

        if (!INTERVENTION_TABLE.delete())
            throw new DaoException("Error deleting original InterventionMedecin file");
        if (!tempFile.renameTo(INTERVENTION_TABLE))
            throw new DaoException("Error renaming temporary InterventionMedecin file");
        if (!isDeleted)
            throw new DaoException("InterventionMedecin with ID " + id + " not found");
    }

    @Override
    public List<InterventionMedecin> findByConsultation(Long consultationId) throws DaoException {
        return findAll()
                .stream()
                .filter(intervention -> intervention.getConsultation().getIdConsultation().equals(consultationId))
                .collect(Collectors.toList());
    }
}