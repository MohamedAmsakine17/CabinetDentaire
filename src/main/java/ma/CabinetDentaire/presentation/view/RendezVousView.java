package ma.CabinetDentaire.presentation.view;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Caisse;
import ma.CabinetDentaire.entities.Facture;
import ma.CabinetDentaire.entities.RendezVous;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.palette.panels.dashboard_panels.HeaderCard;
import ma.CabinetDentaire.presentation.view.palette.panels.patient_panels.PatientFooter;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class RendezVousView extends JPanel {
    private Theme currentTheme;

    private void _init() throws DaoException {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        MyLabel title = new MyLabel(currentTheme, "La Rendez-vous", 24, 1);
        add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        // Header Panel
        JPanel headerBodyPanel = new JPanel(new GridLayout(1, 4, 50, 0));
        headerBodyPanel.setOpaque(false);

        PatientFooter nbrDePatientsHomme = new PatientFooter(currentTheme, "Nbr des rendez-vous", currentTheme.greenColor(), AppFactory.getRendezVousRepo().findAll().size());
        headerBodyPanel.add(nbrDePatientsHomme);

        // Empty Panels for Layout
        for (int i = 0; i < 3; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setOpaque(false);
            headerBodyPanel.add(emptyPanel);
        }

        bodyPanel.add(headerBodyPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainBodyContent = new JPanel(new GridLayout(0, 1, 0, 10)); // Dynamic rows
        mainBodyContent.setBorder(new EmptyBorder(50, 0, 0, 0));
        mainBodyContent.setOpaque(false);

        // Table Header
//        JPanel headerTablePanel = new JPanel(new GridLayout(1, 5));
//        headerTablePanel.setBackground(currentTheme.greenColor());
//        headerTablePanel.add(new Label());
//        headerTablePanel.add(new MyLabel(currentTheme, "Date", 18, 1));
//        headerTablePanel.add(new MyLabel(currentTheme, "Temp", 18, 1));
//        headerTablePanel.add(new MyLabel(currentTheme, "Type", 18, 1));
//        headerTablePanel.add(new MyLabel(currentTheme, "Motif", 18, 1));
//        mainBodyContent.add(headerTablePanel);

        // Pagination Variables
        int pageSize = 6; // Number of items per page
        int[] currentPage = {0}; // Current page index
        List<RendezVous> allRendezVous = AppFactory.getRendezVousRepo().findAll();

        // Runnable to update the table with the current page's data
        Runnable updateTable = () -> {
            mainBodyContent.removeAll(); // Clear the current content

            // Add the header
            JPanel headerTablePanel = new JPanel(new GridLayout(1, 5));
            headerTablePanel.setBackground(currentTheme.greenColor());
            headerTablePanel.add(new Label());
            headerTablePanel.add(new MyLabel(currentTheme, "Date", 18, 1));
            headerTablePanel.add(new MyLabel(currentTheme, "Temp", 18, 1));
            headerTablePanel.add(new MyLabel(currentTheme, "Type", 18, 1));
            headerTablePanel.add(new MyLabel(currentTheme, "Motif", 18, 1));
            mainBodyContent.add(headerTablePanel);

            // Add the current page's items
            int start = currentPage[0] * pageSize;
            int end = Math.min(start + pageSize, allRendezVous.size());

            for (int i = start; i < end; i++) {
                RendezVous rendezVous = allRendezVous.get(i);
                JPanel colTablePanel = new JPanel(new GridLayout(1, 5));
                colTablePanel.setBackground(currentTheme.bgColor());
                colTablePanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()),
                        BorderFactory.createEmptyBorder(0, 0, 0, 0)
                ));
                colTablePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                MyLabel rdvIcon = new MyLabel(currentTheme, "src/main/resources/images/icons/schedule.png", 32);
                rdvIcon.setBorder(new EmptyBorder(0, 15, 0, 0));
                colTablePanel.add(rdvIcon);

                colTablePanel.add(new MyLabel(currentTheme, rendezVous.getDateRDV().toString(), 16, 0));
                colTablePanel.add(new MyLabel(currentTheme, rendezVous.getTemps().toString(), 16, 0));
                colTablePanel.add(new MyLabel(currentTheme, rendezVous.getTypeRDV().toString(), 16, 0));
                colTablePanel.add(new MyLabel(currentTheme, rendezVous.getMotif(), 16, 0));

                mainBodyContent.add(colTablePanel);
            }

            mainBodyContent.revalidate();
            mainBodyContent.repaint();
        };

        // Pagination Controls
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        paginationPanel.setOpaque(false);

        MyButton prevButton = new MyButton(currentTheme, "src/main/resources/images/left-arrow.png");
        prevButton.changeImageSize(42);
        prevButton.setOpaque(false);
        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentPage[0] > 0) {
                    currentPage[0]--;
                    updateTable.run();
                }
            }
        });

        MyButton nextButton = new MyButton(currentTheme, "src/main/resources/images/right-arrow.png");
        nextButton.changeImageSize(42);
        nextButton.setOpaque(false);
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ((currentPage[0] + 1) * pageSize < allRendezVous.size()) {
                    currentPage[0]++;
                    updateTable.run();
                }
            }
        });

        paginationPanel.add(prevButton);
        paginationPanel.add(nextButton);

        // Initial table update
        updateTable.run();

        bodyPanel.add(mainBodyContent, BorderLayout.CENTER);
        bodyPanel.add(paginationPanel, BorderLayout.SOUTH);

        add(bodyPanel, BorderLayout.CENTER);
    }

    public RendezVousView(Theme currentTheme) throws DaoException {
        this.currentTheme = currentTheme;
        _init();
    }
}
