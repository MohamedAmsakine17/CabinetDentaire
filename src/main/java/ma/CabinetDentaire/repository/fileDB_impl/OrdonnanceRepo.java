package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.Ordonnance;
import ma.CabinetDentaire.repository.api.IOrdonnanceRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class OrdonnanceRepo implements IOrdonnanceRepo {
    private static final File ORDONNANCE_TABLE = new File("src/main/data/ordonnances.txt");
    private static ConsultationRepo consultationRepo;

    public void setConsultationRepo(ConsultationRepo consultationRepo) {
        this.consultationRepo = consultationRepo;
    }

    private Ordonnance mapToOrdonnance(String fileLine) throws DaoException {
        try {
            // ID|DATE|CONSULTATION_ID
            StringTokenizer st = new StringTokenizer(fileLine, "\\|");

            String value = st.nextToken();
            Long id = Long.parseLong(value);

            value = st.nextToken();
            LocalDate date = LocalDate.parse(value);

            value = st.nextToken();
            Long consultationId = Long.parseLong(value);
            Consultation consultation = consultationRepo.findById(consultationId);

            return new Ordonnance(id, date, consultation);
        } catch (Exception e) {
            throw new DaoException("Error mapping Ordonnance: " + e.getMessage());
        }
    }

    private String mapToLine(Ordonnance ordonnance) {
        // ID|DATE|CONSULTATION_ID
        return ordonnance.getIdOrdonnance() + "|" +
                ordonnance.getDate() + "|" +
                ordonnance.getConsultationConcernee().getIdConsultation() +
                System.lineSeparator();
    }

    @Override
    public List<Ordonnance> findAll() throws DaoException {
        try {
            List<String> lines = Files.readAllLines(ORDONNANCE_TABLE.toPath());
            return lines.stream().skip(1).map(line -> {
                try {
                    return mapToOrdonnance(line);
                } catch (DaoException e) {
                    throw new RuntimeException("Error in Ordonnance data: " + e.getMessage());
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new DaoException("Error reading Ordonnance data: " + e.getMessage());
        }
    }

    @Override
    public Ordonnance findById(Long id) throws DaoException {
        return findAll()
                .stream()
                .filter(ordonnance -> ordonnance.getIdOrdonnance().equals(id))
                .findFirst()
                .orElseThrow(() -> new DaoException("Ordonnance not found with ID: " + id));
    }

    @Override
    public Ordonnance save(Ordonnance ordonnance) throws DaoException {
        try {
            Long maxId = findAll().stream().mapToLong(Ordonnance::getIdOrdonnance).max().orElse(0L);
            ordonnance.setIdOrdonnance(maxId + 1);
            Files.writeString(
                    ORDONNANCE_TABLE.toPath(),
                    mapToLine(ordonnance),
                    StandardOpenOption.APPEND
            );
            return ordonnance;
        } catch (IOException e) {
            throw new DaoException("Error saving Ordonnance: " + e.getMessage());
        }
    }

    @Override
    public void update(Ordonnance ordonnance) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(ORDONNANCE_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDONNANCE_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if (currentID.equals(ordonnance.getIdOrdonnance().toString())) {
                    writer.write(mapToLine(ordonnance));
                    isUpdated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new DaoException("Error updating Ordonnance: " + e.getMessage());
        }

        if (!ORDONNANCE_TABLE.delete())
            throw new DaoException("Error deleting original Ordonnance file");
        if (!tempFile.renameTo(ORDONNANCE_TABLE))
            throw new DaoException("Error renaming temporary Ordonnance file");
        if (!isUpdated)
            throw new DaoException("Ordonnance with ID " + ordonnance.getIdOrdonnance() + " not found");
    }

    @Override
    public void delete(Ordonnance ordonnance) throws DaoException {
        deleteById(ordonnance.getIdOrdonnance());
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(ORDONNANCE_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDONNANCE_TABLE));
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
            throw new DaoException("Error deleting Ordonnance: " + e.getMessage());
        }

        if (!ORDONNANCE_TABLE.delete())
            throw new DaoException("Error deleting original Ordonnance file");
        if (!tempFile.renameTo(ORDONNANCE_TABLE))
            throw new DaoException("Error renaming temporary Ordonnance file");
        if (!isDeleted)
            throw new DaoException("Ordonnance with ID " + id + " not found");
    }

    @Override
    public Ordonnance findByConsultation(Long consultationId) throws DaoException {
        return findAll()
                .stream()
                .filter(ordonnance -> ordonnance.getConsultationConcernee().getIdConsultation().equals(consultationId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Ordonnance> findAllByDossierId(Long dossierId) throws DaoException {
        return findAll()
                .stream()
                .filter(ordonnance -> ordonnance.getConsultationConcernee().getDossierMedicale().getId().equals(dossierId))
                .collect(Collectors.toList());
    }

    public OrdonnanceRepo(){}

    public static void main(String[] args) throws DaoException {
        new OrdonnanceRepo().findAll().forEach(c -> System.out.println(c));
    }
}