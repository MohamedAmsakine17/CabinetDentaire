package ma.CabinetDentaire.presentation.view.Medicament;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Medicament;
import ma.CabinetDentaire.presentation.view.MainView;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.palette.panels.LayerPaneContainer;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MedicamentView extends JPanel {
    Theme currentTheme;
    JPanel mainPanel;
    List<Medicament> medicaments;
    AjouterMedicamentView ajouterMedicament;

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

        // Header
        JPanel headerContentPanel = new JPanel(new BorderLayout());
        headerContentPanel.setOpaque(false);
        MyLabel title = new MyLabel(currentTheme,"Médicaments",24,1);
        MyButton addMedicamentButton = new MyButton(currentTheme,"src/main/resources/images/add_medicament.png");
        addMedicamentButton.setOpaque(false);
        addMedicamentButton.changeMargin(10,15,20,15);
        addMedicamentButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                MainView.updateRightPanel(ajouterMedicament);
            }
        });
        headerContentPanel.add(title, BorderLayout.LINE_START);
        headerContentPanel.add(addMedicamentButton, BorderLayout.LINE_END);

        // Medicament List Panel
        JPanel medicamentPanel = new JPanel(new GridLayout(2, 4, 20, 15));
        medicamentPanel.setOpaque(false);

        int[] currentPage = {0};

        Runnable updateMedicamentPanel = () -> {
            medicamentPanel.removeAll();
            int start = currentPage[0] * 8;
            int end = Math.min(start + 8, medicaments.size());

            for (int i = start; i < end; i++) {
                JPanel medicament = new JPanel(new BorderLayout());
                medicament.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 5, 0, currentTheme.greenColor()), // Green bottom border
                        BorderFactory.createEmptyBorder(0, 0, 0, 0) // Padding
                ));
                medicament.setPreferredSize(new Dimension(225, 250)); // Adjust as needed
                medicament.setBackground(currentTheme.bgColor());

                medicament.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {

                    }
                });

                // Header with Delete Button
                JPanel medicamentHeader = new JPanel(new BorderLayout());
                MyButton deleteBtn = new MyButton(currentTheme, "src/main/resources/images/delete_btn.png");
                deleteBtn.changeImageSize(20);
                deleteBtn.changeMargin(5, 5, 5, 5);
                deleteBtn.setOpaque(false);
                int finalI = i;
                deleteBtn.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        AppFactory.getMedicamentController().deleteMedicament(medicaments.get(finalI).getIdMedicament());
                    }
                });
                medicamentHeader.add(deleteBtn, BorderLayout.LINE_END);
                medicamentHeader.setOpaque(false);

                // Placeholder for Image in a Fixed Size Panel
                JPanel imageContainer = new JPanel(new BorderLayout());
                imageContainer.setPreferredSize(new Dimension(150, 150));
                imageContainer.setOpaque(false);

                ImageIcon icon = new ImageIcon("src/main/resources/images/medicament/" + medicaments.get(i).getIamgeSrc());
                icon.setImage(icon.getImage().getScaledInstance(96, 96, Image.SCALE_SMOOTH));
                JLabel medicamentImage = new JLabel(icon, SwingConstants.CENTER);
                imageContainer.add(medicamentImage, BorderLayout.CENTER);

                // Footer with Info and Edit Button
                JPanel medicamentFooter = new JPanel(new BorderLayout());
                medicamentFooter.setOpaque(false);
                medicamentFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JPanel medicamentInfoPanel = new JPanel(new GridLayout(3, 1));
                MyLabel medicamentNom = new MyLabel(currentTheme, medicaments.get(i).getNom(), 12, 1);
                MyLabel medicamentPrix = new MyLabel(currentTheme, medicaments.get(i).getPrix() + " MAD", 16, 0);
                JTextArea medicamentDescription = new JTextArea(medicaments.get(i).getDescription());
                medicamentDescription.setLineWrap(true);
                medicamentDescription.setWrapStyleWord(true);
                medicamentDescription.setEditable(false);
                medicamentDescription.setOpaque(false);
                medicamentDescription.setFont(new Font("Arial", Font.PLAIN, 10));

                medicamentInfoPanel.add(medicamentNom);
                medicamentInfoPanel.add(medicamentPrix);
                medicamentInfoPanel.add(medicamentDescription);
                medicamentInfoPanel.setOpaque(false);

                MyButton editBtn = new MyButton(currentTheme, "src/main/resources/images/edit_btn.png");
                editBtn.changeImageSize(24);
                editBtn.changeMargin(5, 5, 5, 5);
                editBtn.setOpaque(false);

                editBtn.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        MainView.updateRightPanel(AppFactory.getMedicamentController().showModifierMedicamentView(medicaments.get(finalI)));
                    }
                });

                medicamentFooter.add(editBtn, BorderLayout.LINE_END);
                medicamentFooter.add(medicamentInfoPanel, BorderLayout.CENTER);

                medicament.add(medicamentHeader, BorderLayout.NORTH);
                medicament.add(imageContainer, BorderLayout.CENTER);
                medicament.add(medicamentFooter, BorderLayout.SOUTH);

                medicamentPanel.add(medicament);
            }

            medicamentPanel.revalidate();
            medicamentPanel.repaint();
        };


        // Footer
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        paginationPanel.setOpaque(false);

        MyButton nextButton = new MyButton(currentTheme,"src/main/resources/images/right-arrow.png"); nextButton.changeImageSize(42); nextButton.setOpaque(false);
        nextButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if ((currentPage[0] + 1) * 8 < medicaments.size()) {
                    currentPage[0]++;
                    updateMedicamentPanel.run();
                }
            }
        });
        MyButton prevButton = new MyButton(currentTheme,"src/main/resources/images/left-arrow.png"); prevButton.changeImageSize(42); prevButton.setOpaque(false);
        prevButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (currentPage[0] > 0) {
                    currentPage[0]--;
                    updateMedicamentPanel.run();
                }
            }
        });

        paginationPanel.add(prevButton);
        paginationPanel.add(nextButton);

        updateMedicamentPanel.run();

        content.add(headerContentPanel, BorderLayout.NORTH);
        content.add(medicamentPanel, BorderLayout.CENTER);
        content.add(paginationPanel, BorderLayout.SOUTH);

        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    public MedicamentView(Theme currentTheme, List<Medicament> medicaments) {
        this.currentTheme = currentTheme;
        this.medicaments = medicaments;

        ajouterMedicament = new AjouterMedicamentView(currentTheme);

        _init();
    }
}
