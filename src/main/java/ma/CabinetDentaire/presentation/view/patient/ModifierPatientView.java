package ma.CabinetDentaire.presentation.view.patient;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.Mutuelle;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.presentation.view.MainView;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.form.FormGroup;
import ma.CabinetDentaire.presentation.view.palette.form.SelectFormGroup;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.util.RoundedLabelUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class ModifierPatientView extends JPanel {
    private Theme currentTheme;
    private MyLabel cin, nom_et_prenom, email, telephone, adresse, sexe, mutuelle;
    private FormGroup emailFormGroup, telephoneFormGroup, addressFormGroup, dateNaissanceFormGroup, cinFormGroup, nomFormGroup, prenomFormGroup;
    private SelectFormGroup assuranceFormGroup, sexeFormGroup;
    private MyLabel errorMessageLabel;


    private Patient patient;
    private boolean isPictureUpdated;

    private void _init(){
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));
        headerPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Ajouter Patient",24,1);

        MyButton backBtn = new MyButton(currentTheme,"src/main/resources/images/back_btn.png");
        backBtn.setOpaque(false);
        backBtn.changeMargin(5, 30, 5, 0);
        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainView.updateRightPanel(AppFactory.getDossierMedicalController().showPatientDossierMedicale(patient.getId()));
            }
        });

        headerPanel.add(title, BorderLayout.LINE_START);
        headerPanel.add(backBtn, BorderLayout.LINE_END);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
        bodyPanel.setOpaque(false);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.GREEN);

        JPanel patientInfoPanel = new JPanel(new BorderLayout());
        patientInfoPanel.setBackground(currentTheme.greenColor());

        JPanel pfpPanel = new JPanel();
        pfpPanel.setOpaque(false);
        pfpPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        ImageIcon icon = new ImageIcon("src/main/resources/images/patient_pfp/"+patient.getPhotoDeProfile());
        try{
            icon = RoundedLabelUtils.makeImageRounded("src/main/resources/images/patient_pfp/default_pfp.png"+patient.getPhotoDeProfile());
        } catch (IOException e1) {
            System.out.println(e1.getMessage());
        }
        Image img = icon.getImage().getScaledInstance(128,128, Image.SCALE_SMOOTH);
        JLabel pfplabel = new JLabel();
        pfplabel.setPreferredSize(new Dimension(128, 128));
        pfplabel.setIcon(new ImageIcon(img));
        pfplabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pfplabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                Component parent = SwingUtilities.getWindowAncestor(ModifierPatientView.this);
                int result = fileChooser.showOpenDialog(parent);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        ImageIcon uploadedIcon = new ImageIcon(selectedFile.getAbsolutePath());
                        Image updatedImg = uploadedIcon.getImage().getScaledInstance(128,128, Image.SCALE_SMOOTH);
                        pfplabel.setIcon(new ImageIcon(updatedImg));

                        File outputDir = new File("src/main/resources/images/patient_pfp");
                        if (!outputDir.exists()) outputDir.mkdirs();
                        File outputFile = new File(outputDir, "uploaded_picture.jpg");
                        BufferedImage uploadedImage = ImageIO.read(selectedFile);
                        ImageIO.write(uploadedImage, "jpg", outputFile);
                        isPictureUpdated = true;
                        System.out.println("Image saved to: " + outputFile.getAbsolutePath());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(parent, "Failed to upload image: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        RoundedLabelUtils.makeRounded(pfplabel, 128);
        pfpPanel.add(pfplabel);

        JPanel patientInfo = new JPanel(new GridLayout(7,1));
        patientInfo.setOpaque(false);
        patientInfo.setBorder(BorderFactory.createEmptyBorder(0, 25, 125, 25));
        cin = new MyLabel(currentTheme,patient.getCin(),12,0);
        cin.changeColor(currentTheme.whiteColor());
        nom_et_prenom = new MyLabel(currentTheme,patient.getNom() + " " + patient.getPrenom(),20,1);
        nom_et_prenom.changeColor(currentTheme.whiteColor());
        email = new MyLabel(currentTheme,patient.getEmail(),13,1);
        email.changeColor(currentTheme.whiteColor());
        telephone = new MyLabel(currentTheme,patient.getTelephone(),16,0);
        telephone.changeColor(currentTheme.whiteColor());
        adresse = new MyLabel(currentTheme,patient.getAdresse(),16,0);
        adresse.changeColor(currentTheme.whiteColor());
        sexe = new MyLabel(currentTheme,patient.getSexe().toString(),16,0);
        sexe.changeColor(currentTheme.whiteColor());
        mutuelle = new MyLabel(currentTheme,patient.getMutuelle().toString(),16,0);
        mutuelle.changeColor(currentTheme.whiteColor());
        patientInfo.add(cin);
        patientInfo.add(nom_et_prenom);
        patientInfo.add(email);
        patientInfo.add(telephone);
        patientInfo.add(adresse);
        patientInfo.add(sexe);
        patientInfo.add(mutuelle);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        footerPanel.setOpaque(false);
        MyButton modifierButton = new MyButton(currentTheme,"Modifier",currentTheme.whiteColor(),currentTheme.darkBgColor());
        modifierButton.setForeground(currentTheme.greenColor());
        modifierButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                modifier();
            }
        });
        footerPanel.add(modifierButton, BorderLayout.CENTER);

        patientInfoPanel.add(pfpPanel, BorderLayout.NORTH);
        patientInfoPanel.add(patientInfo, BorderLayout.CENTER);
        patientInfoPanel.add(footerPanel, BorderLayout.SOUTH);

        JPanel formContainerPanel = new JPanel(new GridLayout(6,2));
        formContainerPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        formContainerPanel.setBackground(currentTheme.bgColor());
        //ID|NOM|PRENOM|CIN|ADRESSE|TELEPHONE|EMAIL|PFP|DATA_NAISSANCE|SEXE|GROUP_SANGUIN|MUTUELLE|PROFESSION|DOSSIERMEDICALE
        nomFormGroup = new FormGroup(currentTheme,"Nom","src/main/resources/images/form/nom.png",patient.getNom());
        formContainerPanel.add(nomFormGroup);
        prenomFormGroup = new FormGroup(currentTheme,"Prenom","src/main/resources/images/form/prenom.png", patient.getPrenom());
        formContainerPanel.add(prenomFormGroup);
        updateText(nomFormGroup,prenomFormGroup,nom_et_prenom);

        emailFormGroup = new FormGroup(currentTheme,"Email","src/main/resources/images/form/email.png", patient.getEmail());
        formContainerPanel.add(emailFormGroup);
        updateText(emailFormGroup,email);
        telephoneFormGroup = new FormGroup(currentTheme,"Telephone","src/main/resources/images/form/telephone.png", patient.getTelephone());
        formContainerPanel.add(telephoneFormGroup);
        updateText(telephoneFormGroup,telephone);
        addressFormGroup = new FormGroup(currentTheme,"Adresse","src/main/resources/images/form/address.png", patient.getAdresse());
        formContainerPanel.add(addressFormGroup);
        updateText(addressFormGroup,adresse);
        dateNaissanceFormGroup = new FormGroup(currentTheme,"Date de naissance","src/main/resources/images/form/data_de_naissance.png",patient.getDataDeNaissance().toString());
        formContainerPanel.add(dateNaissanceFormGroup);
        assuranceFormGroup = new SelectFormGroup(currentTheme,"Assurance","src/main/resources/images/form/assurance.png",new String[]{"CIMR","CNOPS","CNSS","Autre"});
        formContainerPanel.add(assuranceFormGroup);
        updateText(assuranceFormGroup,mutuelle);
        cinFormGroup = new FormGroup(currentTheme,"CIN","src/main/resources/images/form/cin.png",patient.getCin());
        formContainerPanel.add(cinFormGroup);
        updateText(cinFormGroup,cin);
        sexeFormGroup = new SelectFormGroup(currentTheme,"Sexe","src/main/resources/images/form/sexe.png",new String[]{"Homme","Femme"});
        formContainerPanel.add(sexeFormGroup);
        updateText(sexeFormGroup,sexe);
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        formContainerPanel.add(emptyPanel);
        errorMessageLabel = new MyLabel(currentTheme,"",12,1);
        errorMessageLabel.changeColor(currentTheme.redColor());
        errorMessageLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        formContainerPanel.add(errorMessageLabel);
        JPanel clearBtnContainer =new JPanel(new BorderLayout());
        clearBtnContainer.setOpaque(false);
        MyButton clearBtn = new MyButton(currentTheme,"src/main/resources/images/refresh.png");
        clearBtn.setOpaque(false);
        clearBtnContainer.add(clearBtn, BorderLayout.LINE_END);
        clearBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refrech();
            }
        });
        formContainerPanel.add(clearBtnContainer);

        contentPanel.add(patientInfoPanel, BorderLayout.LINE_START);
        contentPanel.add(formContainerPanel, BorderLayout.CENTER);

        bodyPanel.add(contentPanel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);
    }

    private void updateText(SelectFormGroup selectFormGroup , MyLabel label){
        selectFormGroup.getSelectField().addActionListener(e -> label.setText((String) selectFormGroup.getSelectField().getSelectedItem()));
    }

    private void updateText(FormGroup formGroup, MyLabel label){
        formGroup.getInputField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                label.setText(formGroup.getInputField().getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                label.setText(formGroup.getInputField().getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                label.setText(formGroup.getInputField().getText());
            }
        });
    }

    private void updateText(FormGroup formGroup, FormGroup formGroup2, MyLabel label) {
        formGroup.getInputField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabelText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabelText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabelText();
            }

            private void updateLabelText() {
                String text1 = formGroup.getInputField().getText();
                String text2 = formGroup2.getInputField().getText();
                label.setText(text1 + " " + text2); // Concatenate with a space
            }
        });

        formGroup2.getInputField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabelText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabelText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabelText();
            }

            private void updateLabelText() {
                String text1 = formGroup.getInputField().getText();
                String text2 = formGroup2.getInputField().getText();
                label.setText(text1 + " " + text2); // Concatenate with a space
            }
        });
    }



    private void modifier(){
        //Long id, String nom, String prenom,String cin, String address, String telephone, String email, String pfp, LocalDate dateNaissance, Sexe sexe, Mutuelle mutuelle
        String nom = nomFormGroup.getInputField().getText();
        String prenom = prenomFormGroup.getInputField().getText();
        String email = emailFormGroup.getInputField().getText();
        String phone = telephoneFormGroup.getInputField().getText();
        String address = addressFormGroup.getInputField().getText();
        String dateNaissance = dateNaissanceFormGroup.getInputField().getText();
        String cin = cinFormGroup.getInputField().getText();

        String value = (String) sexeFormGroup.getSelectField().getSelectedItem();
        Sexe sexe = value.equals("Homme") ? Sexe.HOMME : Sexe.FEMME;
        value = ((String) assuranceFormGroup.getSelectField().getSelectedItem());
        Mutuelle mutuelle = value.equals("CIMR") ? Mutuelle.CIMR
                : value.equals("CNAM") ? Mutuelle.CNAM
                : value.equals("CNSS") ? Mutuelle.CNSS
                : Mutuelle.CNOPS;;

        if(nom.isEmpty()) {
            errorMessageLabel.setText("Le nom est requis");
            return;
        }
        if(prenom.isEmpty()) {
            errorMessageLabel.setText("Le prenom est requis");
            return;
        }
        if(email.isEmpty()) {
            errorMessageLabel.setText("L'email est requis");
            return;
        }
        if(phone.isEmpty()) {
            errorMessageLabel.setText("Le Telephone est requis");
            return;
        }
        if(address.isEmpty()) {
            errorMessageLabel.setText("Le adresse est requis");
            return;
        }
        if(dateNaissance.isEmpty()) {
            errorMessageLabel.setText("Le Date de naissance est requis");
            return;
        }
        if(cin.isEmpty()) {
            errorMessageLabel.setText("Le CIN est requis");
            return;
        }

        String pictureName = patient.getPhotoDeProfile();

        if(isPictureUpdated){
            pictureName = (nomFormGroup.getInputField().getText() + cinFormGroup.getInputField().getText()).replace(" ","");
            updatePicturName(pictureName);
        }

        errorMessageLabel.setText("");

        //Long id, String nom, String prenom,String cin, String address, String telephone, String email, String pfp, LocalDate dateNaissance, Sexe sexe, Mutuelle mutuelle
        AppFactory.getPatientController().updatePatient(patient.getId(),nom,prenom,cin,address,phone,email,pictureName,LocalDate.parse(dateNaissance),sexe,mutuelle);
    }

    private void updatePicturName(String name){
        // Source file
        File sourceFile = new File("src/main/resources/images/patient_pfp/uploaded_picture.jpg");

        // Destination directory
        File destinationDir = new File("src/main/resources/images/patient_pfp");
        if (!destinationDir.exists()) {
            destinationDir.mkdirs(); // Create directory if it doesn't exist
        }

        // New file name
        String newFileName = name;
        File destinationFile = new File(destinationDir, newFileName);

        try {
            // Copy and rename the file
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied to: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to copy and rename the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void refrech(){
        emailFormGroup.getInputField().setText("");
        telephoneFormGroup.getInputField().setText("");
        addressFormGroup.getInputField().setText("");
        dateNaissanceFormGroup.getInputField().setText("");
        cinFormGroup.getInputField().setText("");
        nomFormGroup.getInputField().setText("");
        prenomFormGroup.getInputField().setText("");

        cin.setText("CIN");
        nom_et_prenom.setText("Nom et Prenom");
        email.setText("Email");
        telephone.setText("Telephone");
        adresse.setText("Adresse");
        sexe.setText("Homme");
        mutuelle.setText("CIMR");
    }

    public ModifierPatientView(Theme currentTheme, Patient patient) {
        this.currentTheme = currentTheme;
        this.patient = patient;
        isPictureUpdated = false;
        _init();
    }
}
