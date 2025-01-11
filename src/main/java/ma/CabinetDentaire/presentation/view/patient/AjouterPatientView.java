package ma.CabinetDentaire.presentation.view.patient;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.enums.Mutuelle;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.form.FormGroup;
import ma.CabinetDentaire.presentation.view.palette.form.SelectFormGroup;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

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

public class AjouterPatientView extends JPanel {
    private Theme currentTheme;
    private JPanel parentPanel;
    private MyLabel cin, nom_et_prenom, email, telephone, adresse, sexe, mutuelle;
    private FormGroup emailFormGroup, telephoneFormGroup, addressFormGroup, dateNaissanceFormGroup, cinFormGroup, nomFormGroup, prenomFormGroup;
    private SelectFormGroup assuranceFormGroup, sexeFormGroup;
    private MyLabel errorMessageLabel;

    public String getNom(){return nomFormGroup.getInputField().getText();}
    public String getPrenom(){return prenomFormGroup.getInputField().getText();}
    public String getEmail(){return emailFormGroup.getInputField().getText();}
    public String getTelephone(){return telephoneFormGroup.getInputField().getText();}
    public String getAddress(){return addressFormGroup.getInputField().getText();}
    public String getCin(){return cinFormGroup.getInputField().getText();}
    public Sexe getSexe(){
        String value = (String) sexeFormGroup.getSelectField().getSelectedItem();
        return value.equals("Homme") ? Sexe.HOMME : Sexe.FEMME;
    }
    public Mutuelle getAssurance(){
        String value = ((String) assuranceFormGroup.getSelectField().getSelectedItem()) ;
        return value.equals("CIMR") ? Mutuelle.CIMR
                : value.equals("CNAM") ? Mutuelle.CNAM
                : value.equals("CNSS") ? Mutuelle.CNSS
                : Mutuelle.CNOPS;
    }
    public LocalDate getDateNaissance(){return LocalDate.parse(dateNaissanceFormGroup.getInputField().getText());}
    public String getPfpPath(){return getNom() + getCin() + ".png";}

