package ma.CabinetDentaire.presentation.view.palette.panels.links;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavLink extends JPanel {
    private Theme currentTheme;
    private String text, iconSrc;
    private JPanel rightPanel;

    public void _init() {
        setLayout(new GridLayout(1,2,-80,0));
        setOpaque(false);

        ImageIcon icon = new ImageIcon(iconSrc);
        Image img = icon.getImage().getScaledInstance(32,32, Image.SCALE_SMOOTH);

        JLabel iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(32, 32));
        iconLabel.setOpaque(false);
        iconLabel.setIcon(new ImageIcon(img));

        add(iconLabel);

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));

        add(textLabel);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                textLabel.setForeground(currentTheme.greenColor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                textLabel.setForeground(currentTheme.fontColor());
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                textLabel.setForeground(currentTheme.greenColor());

                // Call a method in MainView to update the right panel content
                if (text.equals("Patients")) {
                    updateRightPanel(createPatientPanel());
                } else if (text.equals("Rendez-vous")) {
                    updateRightPanel(createRendezVousPanel());
                } else if (text.equals("Consultation")) {
                    updateRightPanel(createConsultationPanel());
                } else if (text.equals("Ordonnances")) {
                    updateRightPanel(createOrdonnancesPanel());
                }
            }
        });
    }

    private void updateRightPanel(JPanel newPanel) {
        rightPanel.removeAll();  // Clear the existing content
        rightPanel.add(newPanel);  // Add the new content
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    // Methods to create different panels for each section
    private JPanel createPatientPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Patient Panel"));
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createRendezVousPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Rendez-vous Panel"));
        panel.setOpaque(false);

        return panel;
    }

    private JPanel createConsultationPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Consultation Panel"));
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createOrdonnancesPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Ordonnances Panel"));
        panel.setOpaque(false);

        return panel;
    }

    public NavLink(Theme currentTheme,String text, String iconSrc, JPanel rightPanel) {
        this.currentTheme = currentTheme;
        this.text = text;
        this.iconSrc = iconSrc;
        this.rightPanel = rightPanel;  // Pass rightPanel reference to NavLink

        _init();
    }
}
