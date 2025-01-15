package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.Medicament;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.GroupeSanguin;
import ma.CabinetDentaire.entities.enums.Mutuelle;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.repository.api.IMedicamentRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class MedicamentRepo implements IMedicamentRepo {
    private static final File MEDICAMENT_TABLE = new File("src/main/data/medicaments.txt");

    private Medicament mapToMedicament(String fileLine) throws DaoException {
        try{
            //ID|PRIX|NOM|DESCRIPTION

            StringTokenizer st = new StringTokenizer(fileLine, "\\|");
            String value = st.nextToken();
            Long id = Long.parseLong(value);
            value = st.nextToken();
            double prix = Double.parseDouble(value);
            value = st.nextToken();
            String nom = (value.equals("null")? null : value);
            value = st.nextToken();
            String description = (value.equals("null")? null : value);
            value = st.nextToken();
            String imgSrc = (value.equals("null")? null : value);

            return new Medicament(id, prix, nom, description,imgSrc);
        } catch (NumberFormatException e){
            throw new DaoException(e);
        }
    }

    private String mapToLine(Medicament medicament) throws DaoException {
        //ID|PRIX|NOM|DESCRIPTION|image

        Long id = medicament.getIdMedicament();
        double prix = medicament.getPrix();
        String nom = medicament.getNom();
        String description = medicament.getDescription();
        String imgSrc = medicament.getIamgeSrc();

        return id + "|" + prix + "|" +
                (nom    == null ? "null": nom)              + "|" +
                (description == null ? "null" : description)+"|" +
                (imgSrc == null ? "null" : imgSrc)+"|" +
                System.lineSeparator();
    }


    @Override
    public List<Medicament> findAll() throws DaoException {
        try {
            List<String> lines = Files.readAllLines(MEDICAMENT_TABLE.toPath());
            return lines.stream().skip(1).map(line -> {
                try {
                    return mapToMedicament(line);
                } catch (DaoException e) {
                    throw new RuntimeException("Donnee patient errone dans la base de donnee!!");
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new DaoException("Probleme dans la lectures des donnees patients!");
        }
    }

    @Override
    public Medicament findById(Long id) throws DaoException {
        return findAll().
                stream().
                filter(medicament -> medicament.getIdMedicament().equals(id)).
                findFirst().
                orElseThrow(()  -> new DaoException("Medicament not found"));
    }

    @Override
    public Medicament save(Medicament element) throws DaoException {
        try {
            Long maxId = findAll().stream().mapToLong(Medicament::getIdMedicament).max().orElse(0L);
            element.setIdMedicament(++maxId);
            Files.writeString(
                    MEDICAMENT_TABLE.toPath(),
                    mapToLine(element),
                    StandardOpenOption.APPEND
            );
            return element;
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Medicament element) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(MEDICAMENT_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(MEDICAMENT_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));)
        {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if(currentID.equals(element.getIdMedicament().toString())) {
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

        if(!MEDICAMENT_TABLE.delete())
            throw new DaoException("Probleme tech dans la modification du patient");
        if(!tempFile.renameTo(MEDICAMENT_TABLE))
            throw new DaoException("Probleme tech dans la modification du patient");
        if(!isUpdated)
            throw new DaoException("Patient n'" + element.getIdMedicament() + " ");
    }

    @Override
    public void delete(Medicament element) throws DaoException {

    }

    @Override
    public void deleteById(Long id) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(MEDICAMENT_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(MEDICAMENT_TABLE));
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

        if(!MEDICAMENT_TABLE.delete())
            throw new DaoException("Probleme tech dans la suppression du patient");
        if(!tempFile.renameTo(MEDICAMENT_TABLE))
            throw new DaoException("Probleme tech dans la suppression du patient");
        if(!isDeleted)
            throw new DaoException("Patient n'" + id + " ");
    }

    public static void main(String[] args) throws DaoException {
        new MedicamentRepo().findAll().forEach(medicament -> System.out.println(medicament));
    }
}
