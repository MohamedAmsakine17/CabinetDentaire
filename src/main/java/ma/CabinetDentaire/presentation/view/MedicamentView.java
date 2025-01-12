package ma.CabinetDentaire.presentation.view;

import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.palette.panels.LayerPaneContainer;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MedicamentView extends JPanel {
    Theme currentTheme;
    JPanel mainPanel;

    public void _init() {
        setLayout(new BorderLayout());
        setOpaque(false);

        LayerPaneContainer layerPaneContainer = new LayerPaneContainer(currentTheme);


        mainPanel = initMainPanel();


        mainPanel.setBounds(0, 70, 1066, 698);

        layerPaneContainer.add(mainPanel, JLayeredPane.DEFAULT_LAYER);

        add(layerPaneContainer, BorderLayout.CENTER);
    }

    private JPanel initMainPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        JPanel contentHeader = new JPanel(new BorderLayout());
        contentHeader.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Médicaments",24,1);

        MyButton addMedicamentButton = new MyButton(currentTheme,"src/main/resources/images/add_medicament.png");
        addMedicamentButton.setOpaque(false);
        addMedicamentButton.changeMargin(10,15,10,15);

        addMedicamentButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //updatePanel(ajouterPatientPanel);
            }
        });

        contentHeader.add(title, BorderLayout.LINE_START);
        contentHeader.add(addMedicamentButton, BorderLayout.LINE_END);

        content.add(contentHeader, BorderLayout.NORTH);


        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    public MedicamentView(Theme currentTheme) {
        this.currentTheme = currentTheme;
        _init();
    }
}
