package ma.CabinetDentaire.presentation.view.dossier_medical;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Acte;
import ma.CabinetDentaire.entities.enums.CategorieActe;
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
import java.util.List;
import java.util.Objects;

public class ActePanel extends JPanel {
    Theme currentTheme;
    public ActePanel(Theme currentTheme) {
        this.currentTheme = currentTheme;
    }

    public JPanel createActe() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Creation d'acte'",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);

        JPanel formPanel = new JPanel(new GridLayout(3,2));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30,0,60,120));
        //String libelle, Double prixDeBase, CategorieActe categorie

        FormGroup libelleFormGroup = new FormGroup(currentTheme,"Libelle:","src/main/resources/images/icons/min.png");
        formPanel.add(libelleFormGroup );
        FormGroup prixDeBaseFormGroup  = new FormGroup(currentTheme,"Prix de base:","src/main/resources/images/icons/max.png");
        formPanel.add(prixDeBaseFormGroup );
        SelectFormGroup categorieFormGroup  = new SelectFormGroup(currentTheme,"Categorie:","src/main/resources/images/edit_btn.png",new String[]{"Prévention","Prothèses dentaires","Orthodontie","Implantologie","Chirurgie","Soins des tissus mous","Soins des caries","Diagnostic"});
        formPanel.add(categorieFormGroup );

        JPanel buttonContainer = new JPanel(new BorderLayout()); buttonContainer.setOpaque(false);
        MyButton createButton = new MyButton(currentTheme,"Créer",18,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/icons/white_add.png",22);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                String libelle = libelleFormGroup.getInputField().getText();
                Double prixDeBase = Double.parseDouble(prixDeBaseFormGroup.getInputField().getText());
                CategorieActe categorie = categorieFormGroup.getSelectField().getSelectedItem().equals("Prévention") ? CategorieActe.PREVENTION
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Prothèses dentaires") ? CategorieActe.PROTHESES_DENTAIRES
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Orthodontie") ? CategorieActe.ORTHODONTIE
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Implantologie") ? CategorieActe.IMPLANTOLOGIE
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Chirurgie") ? CategorieActe.CHIRURGIE
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Soins des tissus mous") ? CategorieActe.SOINS_DES_TISSUS_MOUS
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Soins des caries") ? CategorieActe.SOINS_DES_CARIES
                        : CategorieActe.DIAGNOSTIC
                        ;

                AppFactory.getActeController().createActe(libelle,prixDeBase,categorie);
            }
        });
        buttonContainer.add(createButton, BorderLayout.WEST);

        bodyPanel.add(formPanel, BorderLayout.CENTER);
        bodyPanel.add(buttonContainer, BorderLayout.SOUTH);


        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    public JPanel actePanel(Acte acte) {
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        return mainPanel;
    }

    public JPanel acteAllPanels(List<Acte> actes) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel headerMainPanel = new JPanel(new BorderLayout());
        headerMainPanel.setOpaque(false);
        MyLabel title = new MyLabel(currentTheme,"Les Actes",22,1);
        headerMainPanel.add(title, BorderLayout.WEST);
        MyLabel addActeButton = new MyLabel(currentTheme,"src/main/resources/images/icons/green_add.png",32);
        addActeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        headerMainPanel.add(addActeButton, BorderLayout.EAST);
        addActeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getActeController().createActePanel();
            }
        });

        mainPanel.add(headerMainPanel,BorderLayout.NORTH);

        JPanel mainBodyContent = new JPanel(new GridLayout(7,1,0,10));
        mainBodyContent.setOpaque(false);
        mainBodyContent.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));

        JPanel headerTablePanel = new JPanel(new GridLayout(1,5));
        headerTablePanel.setBackground(currentTheme.greenColor());
        headerTablePanel.add(new Label());
        MyLabel typeColLabel = new MyLabel(currentTheme,"Libelle",18,1);
        headerTablePanel.add(typeColLabel);
        MyLabel motifColLabel = new MyLabel(currentTheme,"Categorie",18,1);
        headerTablePanel.add(motifColLabel);
        MyLabel tempColLabel = new MyLabel(currentTheme,"Prix",18,1);
        headerTablePanel.add(tempColLabel);
        mainBodyContent.add(headerTablePanel);

        actes.forEach(acte -> {
            JPanel colTablePanel = new JPanel(new GridLayout(1,5));
            colTablePanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()), // Green bottom border
                    BorderFactory.createEmptyBorder(0, 0, 0, 0) // Padding
            ));
            colTablePanel.setBackground(currentTheme.fieldsBgColor());

            MyLabel acteID = new MyLabel( currentTheme,"#" +acte.getId().toString(),16,0);
            acteID.setBorder(new EmptyBorder(0, 15, 0, 0));
            colTablePanel.add(acteID);
            MyLabel acteLibbelle = new MyLabel(currentTheme,acte.getLibelle(),16,0);
            colTablePanel.add(acteLibbelle);
            MyLabel acteCategorie = new MyLabel(currentTheme,acte.getCategorieActe().toString(),16,0);
            colTablePanel.add(acteCategorie);
            MyLabel actePrix = new MyLabel(currentTheme,acte.getPrixDeBase().toString(),16,0);
            colTablePanel.add(actePrix);

            JPanel actionsButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            actionsButtonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
            actionsButtonContainer.setOpaque(false);
            MyLabel updateBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/green_edit.png",26);
            updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            updateBtn.setBorder(BorderFactory.createEmptyBorder(0,0,0,15));
            updateBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getActeController().updateActePanel(acte);
                }
            });
            actionsButtonContainer.add(updateBtn);
            MyLabel deleteBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/red_delete.png",26);
            deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            deleteBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getActeController().deleteActeById(acte.getId());
                }
            });
            actionsButtonContainer.add(deleteBtn);
            colTablePanel.add(actionsButtonContainer);

            mainBodyContent.add(colTablePanel);
        });

        mainPanel.add(mainBodyContent,BorderLayout.CENTER);
        return mainPanel;
    }

    public JPanel updateActe(Acte acte) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Creation d'acte'",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);

        JPanel formPanel = new JPanel(new GridLayout(3,2));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30,0,60,120));
        //String libelle, Double prixDeBase, CategorieActe categorie

        FormGroup libelleFormGroup = new FormGroup(currentTheme,"Libelle:","src/main/resources/images/icons/min.png",acte.getLibelle());
        formPanel.add(libelleFormGroup );
        FormGroup prixDeBaseFormGroup  = new FormGroup(currentTheme,"Prix de base:","src/main/resources/images/icons/max.png",acte.getPrixDeBase().toString());
        formPanel.add(prixDeBaseFormGroup );
        SelectFormGroup categorieFormGroup  = new SelectFormGroup(currentTheme,"Categorie:","src/main/resources/images/edit_btn.png",new String[]{"Prévention","Prothèses dentaires","Orthodontie","Implantologie","Chirurgie","Soins des tissus mous","Soins des caries","Diagnostic"});
        formPanel.add(categorieFormGroup );

        JPanel buttonContainer = new JPanel(new BorderLayout()); buttonContainer.setOpaque(false);
        MyButton createButton = new MyButton(currentTheme,"Modifier",18,currentTheme.blueColor(),currentTheme.blueHoverColor(),"src/main/resources/images/icons/white_add.png",22);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                String libelle = libelleFormGroup.getInputField().getText();
                Double prixDeBase = Double.parseDouble(prixDeBaseFormGroup.getInputField().getText());
                CategorieActe categorie = categorieFormGroup.getSelectField().getSelectedItem().equals("Prévention") ? CategorieActe.PREVENTION
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Prothèses dentaires") ? CategorieActe.PROTHESES_DENTAIRES
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Orthodontie") ? CategorieActe.ORTHODONTIE
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Implantologie") ? CategorieActe.IMPLANTOLOGIE
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Chirurgie") ? CategorieActe.CHIRURGIE
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Soins des tissus mous") ? CategorieActe.SOINS_DES_TISSUS_MOUS
                        :categorieFormGroup.getSelectField().getSelectedItem().equals("Soins des caries") ? CategorieActe.SOINS_DES_CARIES
                        : CategorieActe.DIAGNOSTIC
                        ;

                acte.setLibelle(libelle);
                acte.setPrixDeBase(prixDeBase);
                acte.setCategorieActe(categorie);

                AppFactory.getActeController().updateActe(acte);
            }
        });
        buttonContainer.add(createButton, BorderLayout.WEST);

        bodyPanel.add(formPanel, BorderLayout.CENTER);
        bodyPanel.add(buttonContainer, BorderLayout.SOUTH);


        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        return mainPanel;
    }

}
