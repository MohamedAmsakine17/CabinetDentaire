package ma.CabinetDentaire.presentation.controller.api;

import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.entities.enums.TypeRDV;
import ma.CabinetDentaire.presentation.view.RendezVousView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IRendezVousController {
    RendezVousView showAllRendezVous();
    List<RendezVous> filterByTypeRDV(TypeRDV typeRDV);
    List<RendezVous> filterByDate(LocalDate date);
    List<RendezVous> filterByDossier(Long dossierId);

    void createRendezVous(String motif, String temps, Long dossierId, String typeRDV, String dateRDV);

    void updateRendezVous(Long id, String motif, String temps, Long dossierId, String typeRDV, String dateRDV);
    void updateRendezVous(RendezVous rendezVous);
    void deleteRendezVousById(Long id, Long dossierId);


}
