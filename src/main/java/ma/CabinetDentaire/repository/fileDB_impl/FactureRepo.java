package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.App;
import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.Facture;
import ma.CabinetDentaire.entities.SituationFinanciere;
import ma.CabinetDentaire.entities.enums.TypePaiement;
import ma.CabinetDentaire.repository.api.IFactureRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class FactureRepo implements IFactureRepo {
    private static final File FACTURE_TABLE = new File("src/main/data/factures.txt");

    private Facture mapToFacture(String fileLine) throws DaoException {
        try {
            StringTokenizer st = new StringTokenizer(fileLine, "\\|");

            Long id = Long.parseLong(st.nextToken());
            Double montantTotal = Double.parseDouble(st.nextToken());
            Double montantRestant = Double.parseDouble(st.nextToken());
            Double montantPaye = Double.parseDouble(st.nextToken());
            LocalDate dateFacturation = LocalDate.parse(st.nextToken());
            Long situationFinanciereId = Long.parseLong(st.nextToken()); // Save as ID
            SituationFinanciere situationFinanciere = AppFactory.getSituationRepo().findById(situationFinanciereId);

            TypePaiement typePaiement = TypePaiement.valueOf(st.nextToken()); // Save as enum

            Long consultationId = Long.parseLong(st.nextToken());
            Consultation consultation = AppFactory.getConsultationRepo().findById(consultationId);

            return new Facture(id, montantTotal, montantRestant, montantPaye, dateFacturation, situationFinanciere, typePaiement, consultation);
        } catch (Exception e) {
            throw new DaoException("Error mapping Facture: " + e.getMessage());
        }
    }

    private String mapToLine(Facture facture) {
        return facture.getId() + "|" +
                facture.getMontantTotal() + "|" +
                facture.getMontantRestant() + "|" +
                facture.getMontantPaye() + "|" +
                facture.getDataFacturation() + "|" +
                facture.getSituationFinanciere().getId() + "|" +
                facture.getTypePaiement().name() + "|" +
                facture.getConsultation().getIdConsultation() +
                System.lineSeparator();
    }

    @Override
    public List<Facture> findAll() throws DaoException {
        try {
            List<String> lines = Files.readAllLines(FACTURE_TABLE.toPath());
            return lines.stream().skip(1).map(line -> {
                try {
                    return mapToFacture(line);
                } catch (DaoException e) {
                    throw new RuntimeException("Error in Facture data: " + e.getMessage());
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new DaoException("Error reading Facture data: " + e.getMessage());
        }
    }

    @Override
    public Facture findById(Long id) throws DaoException {
        return findAll()
                .stream()
                .filter(facture -> facture.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new DaoException("Facture not found with ID: " + id));
    }

    @Override
    public Facture save(Facture facture) throws DaoException {
        try {
            Long maxId = findAll().stream().mapToLong(Facture::getId).max().orElse(0L);
            facture.setId(maxId + 1);
            Files.writeString(
                    FACTURE_TABLE.toPath(),
                    mapToLine(facture),
                    StandardOpenOption.APPEND
            );
            return facture;
        } catch (IOException e) {
            throw new DaoException("Error saving Facture: " + e.getMessage());
        }
    }

    @Override
    public void update(Facture facture) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(FACTURE_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(FACTURE_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if (currentID.equals(facture.getId().toString())) {
                    writer.write(mapToLine(facture));
                    isUpdated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new DaoException("Error updating Facture: " + e.getMessage());
        }

        if (!FACTURE_TABLE.delete())
            throw new DaoException("Error deleting original Facture file");
        if (!tempFile.renameTo(FACTURE_TABLE))
            throw new DaoException("Error renaming temporary Facture file");
        if (!isUpdated)
            throw new DaoException("Facture with ID " + facture.getId() + " not found");
    }

    @Override
    public void delete(Facture facture) throws DaoException {
        deleteById(facture.getId());
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(FACTURE_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(FACTURE_TABLE));
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
            throw new DaoException("Error deleting Facture: " + e.getMessage());
        }

        if (!FACTURE_TABLE.delete())
            throw new DaoException("Error deleting original Facture file");
        if (!tempFile.renameTo(FACTURE_TABLE))
            throw new DaoException("Error renaming temporary Facture file");
        if (!isDeleted)
            throw new DaoException("Facture with ID " + id + " not found");
    }

    @Override
    public List<Facture> findByConsultation(Long consultationId) throws DaoException {
        return findAll()
                .stream()
                .filter(facture -> facture.getConsultation().getIdConsultation().equals(consultationId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Facture> findBySituationFinancie(Long id) throws DaoException {
        return findAll()
                .stream()
                .filter(facture -> facture.getSituationFinanciere().getId().equals(id))
                .collect(Collectors.toList());
    }
}