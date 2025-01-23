package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.SituationFinanciere;
import ma.CabinetDentaire.repository.api.ISituationFinanciereRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class SituationFinanciereRepo implements ISituationFinanciereRepo {
    private static final File SITUATION_FINANCIERE_TABLE = new File("src/main/data/situation_financiere.txt");

    private SituationFinanciere mapToSituationFinanciere(String fileLine) throws DaoException {
        try {
            StringTokenizer st = new StringTokenizer(fileLine, "\\|");

            Long id = Long.parseLong(st.nextToken());
            Double montantGlobalePaye = Double.parseDouble(st.nextToken());
            Double montantGlobaleRestant = Double.parseDouble(st.nextToken());
            LocalDate dateCreation = LocalDate.parse(st.nextToken());
            Long dossierId = Long.parseLong(st.nextToken());

            DossierMedicale dossierMedicale = new DossierMedicale();
            dossierMedicale.setId(dossierId);

            return new SituationFinanciere(id, montantGlobalePaye, montantGlobaleRestant, dateCreation, dossierMedicale);
        } catch (Exception e) {
            throw new DaoException("Error mapping SituationFinanciere: " + e.getMessage());
        }
    }

    private String mapToLine(SituationFinanciere situationFinanciere) {
        return situationFinanciere.getId() + "|" +
                situationFinanciere.getMontantGlobalePaye() + "|" +
                situationFinanciere.getMontantGlobaleRestant() + "|" +
                situationFinanciere.getDateCreation() + "|" +
                situationFinanciere.getDossierMedicale().getId() +
                System.lineSeparator();
    }

    @Override
    public List<SituationFinanciere> findAll() throws DaoException {
        try {
            List<String> lines = Files.readAllLines(SITUATION_FINANCIERE_TABLE.toPath());
            return lines.stream().skip(1).map(line -> {
                try {
                    return mapToSituationFinanciere(line);
                } catch (DaoException e) {
                    throw new RuntimeException("Error in SituationFinanciere data: " + e.getMessage());
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new DaoException("Error reading SituationFinanciere data: " + e.getMessage());
        }
    }

    @Override
    public SituationFinanciere findById(Long id) throws DaoException {
        return findAll()
                .stream()
                .filter(situation -> situation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new DaoException("SituationFinanciere not found with ID: " + id));
    }

    @Override
    public SituationFinanciere save(SituationFinanciere situationFinanciere) throws DaoException {
        try {
            Long maxId = findAll().stream().mapToLong(SituationFinanciere::getId).max().orElse(0L);
            situationFinanciere.setId(maxId + 1);
            Files.writeString(
                    SITUATION_FINANCIERE_TABLE.toPath(),
                    mapToLine(situationFinanciere),
                    StandardOpenOption.APPEND
            );
            return situationFinanciere;
        } catch (IOException e) {
            throw new DaoException("Error saving SituationFinanciere: " + e.getMessage());
        }
    }

    @Override
    public void update(SituationFinanciere situationFinanciere) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(SITUATION_FINANCIERE_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(SITUATION_FINANCIERE_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if (currentID.equals(situationFinanciere.getId().toString())) {
                    writer.write(mapToLine(situationFinanciere));
                    isUpdated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new DaoException("Error updating SituationFinanciere: " + e.getMessage());
        }

        if (!SITUATION_FINANCIERE_TABLE.delete())
            throw new DaoException("Error deleting original SituationFinanciere file");
        if (!tempFile.renameTo(SITUATION_FINANCIERE_TABLE))
            throw new DaoException("Error renaming temporary SituationFinanciere file");
        if (!isUpdated)
            throw new DaoException("SituationFinanciere with ID " + situationFinanciere.getId() + " not found");
    }

    @Override
    public void delete(SituationFinanciere situationFinanciere) throws DaoException {
        deleteById(situationFinanciere.getId());
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(SITUATION_FINANCIERE_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(SITUATION_FINANCIERE_TABLE));
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
            throw new DaoException("Error deleting SituationFinanciere: " + e.getMessage());
        }

        if (!SITUATION_FINANCIERE_TABLE.delete())
            throw new DaoException("Error deleting original SituationFinanciere file");
        if (!tempFile.renameTo(SITUATION_FINANCIERE_TABLE))
            throw new DaoException("Error renaming temporary SituationFinanciere file");
        if (!isDeleted)
            throw new DaoException("SituationFinanciere with ID " + id + " not found");
    }

    @Override
    public SituationFinanciere findByDossierMedicale(Long dossierId) throws DaoException {
        return findAll()
                .stream()
                .filter(situation -> situation.getDossierMedicale().getId().equals(dossierId))
                .findFirst()
                .orElseThrow(() -> new DaoException("SituationFinanciere not found for DossierMedicale ID: " + dossierId));
    }
}