    private void _init(){
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));
        headerPanel.setOpaque(false);

            MyLabel title = new MyLabel(currentTheme,"Ajouter Patient",24,1);

            MyButton backBtn = new MyButton(currentTheme,"src/main/resources/images/back_btn.png");
            backBtn.setOpaque(false);
            backBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    parentPanel.removeAll();
                    parentPanel.revalidate();
                    parentPanel.repaint();
                    parentPanel.add(AppFactory.getPatientController().showAllPatients());
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

                    JPanel pfpPanel = new JPanel(new BorderLayout());
                    pfpPanel.setOpaque(false);
                    pfpPanel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 0));
                        ImageIcon icon = new ImageIcon("src/main/resources/images/patient_pfp/default_picture.jpg");
                        Image img = icon.getImage().getScaledInstance(128,128, Image.SCALE_SMOOTH);
                        JLabel pfplabel = new JLabel();
                        pfplabel.setPreferredSize(new Dimension(250,150));
                        pfplabel.setIcon(new ImageIcon(img));
                        pfplabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                        pfplabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                JFileChooser fileChooser = new JFileChooser();
                                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                                Component parent = SwingUtilities.getWindowAncestor(AjouterPatientView.this);
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
                                        System.out.println("Image saved to: " + outputFile.getAbsolutePath());
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        JOptionPane.showMessageDialog(parent, "Failed to upload image: " + ex.getMessage(),
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        });


                    pfpPanel.add(pfplabel, BorderLayout.CENTER);

                    JPanel patientInof= new JPanel(new GridLayout(7,1));
                    patientInof.setOpaque(false);
                    patientInof.setBorder(BorderFactory.createEmptyBorder(0, 25, 125, 25));
                        cin = new MyLabel(currentTheme,"CIN",12,0);
                        cin.changeColor(currentTheme.whiteColor());
                        nom_et_prenom = new MyLabel(currentTheme,"Nom et prenom",20,1);
                        nom_et_prenom.changeColor(currentTheme.whiteColor());
                        email = new MyLabel(currentTheme,"Email",13,1);
                        email.changeColor(currentTheme.whiteColor());
                        telephone = new MyLabel(currentTheme,"Telephone",16,0);
                        telephone.changeColor(currentTheme.whiteColor());
                        adresse = new MyLabel(currentTheme,"Adresse",16,0);
                        adresse.changeColor(currentTheme.whiteColor());
                        sexe = new MyLabel(currentTheme,"Homme",16,0);
                        sexe.changeColor(currentTheme.whiteColor());
                        mutuelle = new MyLabel(currentTheme,"CIMR",16,0);
                        mutuelle.changeColor(currentTheme.whiteColor());
                    patientInof.add(cin);
                    patientInof.add(nom_et_prenom);
                    patientInof.add(email);
                    patientInof.add(telephone);
                    patientInof.add(adresse);
                    patientInof.add(sexe);
                    patientInof.add(mutuelle);

                    JPanel footerPanel = new JPanel(new BorderLayout());
                    footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
                    footerPanel.setOpaque(false);
                        MyButton saveBtn = new MyButton(currentTheme,"Ajouter",null,currentTheme.whiteColor(),currentTheme.darkBgColor());
                        saveBtn.setForeground(currentTheme.greenColor());
                        saveBtn.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                ajouter();
                            }
                        });
                    footerPanel.add(saveBtn, BorderLayout.CENTER);

                patientInfoPanel.add(pfpPanel, BorderLayout.NORTH);
                patientInfoPanel.add(patientInof, BorderLayout.CENTER);
                patientInfoPanel.add(footerPanel, BorderLayout.SOUTH);

                JPanel formContainerPanel = new JPanel(new GridLayout(6,2));
                formContainerPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
                formContainerPanel.setBackground(currentTheme.bgColor());
                    //ID|NOM|PRENOM|CIN|ADRESSE|TELEPHONE|EMAIL|PFP|DATA_NAISSANCE|SEXE|GROUP_SANGUIN|MUTUELLE|PROFESSION|DOSSIERMEDICALE
                     nomFormGroup = new FormGroup(currentTheme,"Nom","src/main/resources/images/form/nom.png");
                    formContainerPanel.add(nomFormGroup);
                    prenomFormGroup = new FormGroup(currentTheme,"Prenom","src/main/resources/images/form/prenom.png");
                    formContainerPanel.add(prenomFormGroup);

                    updateText(nomFormGroup,prenomFormGroup,nom_et_prenom);

                     emailFormGroup = new FormGroup(currentTheme,"Email","src/main/resources/images/form/email.png");
                    formContainerPanel.add(emailFormGroup);
                    updateText(emailFormGroup,email);
                     telephoneFormGroup = new FormGroup(currentTheme,"Telephone","src/main/resources/images/form/telephone.png");
                    formContainerPanel.add(telephoneFormGroup);
                    updateText(telephoneFormGroup,telephone);
                     addressFormGroup = new FormGroup(currentTheme,"Adresse","src/main/resources/images/form/address.png");
                    formContainerPanel.add(addressFormGroup);
                    updateText(addressFormGroup,adresse);
                     dateNaissanceFormGroup = new FormGroup(currentTheme,"Date de naissance","src/main/resources/images/form/data_de_naissance.png");
                    formContainerPanel.add(dateNaissanceFormGroup);
                     assuranceFormGroup = new SelectFormGroup(currentTheme,"Assurance","src/main/resources/images/form/assurance.png",new String[]{"CIMR","CNOPS","CNSS","Autre"});
                    formContainerPanel.add(assuranceFormGroup);
                    updateText(assuranceFormGroup,mutuelle);
                     cinFormGroup = new FormGroup(currentTheme,"CIN","src/main/resources/images/form/cin.png");
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

    private void updatePicturName(){
        // Source file
        File sourceFile = new File("src/main/resources/images/patient_pfp/uploaded_picture.jpg");

        // Destination directory
        File destinationDir = new File("src/main/resources/images/patient_pfp");
        if (!destinationDir.exists()) {
            destinationDir.mkdirs(); // Create directory if it doesn't exist
        }

        // New file name
        String newFileName = getPfpPath();
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

    private void ajouter(){
        if(nomFormGroup.getInputField().getText().isEmpty()) {
            errorMessageLabel.setText("Le nom est requis");
            return;
        }
        if(prenomFormGroup.getInputField().getText().isEmpty()) {
            errorMessageLabel.setText("Le prenom est requis");
            return;
        }
        if(emailFormGroup.getInputField().getText().isEmpty()) {
            errorMessageLabel.setText("L'email est requis");
            return;
        }
        if(telephoneFormGroup.getInputField().getText().isEmpty()) {
            errorMessageLabel.setText("Le Telephone est requis");
            return;
        }
        if(addressFormGroup.getInputField().getText().isEmpty()) {
            errorMessageLabel.setText("Le adresse est requis");
            return;
        }
        if(dateNaissanceFormGroup.getInputField().getText().isEmpty()) {
            errorMessageLabel.setText("Le Date de naissance est requis");
            return;
        }
        if(cinFormGroup.getInputField().getText().isEmpty()) {
            errorMessageLabel.setText("Le CIN est requis");
            return;
        }

        errorMessageLabel.setText("");
        updatePicturName();

        parentPanel.removeAll();
        parentPanel.revalidate();
        parentPanel.repaint();
        parentPanel.add(AppFactory.getPatientController().showAllPatients());
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

    public AjouterPatientView(Theme currentTheme, JPanel parent) {
        this.currentTheme = currentTheme;
        this.parentPanel = parent;
        _init();
    }
}
