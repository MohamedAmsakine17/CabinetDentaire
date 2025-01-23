package ma.CabinetDentaire.presentation.view;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.palette.panels.dashboard_panels.DashboardHeader;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardView extends JPanel {
    private Theme currentTheme;

    private void _init() throws DaoException {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        MyLabel title = new MyLabel(currentTheme,"Dashboard",24,1);
        add(title, BorderLayout.NORTH);

        JPanel bodyContent = new JPanel(new BorderLayout());
        bodyContent.setBorder(new EmptyBorder(50, 0, 0, 0));
        bodyContent.setOpaque(false);
        DashboardHeader header = new DashboardHeader(currentTheme);

        JPanel mainBodyContent = new JPanel(new GridLayout(4,1,0,10));
        mainBodyContent.setBorder(new EmptyBorder(50, 0, 0, 0));
        mainBodyContent.setOpaque(false);

        JPanel headerTablePanel = new JPanel(new GridLayout(1,5));
        headerTablePanel.setBackground(currentTheme.greenColor());
        // Header Content ----------------------------------
        headerTablePanel.add(new Label());
        MyLabel dateColLabel = new MyLabel(currentTheme,"Date",18,1);
        headerTablePanel.add(dateColLabel);
        MyLabel tempColLabel = new MyLabel(currentTheme,"Temp",18,1);
        headerTablePanel.add(tempColLabel);
        MyLabel typeColLabel = new MyLabel(currentTheme,"Type",18,1);
        headerTablePanel.add(typeColLabel);
        MyLabel motifColLabel = new MyLabel(currentTheme,"Motif",18,1);
        headerTablePanel.add(motifColLabel);

        // -------------------------------------------------
        mainBodyContent.add(headerTablePanel);
        // Col Content ----------------------------------
        for (int i = 0; i < getUpcomingRendezVous(AppFactory.getRendezVousRepo().findAll()).size(); i++){
            RendezVous rendezVous = getUpcomingRendezVous(AppFactory.getRendezVousRepo().findAll()).get(i);
            JPanel colTablePanel = new JPanel(new GridLayout(1,5));
            colTablePanel.setBackground(currentTheme.bgColor());
            colTablePanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()), // Green bottom border
                    BorderFactory.createEmptyBorder(0, 0, 0, 0) // Padding
            ));
            colTablePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            MyLabel rdv_icon = new MyLabel(currentTheme,"src/main/resources/images/icons/schedule.png",32);
            rdv_icon.setBorder(new EmptyBorder(0, 15, 0, 0));
            colTablePanel.add(rdv_icon);
            MyLabel rdv_dateColLabel = new MyLabel(currentTheme,rendezVous.getDateRDV().toString(),16,0);
            colTablePanel.add(rdv_dateColLabel);
            MyLabel rdv_tempColLabel = new MyLabel(currentTheme,rendezVous.getTemps().toString(),16,0);
            colTablePanel.add(rdv_tempColLabel);
            MyLabel rdv_typeColLabel = new MyLabel(currentTheme,rendezVous.getTypeRDV().toString(),16,0);
            colTablePanel.add(rdv_typeColLabel);
            MyLabel rdv_motifColLabel = new MyLabel(currentTheme,rendezVous.getMotif(),16,0);
            colTablePanel.add(rdv_motifColLabel);
            mainBodyContent.add(colTablePanel);
        }

        bodyContent.add(header, BorderLayout.NORTH);
        bodyContent.add(mainBodyContent, BorderLayout.CENTER);
        add(bodyContent, BorderLayout.CENTER);
    }

    public DashboardView(Theme currentTheme) throws DaoException {
        this.currentTheme = currentTheme;
        _init();
    }


    public List<RendezVous> getUpcomingRendezVous(List<RendezVous> rendezVousList) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        return rendezVousList.stream()
                .filter(rdv -> rdv.getDateRDV().isAfter(today) ||
                        (rdv.getDateRDV().isEqual(today) && rdv.getTemps().isAfter(now))) // Filter out past appointments
                .sorted(Comparator.comparing(RendezVous::getDateRDV)
                        .thenComparing(RendezVous::getTemps)) // Sort by date and time
                .limit(3) // Get the first 5 upcoming appointments
                .collect(Collectors.toList());
    }
}


