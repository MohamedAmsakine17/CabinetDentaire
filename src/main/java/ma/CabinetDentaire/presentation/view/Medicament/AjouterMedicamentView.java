package ma.CabinetDentaire.presentation.view.Medicament;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.presentation.view.MainView;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.form.FormGroup;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.patient.AjouterPatientView;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.util.RoundedLabelUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AjouterMedicamentView extends JPanel {
    private Theme currentTheme;
    FormGroup nomFormGroup, prixFormGroup, descriptionFormGroup;


    private void _init(){
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));
        headerPanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Ajouter Medicament",24,1);

        MyButton backBtn = new MyButton(currentTheme,"src/main/resources/images/back_btn.png");
        backBtn.setOpaque(false);
        backBtn.changeMargin(5, 30, 5, 0);
        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainView.updateRightPanel(AppFactory.getMedicamentController().showAllMedicaments());
            }
        });

        headerPanel.add(title, BorderLayout.LINE_START);
        headerPanel.add(backBtn, BorderLayout.LINE_END);

        // Body Panel
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(50, 250, 50, 250));

        JPanel formContainerPanel = new JPanel(new BorderLayout());
        formContainerPanel.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));
        formContainerPanel.setBackground(currentTheme.bgColor());

        JPanel formPanel =new JPanel(new GridLayout(4,1));
        formPanel.setOpaque(false);

        JPanel pfpPanel = new JPanel();
        pfpPanel.setOpaque(false);
        pfpPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        ImageIcon icon = new ImageIcon("src/main/resources/images/medicament/default.png");
        try{
            icon = RoundedLabelUtils.makeImageRounded("src/main/resources/images/medicament/default.png");
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

                Component parent = SwingUtilities.getWindowAncestor(AjouterMedicamentView.this);
                int result = fileChooser.showOpenDialog(parent);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        ImageIcon uploadedIcon = new ImageIcon(selectedFile.getAbsolutePath());
                        Image updatedImg = uploadedIcon.getImage().getScaledInstance(128,128, Image.SCALE_SMOOTH);
                        pfplabel.setIcon(new ImageIcon(updatedImg));

                        File outputDir = new File("src/main/resources/images/medicament");
                        if (!outputDir.exists()) outputDir.mkdirs();
                        File outputFile = new File(outputDir, "uploaded_picture.png");
                        BufferedImage uploadedImage = ImageIO.read(selectedFile);
                        ImageIO.write(uploadedImage, "png", outputFile);
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
        pfplabel.setBackground(currentTheme.bgColor());
        pfpPanel.add(pfplabel);

        nomFormGroup= new FormGroup(currentTheme,"Nom","src/main/resources/images/icons/medicament_nom.png","Entrez le nom du médicament");
        prixFormGroup = new FormGroup(currentTheme,"Prix","src/main/resources/images/icons/medicament_prix.png","Entrez le prix du médicament");
        descriptionFormGroup = new FormGroup(currentTheme,"Description","src/main/resources/images/icons/medicament_description.png","Entrez la description du médicament");
        JPanel ajouterButtonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ajouterButtonContainer.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
        ajouterButtonContainer.setOpaque(false);
        MyButton ajouterButton = new MyButton(currentTheme,"Ajouter",currentTheme.greenColor(),currentTheme.greenHoverColor());
        ajouterButtonContainer.add(ajouterButton);
        ajouterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ajouter();
            }
        });

        formPanel.add(nomFormGroup);
        formPanel.add(prixFormGroup);
        formPanel.add(descriptionFormGroup);
        formPanel.add(ajouterButtonContainer);

        formContainerPanel.add(pfpPanel,BorderLayout.NORTH);
        formContainerPanel.add(formPanel,BorderLayout.CENTER);

        bodyPanel.add(formContainerPanel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);
    }

    private void ajouter(){
        String nom = nomFormGroup.getInputField().getText();
        String prix = prixFormGroup.getInputField().getText();
        String description = descriptionFormGroup.getInputField().getText();

        if(nom.isEmpty() || nom.equals("Entrez le nom du médicament")){
            nomFormGroup.getInputField().setBackground(currentTheme.redColor());
            return;
        }

        nomFormGroup.getInputField().setBackground(currentTheme.fieldsBgColor());
        if(prix.isEmpty() || prix.equals("Entrez le prix du médicament")){
            prixFormGroup.getInputField().setBackground(currentTheme.redColor());
            return;
        }

        prixFormGroup.getInputField().setBackground(currentTheme.fieldsBgColor());
        if(description.isEmpty() || description.equals("Entrez la description du médicament")){
            descriptionFormGroup.getInputField().setBackground(currentTheme.redColor());
            return;
        }

        String pictureName = nom.replace(" ","");
        updatePicturName(pictureName);

        //double prix, String nom, String description, String imageSource
        AppFactory.getMedicamentController().createMedicament(Double.parseDouble(prix),nom,description,pictureName);
    }

    private void updatePicturName(String picture_name){
        // Source file
        File sourceFile = new File("src/main/resources/images/medicament/uploaded_picture.png");

        // Destination directory
        File destinationDir = new File("src/main/resources/images/medicament");
        if (!destinationDir.exists()) {
            destinationDir.mkdirs(); // Create directory if it doesn't exist
        }

        // New file name
        String newFileName = picture_name;
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

    public AjouterMedicamentView(Theme currentTheme) {
        this.currentTheme = currentTheme;
        _init();
    }
}
