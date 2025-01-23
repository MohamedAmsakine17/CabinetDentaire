package ma.CabinetDentaire.presentation.view.dossier_medical;

import ma.CabinetDentaire.App;
import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Ordonnance;
import ma.CabinetDentaire.entities.PrescriptionDeMedicament;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.form.FormGroup;
import ma.CabinetDentaire.presentation.view.palette.form.SelectFormGroup;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Objects;

public class PrescriptionDeMedicamentPanel extends JPanel {
    Theme currentTheme;
    public PrescriptionDeMedicamentPanel(Theme currentTheme) {
        this.currentTheme = currentTheme;
    }

    public JPanel prescriptionDeMedicamentPanel(PrescriptionDeMedicament prescriptionDeMedicament) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        MyLabel title = new MyLabel(currentTheme,"Prescription de medicament #" + prescriptionDeMedicament.getIdPrescription(),22,1);
        headerPanel.add(title, BorderLayout.WEST);
        MyLabel backBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/back.png",32);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getOrdonnanceController().displayOrddonnaceById(prescriptionDeMedicament.getOrdonnance().getIdOrdonnance());
            }
        });
        headerPanel.add(backBtn, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);

        JPanel infoPanel = new JPanel(new GridLayout(5,1));
        infoPanel.setBorder(new EmptyBorder(25,25,75,0));
        infoPanel.setOpaque(false);
        MyLabel minAPrendreLabel = new MyLabel(currentTheme,"Min a prendre: " + prescriptionDeMedicament.getUnitesMinAPrendre(),18,0,"src/main/resources/images/icons/min.png",32);
        infoPanel.add(minAPrendreLabel);
        MyLabel maxAPrendreLabel = new MyLabel(currentTheme,"Max a prendre: " + prescriptionDeMedicament.getUnitesMaxAPrendre(),18,0,"src/main/resources/images/icons/max.png",32);
        infoPanel.add(maxAPrendreLabel);
        MyLabel contraintesAlimentationLabel = new MyLabel(currentTheme,"Contraintes Alimentation: " + prescriptionDeMedicament.getContraintesAlimentation(),18,0,"src/main/resources/images/icons/contraintesAlimentation.png",32);
        infoPanel.add(contraintesAlimentationLabel);
        MyLabel contraintesTempLabel = new MyLabel(currentTheme,"Contraintes Temps: " + prescriptionDeMedicament.getContraintesTemps(),18,0,"src/main/resources/images/icons/contraintesTemps.png",32);
        infoPanel.add(contraintesTempLabel);
        MyLabel medicamentLabel = new MyLabel(currentTheme,"Medicament: " + prescriptionDeMedicament.getMedicamentAPrescrire().getNom(),18,0,"src/main/resources/images/icons/medicament.png",32);
        infoPanel.add(medicamentLabel);
        bodyPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel medicamentPanel = new JPanel(new BorderLayout());
        medicamentPanel.setBorder(new EmptyBorder(50,0,50,75));
        medicamentPanel.setOpaque(false);
        MyLabel medicamentImage = new MyLabel(currentTheme,"src/main/resources/images/medicament/" + prescriptionDeMedicament.getMedicamentAPrescrire().getIamgeSrc(),156);
        medicamentPanel.add(medicamentImage, BorderLayout.CENTER);

        MyLabel medicamentNameLabel = new MyLabel(currentTheme,prescriptionDeMedicament.getMedicamentAPrescrire().getNom(),16,0);
        medicamentPanel.add(medicamentNameLabel, BorderLayout.SOUTH);
        bodyPanel.add(medicamentPanel, BorderLayout.EAST);

        JPanel actionsButtonContainer = new JPanel(new BorderLayout());
        actionsButtonContainer.setOpaque(false);
        MyLabel updateButton = new MyLabel(currentTheme,"src/main/resources/images/icons/green_edit.png",32);
        updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getPrescriptionController().modifieirPrescription(prescriptionDeMedicament);
            }
        });
        actionsButtonContainer.add(updateButton,BorderLayout.WEST);
        MyLabel deleteButton = new MyLabel(currentTheme,"src/main/resources/images/icons/red_delete.png",32);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getPrescriptionController().deletePrescriptionById(prescriptionDeMedicament.getIdPrescription());
                AppFactory.getOrdonnanceController().displayOrddonnaceById(prescriptionDeMedicament.getOrdonnance().getIdOrdonnance());
            }
        });
        actionsButtonContainer.add(deleteButton,BorderLayout.EAST);

        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        mainPanel.add(actionsButtonContainer, BorderLayout.SOUTH);
        return mainPanel;
    }

    public JPanel createPrescriptionMedicamentPanel(Ordonnance ordonnance) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Creation de Prescription de medicament",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);

        JPanel formPanel = new JPanel(new GridLayout(3,2));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30,0,60,120));
        //int unitesMin, int unitesMax, String contraintesAlimentation, Long ordonnanceId, String contraintesTemps, Long medicamentId

        FormGroup unitesMinFormGroup = new FormGroup(currentTheme,"Unites min a prendre:","src/main/resources/images/icons/min.png","1");
        formPanel.add(unitesMinFormGroup );
        FormGroup unitesMaxFormGroup  = new FormGroup(currentTheme,"Unites max a prendre:","src/main/resources/images/icons/max.png","3");
        formPanel.add(unitesMaxFormGroup );
        FormGroup contraintesAlimentationFormGroup  = new FormGroup(currentTheme,"Contraintes Alimentation:","src/main/resources/images/icons/contraintesAlimentation.png");
        formPanel.add(contraintesAlimentationFormGroup );
        FormGroup contraintesTempsFormGroup  = new FormGroup(currentTheme,"Contraintes Temps:","src/main/resources/images/icons/contraintesTemps.png");
        formPanel.add(contraintesTempsFormGroup );
        String[] medicamentList = new String[AppFactory.getMedicamentController().getAllMedicament().size()];
        for (int i = 0; i < medicamentList.length; i++) {
            medicamentList[i] = AppFactory.getMedicamentController().getAllMedicament().get(i).getNom();
        }
        SelectFormGroup medicamentIdFormGroup  = new SelectFormGroup(currentTheme,"Medicament","src/main/resources/images/edit_btn.png",medicamentList);
        formPanel.add(medicamentIdFormGroup );

        JPanel buttonContainer = new JPanel(new BorderLayout()); buttonContainer.setOpaque(false);
        MyButton createButton = new MyButton(currentTheme,"Créer",18,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/icons/white_add.png",22);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //int unitesMin, int unitesMax, String contraintesAlimentation, Long ordonnanceId, String contraintesTemps, Long medicamentId

                int unitesMin = Integer.parseInt(unitesMinFormGroup.getInputField().getText());
                int unitesMax = Integer.parseInt(unitesMaxFormGroup.getInputField().getText());
                String contraintesAlimentation = contraintesAlimentationFormGroup.getInputField().getText();
                String contraintesTemps = contraintesTempsFormGroup.getInputField().getText();
                Long medicamentId = AppFactory.getMedicamentController().getMedicamentByName(Objects.requireNonNull(medicamentIdFormGroup.getSelectField().getSelectedItem()).toString()).getIdMedicament();

                AppFactory.getPrescriptionController().createPrescription(unitesMin,unitesMax,contraintesAlimentation,ordonnance.getIdOrdonnance(),contraintesTemps,medicamentId);
                AppFactory.getOrdonnanceController().displayOrddonnaceById(ordonnance.getIdOrdonnance());
            }
        });
        buttonContainer.add(createButton, BorderLayout.WEST);

        bodyPanel.add(formPanel, BorderLayout.CENTER);
        bodyPanel.add(buttonContainer, BorderLayout.SOUTH);


        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    public JPanel modifierPrescriptionMedicamentPanel(PrescriptionDeMedicament prescriptionDeMedicament) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Creation de Prescription de medicament",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);

        JPanel formPanel = new JPanel(new GridLayout(3,2));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30,0,60,120));
        //int unitesMin, int unitesMax, String contraintesAlimentation, Long ordonnanceId, String contraintesTemps, Long medicamentId

        FormGroup unitesMinFormGroup = new FormGroup(currentTheme,"Unites min a prendre:","src/main/resources/images/icons/min.png",prescriptionDeMedicament.getUnitesMinAPrendre() + "");
        formPanel.add(unitesMinFormGroup );
        FormGroup unitesMaxFormGroup  = new FormGroup(currentTheme,"Unites max a prendre:","src/main/resources/images/icons/max.png",prescriptionDeMedicament.getUnitesMaxAPrendre() + "");
        formPanel.add(unitesMaxFormGroup );
        FormGroup contraintesAlimentationFormGroup  = new FormGroup(currentTheme,"Contraintes Alimentation:","src/main/resources/images/icons/contraintesAlimentation.png",prescriptionDeMedicament.getContraintesAlimentation());
        formPanel.add(contraintesAlimentationFormGroup );
        FormGroup contraintesTempsFormGroup  = new FormGroup(currentTheme,"Contraintes Temps:","src/main/resources/images/icons/contraintesTemps.png",prescriptionDeMedicament.getContraintesTemps());
        formPanel.add(contraintesTempsFormGroup );
        String[] medicamentList = new String[AppFactory.getMedicamentController().getAllMedicament().size()];
        for (int i = 0; i < medicamentList.length; i++) {
            medicamentList[i] = AppFactory.getMedicamentController().getAllMedicament().get(i).getNom();
        }
        SelectFormGroup medicamentIdFormGroup  = new SelectFormGroup(currentTheme,"Medicament","src/main/resources/images/edit_btn.png",medicamentList);
        formPanel.add(medicamentIdFormGroup );

        JPanel buttonContainer = new JPanel(new BorderLayout()); buttonContainer.setOpaque(false);
        MyButton createButton = new MyButton(currentTheme,"Modifier",18,currentTheme.blueColor(),currentTheme.blueHoverColor(),"src/main/resources/images/icons/white_add.png",22);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //int unitesMin, int unitesMax, String contraintesAlimentation, Long ordonnanceId, String contraintesTemps, Long medicamentId

                int unitesMin = Integer.parseInt(unitesMinFormGroup.getInputField().getText());
                int unitesMax = Integer.parseInt(unitesMaxFormGroup.getInputField().getText());
                String contraintesAlimentation = contraintesAlimentationFormGroup.getInputField().getText();
                String contraintesTemps = contraintesTempsFormGroup.getInputField().getText();
                //Long medicamentId = AppFactory.getMedicamentController().getMedicamentByName(Objects.requireNonNull(medicamentIdFormGroup.getSelectField().getSelectedItem()).toString()).getIdMedicament();

                prescriptionDeMedicament.setUnitesMinAPrendre(unitesMin);
                prescriptionDeMedicament.setUnitesMaxAPrendre(unitesMax);
                prescriptionDeMedicament.setContraintesAlimentation(contraintesAlimentation);
                prescriptionDeMedicament.setContraintesTemps(contraintesTemps);
                prescriptionDeMedicament.setMedicamentAPrescrire(AppFactory.getMedicamentController().getMedicamentByName(Objects.requireNonNull(medicamentIdFormGroup.getSelectField().getSelectedItem()).toString()));

                AppFactory.getPrescriptionController().updatePrescription(prescriptionDeMedicament);
                AppFactory.getPrescriptionController().displayPrescriptionDeMedicament(prescriptionDeMedicament);
            }
        });
        buttonContainer.add(createButton, BorderLayout.WEST);

        bodyPanel.add(formPanel, BorderLayout.CENTER);
        bodyPanel.add(buttonContainer, BorderLayout.SOUTH);


        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        return mainPanel;
    }
}
