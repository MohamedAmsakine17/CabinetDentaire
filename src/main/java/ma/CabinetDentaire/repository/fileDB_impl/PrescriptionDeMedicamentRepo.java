package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.Medicament;
import ma.CabinetDentaire.entities.Ordonnance;
import ma.CabinetDentaire.entities.PrescriptionDeMedicament;
import ma.CabinetDentaire.repository.api.IPrescriptionDeMedicamentRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class PrescriptionDeMedicamentRepo implements IPrescriptionDeMedicamentRepo {
    private static final File PRESCRIPTION_TABLE = new File("src/main/data/prescriptions.txt");
    private static OrdonnanceRepo ordonnanceRepo;
    private static MedicamentRepo medicamentRepo;

    public void setOrdonnanceRepo(OrdonnanceRepo ordonnanceRepo) {
        this.ordonnanceRepo = ordonnanceRepo;
    }

    public void setMedicamentRepo(MedicamentRepo medicamentRepo) {
        this.medicamentRepo = medicamentRepo;
    }

    private PrescriptionDeMedicament mapToPrescription(String fileLine) throws DaoException {
        try {
            // ID|UNITES_MIN|UNITES_MAX|CONTRAINTES_ALIMENTATION|ORDONNANCE_ID|CONTRAINTES_TEMPS|MEDICAMENT_ID
            StringTokenizer st = new StringTokenizer(fileLine, "\\|");

            String value = st.nextToken();
            Long id = Long.parseLong(value);

            value = st.nextToken();
            int unitesMin = Integer.parseInt(value);

            value = st.nextToken();
            int unitesMax = Integer.parseInt(value);

            value = st.nextToken();
            String contraintesAlimentation = (value.equals("null") ? null : value);

            value = st.nextToken();
            Long ordonnanceId = Long.parseLong(value);
            Ordonnance ordonnance = ordonnanceRepo.findById(ordonnanceId);

            value = st.nextToken();
            String contraintesTemps = (value.equals("null") ? null : value);

            value = st.nextToken();
            Long medicamentId = Long.parseLong(value);
            Medicament medicament = medicamentRepo.findById(medicamentId);

            return new PrescriptionDeMedicament(id, unitesMin, unitesMax, contraintesAlimentation, ordonnance, contraintesTemps, medicament);
        } catch (Exception e) {
            throw new DaoException("Error mapping PrescriptionDeMedicament: " + e.getMessage());
        }
    }

    private String mapToLine(PrescriptionDeMedicament prescription) {
        // ID|UNITES_MIN|UNITES_MAX|CONTRAINTES_ALIMENTATION|ORDONNANCE_ID|CONTRAINTES_TEMPS|MEDICAMENT_ID
        return prescription.getIdPrescription() + "|" +
                prescription.getUnitesMinAPrendre() + "|" +
                prescription.getUnitesMaxAPrendre() + "|" +
                (prescription.getContraintesAlimentation() == null ? "null" : prescription.getContraintesAlimentation()) + "|" +
                prescription.getOrdonnance().getIdOrdonnance() + "|" +
                (prescription.getContraintesTemps() == null ? "null" : prescription.getContraintesTemps()) + "|" +
                prescription.getMedicamentAPrescrire().getIdMedicament() +
                System.lineSeparator();
    }

    @Override
    public List<PrescriptionDeMedicament> findAll() throws DaoException {
        try {
            List<String> lines = Files.readAllLines(PRESCRIPTION_TABLE.toPath());
            return lines.stream().skip(1).map(line -> {
                try {
                    return mapToPrescription(line);
                } catch (DaoException e) {
                    throw new RuntimeException("Error in PrescriptionDeMedicament data: " + e.getMessage());
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new DaoException("Error reading PrescriptionDeMedicament data: " + e.getMessage());
        }
    }

    @Override
    public PrescriptionDeMedicament findById(Long id) throws DaoException {
        return findAll()
                .stream()
                .filter(prescription -> prescription.getIdPrescription().equals(id))
                .findFirst()
                .orElseThrow(() -> new DaoException("PrescriptionDeMedicament not found with ID: " + id));
    }

    @Override
    public PrescriptionDeMedicament save(PrescriptionDeMedicament prescription) throws DaoException {
        try {
            Long maxId = findAll().stream().mapToLong(PrescriptionDeMedicament::getIdPrescription).max().orElse(0L);
            prescription.setIdPrescription(maxId + 1);
            Files.writeString(
                    PRESCRIPTION_TABLE.toPath(),
                    mapToLine(prescription),
                    StandardOpenOption.APPEND
            );
            return prescription;
        } catch (IOException e) {
            throw new DaoException("Error saving PrescriptionDeMedicament: " + e.getMessage());
        }
    }

    @Override
    public void update(PrescriptionDeMedicament prescription) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(PRESCRIPTION_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(PRESCRIPTION_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if (currentID.equals(prescription.getIdPrescription().toString())) {
                    writer.write(mapToLine(prescription));
                    isUpdated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new DaoException("Error updating PrescriptionDeMedicament: " + e.getMessage());
        }

        if (!PRESCRIPTION_TABLE.delete())
            throw new DaoException("Error deleting original PrescriptionDeMedicament file");
        if (!tempFile.renameTo(PRESCRIPTION_TABLE))
            throw new DaoException("Error renaming temporary PrescriptionDeMedicament file");
        if (!isUpdated)
            throw new DaoException("PrescriptionDeMedicament with ID " + prescription.getIdPrescription() + " not found");
    }

    @Override
    public void delete(PrescriptionDeMedicament prescription) throws DaoException {
        deleteById(prescription.getIdPrescription());
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(PRESCRIPTION_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(PRESCRIPTION_TABLE));
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
            throw new DaoException("Error deleting PrescriptionDeMedicament: " + e.getMessage());
        }

        if (!PRESCRIPTION_TABLE.delete())
            throw new DaoException("Error deleting original PrescriptionDeMedicament file");
        if (!tempFile.renameTo(PRESCRIPTION_TABLE))
            throw new DaoException("Error renaming temporary PrescriptionDeMedicament file");
        if (!isDeleted)
            throw new DaoException("PrescriptionDeMedicament with ID " + id + " not found");
    }

    @Override
    public List<PrescriptionDeMedicament> findByOrdonnance(Long ordonnanceId) throws DaoException {
        return findAll()
                .stream()
                .filter(prescription -> prescription.getOrdonnance().getIdOrdonnance().equals(ordonnanceId))
                .collect(Collectors.toList());
    }
}