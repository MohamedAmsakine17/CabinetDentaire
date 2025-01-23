package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.entities.enums.CategorieActe;
import ma.CabinetDentaire.repository.api.IActeRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class ActeRepo implements IActeRepo {
    private static final File ACTE_TABLE = new File("src/main/data/actes.txt");

    private Acte mapToActe(String fileLine) throws DaoException {
        try {
            // ID|LIBELLE|PRIX_DE_BASE|CATEGORIE
            StringTokenizer st = new StringTokenizer(fileLine, "\\|");

            Long id = Long.parseLong(st.nextToken());
            String libelle = st.nextToken();
            Double prixDeBase = Double.parseDouble(st.nextToken());
            CategorieActe categorie = CategorieActe.valueOf(st.nextToken());

            return new Acte(id, libelle, prixDeBase, categorie);
        } catch (Exception e) {
            throw new DaoException("Error mapping Acte: " + e.getMessage());
        }
    }

    private String mapToLine(Acte acte) {
        // ID|LIBELLE|PRIX_DE_BASE|CATEGORIE
        return acte.getId() + "|" +
                acte.getLibelle() + "|" +
                acte.getPrixDeBase() + "|" +
                acte.getCategorieActe().name() +
                System.lineSeparator();
    }

    @Override
    public List<Acte> findAll() throws DaoException {
        try {
            List<String> lines = Files.readAllLines(ACTE_TABLE.toPath());
            return lines.stream().skip(1).map(line -> {
                try {
                    return mapToActe(line);
                } catch (DaoException e) {
                    throw new RuntimeException("Error in Acte data: " + e.getMessage());
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new DaoException("Error reading Acte data: " + e.getMessage());
        }
    }

    @Override
    public Acte findById(Long id) throws DaoException {
        return findAll()
                .stream()
                .filter(acte -> acte.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new DaoException("Acte not found with ID: " + id));
    }

    @Override
    public Acte findByLibelle(String libelle) throws DaoException {
        return findAll()
                .stream()
                .filter(acte -> acte.getLibelle().equals(libelle))
                .findFirst()
                .orElseThrow(() -> new DaoException("Acte not found with liblle: " + libelle));
    }

    @Override
    public Acte save(Acte acte) throws DaoException {
        try {
            Long maxId = findAll().stream().mapToLong(Acte::getId).max().orElse(0L);
            acte.setId(maxId + 1);
            Files.writeString(
                    ACTE_TABLE.toPath(),
                    mapToLine(acte),
                    StandardOpenOption.APPEND
            );
            return acte;
        } catch (IOException e) {
            throw new DaoException("Error saving Acte: " + e.getMessage());
        }
    }

    @Override
    public void update(Acte acte) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(ACTE_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(ACTE_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if (currentID.equals(acte.getId().toString())) {
                    writer.write(mapToLine(acte));
                    isUpdated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new DaoException("Error updating Acte: " + e.getMessage());
        }

        if (!ACTE_TABLE.delete())
            throw new DaoException("Error deleting original Acte file");
        if (!tempFile.renameTo(ACTE_TABLE))
            throw new DaoException("Error renaming temporary Acte file");
        if (!isUpdated)
            throw new DaoException("Acte with ID " + acte.getId() + " not found");
    }

    @Override
    public void delete(Acte acte) throws DaoException {
        deleteById(acte.getId());
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(ACTE_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(ACTE_TABLE));
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
            throw new DaoException("Error deleting Acte: " + e.getMessage());
        }

        if (!ACTE_TABLE.delete())
            throw new DaoException("Error deleting original Acte file");
        if (!tempFile.renameTo(ACTE_TABLE))
            throw new DaoException("Error renaming temporary Acte file");
        if (!isDeleted)
            throw new DaoException("Acte with ID " + id + " not found");
    }
}