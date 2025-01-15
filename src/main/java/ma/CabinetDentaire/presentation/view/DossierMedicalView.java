package ma.CabinetDentaire.presentation.view;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.DossierMedicale;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.util.RoundedLabelUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DossierMedicalView extends JPanel {
    private Theme currentTheme;
    private DossierMedicale dossierMedicale;
    private Patient patient;

    private void _init(){
        setLayout(new BorderLayout());
        setOpaque(false);

        // ====================== Header Panel ==================================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(15, 30, 25, 30));
        headerPanel.setOpaque(false);

        JPanel headerTitlePanel = new JPanel(new BorderLayout());
        headerTitlePanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        headerTitlePanel.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Dossier Medical",24,1);
        MyButton backBtn = new MyButton(currentTheme,"src/main/resources/images/back_btn.png");
        backBtn.changeMargin(5, 30, 5, 0);
        backBtn.setOpaque(false);
        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainView.updateRightPanel(AppFactory.getPatientController().showAllPatients());
            }
        });
        headerTitlePanel.add(title, BorderLayout.WEST);
        headerTitlePanel.add(backBtn, BorderLayout.EAST);

        // ---------------------- Header Container ------------------------------
        JPanel headerContainer = new JPanel(new BorderLayout());
        headerContainer.setBackground(currentTheme.bgColor());
        headerContainer.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Avatar Info Container
        JPanel avatarInfoContainer = new JPanel(new BorderLayout());
        avatarInfoContainer.setOpaque(false);

        // Avatar Panel
        JPanel avatarPanel = new JPanel(new BorderLayout());
        avatarPanel.setOpaque(false);
        MyLabel idLabel = new MyLabel(currentTheme,"#" + patient.getId(),12,1); idLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        JLabel pfpLabel = new JLabel(); pfpLabel.setPreferredSize(new Dimension(128,128));  pfpLabel.setBackground(Color.blue); pfpLabel.setOpaque(true);
        RoundedLabelUtils.makeRounded(pfpLabel, 128);
        avatarPanel.add(idLabel, BorderLayout.NORTH);
        avatarPanel.add(pfpLabel, BorderLayout.CENTER);
        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(7,1));
        infoPanel.setBorder(new EmptyBorder(0, 25, 0, 0));
        infoPanel.setOpaque(false);
        MyLabel cinLabel = new MyLabel(currentTheme,patient.getCin(),10,1,"src/main/resources/images/form/cin.png",20);
        infoPanel.add(cinLabel);
        MyLabel nomEtPrenomLabel = new MyLabel(currentTheme,patient.getNom() + " " + dossierMedicale.getPatient().getPrenom(),18,1 ,"src/main/resources/images/form/prenom.png",20);
        nomEtPrenomLabel.changeColor(currentTheme.greenColor());
        infoPanel.add(nomEtPrenomLabel);
        MyLabel emailLabel = new MyLabel(currentTheme,patient.getEmail(),12,0,"src/main/resources/images/form/email.png",20);
        infoPanel.add(emailLabel);
        MyLabel telephone = new MyLabel(currentTheme,patient.getTelephone(),12,0,"src/main/resources/images/form/telephone.png",20);
        infoPanel.add(telephone);
        MyLabel addressLabel = new MyLabel(currentTheme,patient.getAdresse(),12,0,"src/main/resources/images/form/address.png",20);
        infoPanel.add(addressLabel);
        MyLabel mutuelle = new MyLabel(currentTheme,patient.getMutuelle().toString(),12,0,"src/main/resources/images/form/assurance.png",20);
        infoPanel.add(mutuelle);
        MyLabel sexe =  new MyLabel(currentTheme,patient.getSexe().toString(),12,0,"src/main/resources/images/form/sexe.png",20);
        infoPanel.add(sexe);

        avatarInfoContainer.add(avatarPanel, BorderLayout.LINE_START);
        avatarInfoContainer.add(infoPanel, BorderLayout.LINE_END);

        // Buttons Dats Container
        JPanel headerLeftContent = new JPanel(new BorderLayout());
        headerLeftContent.setOpaque(false);

        // Buttons Container
        JPanel buttonsContainer = new JPanel(new BorderLayout());
        buttonsContainer.setOpaque(false);
        MyButton editBtn = new MyButton(currentTheme,"Modifier",16,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/edit_user.png",18);
        MyButton deleteBtn = new MyButton(currentTheme,"Supprimer",16,currentTheme.redColor(),currentTheme.redHoverColor(),"src/main/resources/images/delete_user.png",18);
        editBtn.changeMargin(5,5,5,5);
        deleteBtn.changeMargin(5,5,5,5);
        editBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainView.updateRightPanel(AppFactory.getPatientController().showPatientsModifier(patient));
            }
        });
        deleteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getPatientController().deletePatientById(patient.getId());
            }
        });
        buttonsContainer.add(editBtn, BorderLayout.NORTH);
        buttonsContainer.add(deleteBtn, BorderLayout.SOUTH);
        buttonsContainer.setBorder(new EmptyBorder(0, 0, 10, 0));

        MyLabel dateCreation =  new MyLabel(currentTheme,dossierMedicale.getDateCreation().toString(),14,1,"src/main/resources/images/creation_date.png",20);
        dateCreation.setBorder(new EmptyBorder(20, 0, 20, 0));

        headerLeftContent.add(buttonsContainer, BorderLayout.CENTER);
        headerLeftContent.add(dateCreation,BorderLayout.SOUTH);

        headerContainer.add(avatarInfoContainer, BorderLayout.LINE_START);
        headerContainer.add(headerLeftContent, BorderLayout.LINE_END);

        headerPanel.add(headerTitlePanel, BorderLayout.NORTH);
        headerPanel.add(headerContainer, BorderLayout.CENTER);

        // ====================== Body Panel ==================================
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBorder(new EmptyBorder(0, 30, 30, 30));
        bodyPanel.setOpaque(false);

        JPanel bodyContentPanel = new JPanel();
        bodyContentPanel.setBackground(currentTheme.bgColor());

        bodyPanel.add(bodyContentPanel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);
    }

    public DossierMedicalView(Theme currentTheme, DossierMedicale dossierMedicale) {
        this.currentTheme = currentTheme;
        this.dossierMedicale = dossierMedicale;
        this.patient = dossierMedicale.getPatient();

        _init();
    }
}
