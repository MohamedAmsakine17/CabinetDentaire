package ma.CabinetDentaire.presentation.view.palette.panels;

import ma.CabinetDentaire.presentation.view.palette.panels.links.NavLink;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class NavPanel extends JPanel {
    private Theme currentTheme;
    private JPanel rightPanel;

    public void _init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 80));
        setOpaque(false);

        // Create and add NavLinks
        NavLink patient = new NavLink(currentTheme, "Patients", "src/main/resources/images/patient_icon.png",rightPanel);
        NavLink rendezVous = new NavLink(currentTheme, "Rendez-vous", "src/main/resources/images/rendezvous.png",rightPanel);
        NavLink consultation = new NavLink(currentTheme, "Consultation", "src/main/resources/images/consultation.png",rightPanel);
        NavLink ordonnances = new NavLink(currentTheme, "Ordonnances", "src/main/resources/images/ordonnance.png",rightPanel);

        // Add NavLinks to the NavPanel
        add(patient);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(rendezVous);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(consultation);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(ordonnances);
    }

    public NavPanel(Theme currentTheme, JPanel rightPanel) {
        this.currentTheme = currentTheme;
        this.rightPanel = rightPanel;
        _init();
    }
}
