package ma.CabinetDentaire.repository.fileDB_impl;

import ma.CabinetDentaire.entities.Consultation;
import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.entities.enums.Mutuelle;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.entities.enums.TypeRDV;
import ma.CabinetDentaire.repository.api.IRendezVousRepo;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class RendezVousRepo implements IRendezVousRepo {
    private static final File RENDEZ_VOUS_TABLE = new File("src/main/data/rendezvous.txt");

    private static DossierMedicalRepo dossierMedicalRepo;
    private static ConsultationRepo consultationRepo;

    public void setDossierMedicalRepo(DossierMedicalRepo dossierMedicalRepo) {
        this.dossierMedicalRepo = dossierMedicalRepo;
    }

    public void setConsultationRepo(ConsultationRepo consultationRepo) {
        this.consultationRepo = consultationRepo;
    }

    private RendezVous mapToRendezVous(String fileLine) throws DaoException {
        try {
            // ID|MOTIF|TEMPS|DOSSIER_ID|CONSULTATION_ID|TYPE_RDV|DATE_RDV
            StringTokenizer st = new StringTokenizer(fileLine, "\\|");

            String value = st.nextToken();
            Long id = Long.parseLong(value);
            value = st.nextToken();
            String motif = (value.equals("null") ? null : value);
            value = st.nextToken();
            LocalTime temps = LocalTime.parse(value);
            value = st.nextToken();
            Long dossierId = Long.parseLong(value);
            DossierMedicale dossier = dossierMedicalRepo.findById(dossierId);
            value = st.nextToken();
            Long consultationId = (value.equals("null") ? null : Long.parseLong(value));
            Consultation consultation = value.equals("null") ? null : consultationRepo.findById(consultationId);
            value = st.nextToken();
            TypeRDV typeRDV = (value.equals("null") ? null :
                    (value.equals("PRIORITAIRE") ? TypeRDV.PRIORITAIRE
                            : value.equals("NORMAL") ? TypeRDV.NORMAL
                            : value.equals("VIP") ? TypeRDV.VIP
                            : value.equals("URGENT") ? TypeRDV.URGENT
                            : value.equals("POUR_CONSEIL") ? TypeRDV.POUR_CONSEIL
                            : TypeRDV.CONTROLE));
            value = st.nextToken();
            LocalDate dateRDV = LocalDate.parse(value);

            return new RendezVous(id, motif, temps, dossier, consultation, typeRDV, dateRDV);
        } catch (Exception e) {
            throw new DaoException("Error mapping RendezVous: " + e.getMessage());
        }
    }

    private String mapToLine(RendezVous rendezVous) {
        // ID|MOTIF|TEMPS|DOSSIER_ID|CONSULTATION_ID|TYPE_RDV|DATE_RDV
        return rendezVous.getId() + "|" +
                (rendezVous.getMotif() == null ? "null" : rendezVous.getMotif()) + "|" +
                rendezVous.getTemps() + "|" +
                rendezVous.getDossier().getId() + "|" + // Store DossierMedicale ID
                (rendezVous.getConsultation() == null ? "null" : rendezVous.getConsultation().getIdConsultation()) + "|" +
                (rendezVous.getTypeRDV() == null ? "null"
                        : (rendezVous.getTypeRDV().equals(TypeRDV.PRIORITAIRE) ? "PRIORITAIRE"
                        : rendezVous.getTypeRDV().equals(TypeRDV.NORMAL) ? "NORMAL"
                        : rendezVous.getTypeRDV().equals(TypeRDV.VIP) ? "VIP"
                        : rendezVous.getTypeRDV().equals(TypeRDV.URGENT) ? "URGENT"
                        : rendezVous.getTypeRDV().equals(TypeRDV.POUR_CONSEIL) ? "POUR_CONSEIL" : "CONTROLE")) + "|" +
                rendezVous.getDateRDV() +
                System.lineSeparator();
    }

    @Override
    public List<RendezVous> findAll() throws DaoException {
        try {
            List<String> lines = Files.readAllLines(RENDEZ_VOUS_TABLE.toPath());
            return lines.stream().skip(1).map(line -> {
                try {
                    return mapToRendezVous(line);
                } catch (DaoException e) {
                    throw new RuntimeException("Error in RendezVous data: " + e.getMessage());
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new DaoException("Error reading RendezVous data: " + e.getMessage());
        }
    }

    @Override
    public RendezVous findById(Long id) throws DaoException {
        return findAll()
                .stream()
                .filter(rendezVous -> rendezVous.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new DaoException("RendezVous not found with ID: " + id));
    }

    @Override
    public RendezVous save(RendezVous element) throws DaoException {
        try {
            Long maxId = findAll().stream().mapToLong(RendezVous::getId).max().orElse(0L);
            element.setId(maxId + 1);
            Files.writeString(
                    RENDEZ_VOUS_TABLE.toPath(),
                    mapToLine(element),
                    StandardOpenOption.APPEND
            );
            return element;
        } catch (IOException e) {
            throw new DaoException("Error saving RendezVous: " + e.getMessage());
        }
    }

    @Override
    public void update(RendezVous element) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(RENDEZ_VOUS_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(RENDEZ_VOUS_TABLE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentID = line.split("\\|")[0];
                if (currentID.equals(element.getId().toString())) {
                    writer.write(mapToLine(element));
                    isUpdated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new DaoException("Error updating RendezVous: " + e.getMessage());
        }

        if (!RENDEZ_VOUS_TABLE.delete())
            throw new DaoException("Error deleting original RendezVous file");
        if (!tempFile.renameTo(RENDEZ_VOUS_TABLE))
            throw new DaoException("Error renaming temporary RendezVous file");
        if (!isUpdated)
            throw new DaoException("RendezVous with ID " + element.getId() + " not found");
    }

    @Override
    public void delete(RendezVous element) throws DaoException {
        deleteById(element.getId());
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(RENDEZ_VOUS_TABLE.getAbsolutePath() + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(RENDEZ_VOUS_TABLE));
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
            throw new DaoException("Error deleting RendezVous: " + e.getMessage());
        }

        if (!RENDEZ_VOUS_TABLE.delete())
            throw new DaoException("Error deleting original RendezVous file");
        if (!tempFile.renameTo(RENDEZ_VOUS_TABLE))
            throw new DaoException("Error renaming temporary RendezVous file");
        if (!isDeleted)
            throw new DaoException("RendezVous with ID " + id + " not found");
    }

    @Override
    public List<RendezVous> findByDossier(DossierMedicale dossier) throws DaoException {
        return findAll()
                .stream()
                .filter(rendezVous -> rendezVous.getDossier().getId().equals(dossier.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RendezVous> findByDate(LocalDate date) throws DaoException {
        return findAll()
                .stream()
                .filter(rendezVous -> rendezVous.getDateRDV().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public RendezVous findByConsultation(Consultation consultation) throws DaoException {
        return findAll()
                .stream()
                .filter(rendezVous -> rendezVous.getConsultation().getIdConsultation().equals(consultation.getIdConsultation()))
                .findFirst()
                .orElseThrow(() -> new DaoException("RendezVous not found with Consultation ID: " + consultation.getIdConsultation()));
    }

}
