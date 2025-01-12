package ma.CabinetDentaire.presentation.view.patient;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.Patient;
import ma.CabinetDentaire.entities.enums.Sexe;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.palette.panels.LayerPaneContainer;
import ma.CabinetDentaire.presentation.view.palette.Table.CustomTableCol;
import ma.CabinetDentaire.presentation.view.palette.Table.CustomTableHeader;
import ma.CabinetDentaire.presentation.view.palette.panels.patient_panels.PatientFooter;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class PatientView extends JPanel {
    private Theme currentTheme;
    private List<Patient> patients;
    private int patientsHommeCount;
    private int nbrPatientEnfant;
    private MyButton next, prev;

    private JPanel mainPanel;
    private final AjouterPatientView ajouterPatientPanel;

    private JPanel tablePanel;
    private int nbrPatientToDisplay;
    private int lastElementIndex;
    private int pagesNumber;
    private int currentPage;

    public void _init() {
        setLayout(new BorderLayout());
        setOpaque(false);

        LayerPaneContainer layerPaneContainer = new LayerPaneContainer(currentTheme);


        mainPanel = initMainPanel();


        mainPanel.setBounds(0, 70, 1066, 698);

        layerPaneContainer.add(mainPanel, JLayeredPane.DEFAULT_LAYER);

        add(layerPaneContainer, BorderLayout.CENTER);
    }

    private JPanel initMainPanel(){
        initPagination();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);


        //mainPanel.add(searchBar, BorderLayout.NORTH);

        //add(mainPanel, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

        JPanel contentHeader = new JPanel(new BorderLayout());
        contentHeader.setOpaque(false);

        MyLabel title = new MyLabel(currentTheme,"Patients",24,1);

        //JPanel CRUDButtons = new JPanel();
        //CRUDButtons.setOpaque(false);

        //CRUDButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        MyButton createBtn = new MyButton(currentTheme,"src/main/resources/images/add_patient.png");
        createBtn.setOpaque(false);

        createBtn.changeMargin(10,15,10,15);

        createBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                updatePanel(ajouterPatientPanel);
            }
        });

        //CRUDButtons.add(createBtn);

        contentHeader.add(title, BorderLayout.LINE_START);
        contentHeader.add(createBtn, BorderLayout.LINE_END);

        content.add(contentHeader, BorderLayout.NORTH);

        tablePanel = new JPanel(new GridLayout(nbrPatientToDisplay + 1,1));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        tablePanel.setOpaque(false);

        displayPatients();

        content.add(tablePanel, BorderLayout.CENTER);

        JPanel patientPagenation = new JPanel(new BorderLayout());
        next = new MyButton(currentTheme,"src/main/resources/images/right-arrow.png"); next.changeImageSize(32); next.setOpaque(false);
        prev = new MyButton(currentTheme,"src/main/resources/images/left-arrow.png"); prev.changeImageSize(32); prev.setOpaque(false);


        next.changeMargin(1,0,1,0);
        prev.changeMargin(1,0,1,0);

        next.setPreferredSize(new Dimension(40,40));
        prev.setPreferredSize(new Dimension(40,40));

        prev.setVisible(false);

        next.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentPage++;

                prev.setVisible(true);

                lastElementIndex += nbrPatientToDisplay;

                if(currentPage == pagesNumber){
                    requestFocusInWindow();
                    next.setVisible(false);
                }

                displayPatients();
            }
        });

        prev.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentPage--;

                next.setVisible(true);

                lastElementIndex -= nbrPatientToDisplay;

                if(currentPage == 1){
                    requestFocusInWindow();
                    prev.setVisible(false);
                }

                displayPatients();
            }
        });

        patientPagenation.add(next, BorderLayout.LINE_END);
        patientPagenation.add(prev, BorderLayout.LINE_START);

        patientPagenation.setBackground(currentTheme.fieldsBgColor());

        patientPagenation.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10) );

        content.add(patientPagenation, BorderLayout.SOUTH);

        mainPanel.add(content, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new GridLayout(1,4,15,0));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        footerPanel.setOpaque(false);

        PatientFooter nbrDePatientsHomme = new PatientFooter(currentTheme,"Nbr de Patients Homme",currentTheme.greenColor(),patientsHommeCount);
        PatientFooter nbrDePatientsFemme = new PatientFooter(currentTheme,"Nbr de Patients Femme",currentTheme.greenColor(),(patients.size() - patientsHommeCount));
        PatientFooter nbrDePatientsEnfant = new PatientFooter(currentTheme,"Nbr de Patients Enfant",currentTheme.greenColor(),nbrPatientEnfant);
        PatientFooter nbrDePatients = new PatientFooter(currentTheme,"Nbr de Patients",currentTheme.greenColor(),patients.size());

        footerPanel.add(nbrDePatientsHomme);
        footerPanel.add(nbrDePatientsFemme);
        footerPanel.add(nbrDePatientsEnfant);
        footerPanel.add(nbrDePatients);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private void displayPatients(){
        tablePanel.removeAll();
        tablePanel.revalidate();
        tablePanel.repaint();

        String[] colsName = {"","Nom","Prenom", "Telephone","Email","Sexe","Age"};
        CustomTableHeader customTableHeader = new CustomTableHeader(currentTheme, colsName);
        tablePanel.add(customTableHeader);

        // Calculate the correct end index
        int endIndex = Math.min(lastElementIndex + nbrPatientToDisplay, patients.size());  // Ensure it doesn't go out of bounds

        for (int i = lastElementIndex; i < endIndex; i++) {
            CustomTableCol customTableCol = new CustomTableCol(currentTheme, new String[]{
                    patients.get(i).getPhotoDeProfile(),
                    patients.get(i).getNom(),
                    patients.get(i).getPrenom(),
                    patients.get(i).getTelephone(),
                    patients.get(i).getEmail(),
                    (patients.get(i).getSexe().equals(Sexe.FEMME) ? "Femme" : "Homme"),
                    Period.between(patients.get(i).getDataDeNaissance(), LocalDate.now()).getYears() + "",
            });
            int finalI = i;
            customTableCol.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    updatePanel(AppFactory.getDossierMedicalController().showPatientDossierMedicale(patients.get(finalI).getId()));
                }
            });
            tablePanel.add(customTableCol);
        }
    }

    private void updatePatients(List<Patient> filteredPatients){
        this.patients = filteredPatients;
        initPagination();
        displayPatients();
    }

    private void filterPatients(String keyword) {
        List<Patient> filteredPatients = AppFactory.getPatientController().filterByName(keyword.toLowerCase());
        updatePatients(filteredPatients);
    }



    private void initPagination(){
        this.nbrPatientToDisplay = 5;
        this.lastElementIndex = 0;
        this.pagesNumber = (int) (patients.size() / nbrPatientToDisplay); // Calculate the integer part.
        double remainder = (double) patients.size() / nbrPatientToDisplay - pagesNumber; // Get the decimal part.
        if (remainder > 0.001) pagesNumber++;
        this.currentPage = 1;
    }

    public PatientView(Theme currentTheme, List<Patient> patients, int nbrPatientsHomme, int nbrPatientEnfant ){
        this.currentTheme = currentTheme;
        this.patients = patients;
        this.patientsHommeCount = nbrPatientsHomme;
        this.nbrPatientEnfant = nbrPatientEnfant;

        this.ajouterPatientPanel = new AjouterPatientView(currentTheme, this);
        _init();
    }

    private void updatePanel(JPanel panel) {
        removeAll();
        revalidate();
        repaint();
        add(panel);
    }

    public AjouterPatientView getAjouterPatientPanel() {
        return ajouterPatientPanel;
    }
}
