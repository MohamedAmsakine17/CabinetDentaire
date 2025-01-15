package ma.CabinetDentaire.presentation.view;

import ma.CabinetDentaire.presentation.view.palette.links.NavLink;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavPanel extends JPanel {
    private JFrame currentFrame;
    private Theme currentTheme;
    private JPanel rightPanel;

    public void _init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 80));
        setOpaque(false);

        // Create and add NavLinks
        NavLink dashboard = new NavLink(currentTheme, "Dashboard", "src/main/resources/images/dashboard.png");
        NavLink patient = new NavLink(currentTheme, "Patients", "src/main/resources/images/patient_icon.png");
        NavLink medicament = new NavLink(currentTheme, "Medicament", "src/main/resources/images/medicament.png");
        NavLink rendezVous = new NavLink(currentTheme, "Rendez-vous", "src/main/resources/images/rendezvous.png");
        NavLink consultation = new NavLink(currentTheme, "Consultation", "src/main/resources/images/consultation.png");
        NavLink ordonnances = new NavLink(currentTheme, "Ordonnances", "src/main/resources/images/ordonnance.png");
        NavLink deconnexion = new NavLink(currentTheme, "Deconnexion", "src/main/resources/images/logout.png");

        dashboard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                updateRightPanel();
                rightPanel.add(new DashboardView(currentTheme));
            }
        });

        patient.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                updateRightPanel();
                rightPanel.add(AppFactory.getPatientController().showAllPatients());
            }
        });

        medicament.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                updateRightPanel();
                rightPanel.add(AppFactory.getMedicamentController().showAllMedicaments());
            }
        });

        deconnexion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFrame.dispose();
                try {
                    AppFactory.getUtilisateurDAO().deleteSession();
                } catch (DaoException ex) {
                    throw new RuntimeException(ex);
                }
                new LoginView(currentTheme);
            }
        });

        // Add NavLinks to the NavPanel
        add(dashboard);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(patient);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(medicament);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(rendezVous);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(consultation);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(ordonnances);
        add(Box.createRigidArea(new Dimension(0, 125)));
        add(deconnexion);
    }

    public NavPanel(Theme currentTheme, JPanel rightPanel, JFrame currentFrame) {
        this.currentTheme = currentTheme;
        this.rightPanel = rightPanel;
        this.currentFrame = currentFrame;
        _init();
    }

        private void updateRightPanel() {
        rightPanel.removeAll();
        rightPanel.revalidate();
        rightPanel.repaint();
    }
}
