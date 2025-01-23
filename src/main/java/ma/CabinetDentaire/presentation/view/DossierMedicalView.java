package ma.CabinetDentaire.presentation.view;

import ma.CabinetDentaire.config.AppFactory;
import ma.CabinetDentaire.entities.*;
import ma.CabinetDentaire.presentation.view.dossier_medical.ConsultationPanel;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.form.FormGroup;
import ma.CabinetDentaire.presentation.view.palette.form.SelectFormGroup;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.util.RoundedLabelUtils;
import ma.CabinetDentaire.repository.exceptions.DaoException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DossierMedicalView extends JPanel {
    private Theme currentTheme;
    private DossierMedicale dossierMedicale;
    private Patient patient;
    private List<RendezVous> rendezVousList;
    private List<Consultation> consultationList;
    static JPanel panelsContainer;

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
        ImageIcon icon = new ImageIcon("src/main/resources/images/patient_pfp/" + patient.getPhotoDeProfile());
        try{
            icon = RoundedLabelUtils.makeImageRounded("src/main/resources/images/patient_pfp/" + patient.getPhotoDeProfile());
        } catch (IOException e1) {
            System.out.println(e1.getMessage());
        }
        icon.setImage(icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH));
        JLabel pfpLabel = new JLabel(icon, SwingConstants.CENTER); pfpLabel.setPreferredSize(new Dimension(128,128));  pfpLabel.setOpaque(false);
        RoundedLabelUtils.makeRounded(pfpLabel, 128);
        avatarPanel.add(idLabel, BorderLayout.NORTH);
        avatarPanel.add(pfpLabel, BorderLayout.CENTER);
        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(7,1));
        infoPanel.setBorder(new EmptyBorder(0, 25, 0, 0));
        infoPanel.setOpaque(false);
        MyLabel cinLabel = new MyLabel(currentTheme,patient.getCin(),10,1,"src/main/resources/images/form/cin.png",20);
        infoPanel.add(cinLabel);
        MyLabel nomEtPrenomLabel = new MyLabel(currentTheme,patient.getNom() + " " + patient.getPrenom(),18,1 ,"src/main/resources/images/form/prenom.png",20);
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

        JPanel bodyContentPanel = new JPanel(new BorderLayout());
        bodyContentPanel.setBackground(currentTheme.bgColor());

        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(currentTheme.greenColor());
        navPanel.setPreferredSize(new Dimension(64, 100));

        MyLabel rendezVousIcon = new MyLabel(currentTheme, "src/main/resources/images/icons/white_rdv.png", 32);
        MyLabel acteIcon = new MyLabel(currentTheme, "src/main/resources/images/icons/acte.png", 32);
        MyLabel consultationIcon = new MyLabel(currentTheme, "src/main/resources/images/icons/white_consultation.png", 32);
        MyLabel ordonanceIcon = new MyLabel(currentTheme, "src/main/resources/images/icons/white_ordonnance.png",32);
        MyLabel situationFinancierIcon = new MyLabel(currentTheme, "src/main/resources/images/icons/white_situation_financier.png", 32);

        rendezVousIcon.setAlignmentX(Component.CENTER_ALIGNMENT); rendezVousIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        acteIcon.setAlignmentX(Component.CENTER_ALIGNMENT); acteIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        consultationIcon.setAlignmentX(Component.CENTER_ALIGNMENT); consultationIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ordonanceIcon.setAlignmentX(Component.CENTER_ALIGNMENT); ordonanceIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        situationFinancierIcon.setAlignmentX(Component.CENTER_ALIGNMENT); situationFinancierIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rendezVousIcon.setToolTipText("Rendez vous");
        acteIcon.setToolTipText("Acte");
        consultationIcon.setToolTipText("Consultation");
        ordonanceIcon.setToolTipText("Ordonance");
        situationFinancierIcon.setToolTipText("Situation Financière");

        panelsContainer = new JPanel(new BorderLayout());
        panelsContainer.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelsContainer.setOpaque(false);
        updateBodyContentPanel(panelsContainer,rendezVousPanel());

        rendezVousIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateBodyContentPanel(panelsContainer,rendezVousPanel());
            }
        });
        acteIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getActeController().findAll();
            }
        });
        consultationIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consultationList = AppFactory.getConsultationController().filterByDossier(dossierMedicale.getId());
                updateBodyContentPanel(panelsContainer,new ConsultationPanel(currentTheme,consultationList));
            }
        });
        ordonanceIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFactory.getOrdonnanceController().findAllByDossierId(dossierMedicale.getId());
            }
        });
        situationFinancierIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    updateBodyContentPanel(panelsContainer,situationFinancierPanel());
                } catch (DaoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //navPanel.add(Box.createVerticalGlue());
        navPanel.add(Box.createVerticalStrut(25));
        navPanel.add(rendezVousIcon);
        navPanel.add(Box.createVerticalStrut(25));
        navPanel.add(acteIcon);
        navPanel.add(Box.createVerticalStrut(25));
        navPanel.add(consultationIcon);
        navPanel.add(Box.createVerticalStrut(25));
        navPanel.add(ordonanceIcon);
        navPanel.add(Box.createVerticalStrut(25));
        navPanel.add(situationFinancierIcon);
        navPanel.add(Box.createVerticalGlue());

        bodyContentPanel.add(navPanel,BorderLayout.LINE_START);
        bodyContentPanel.add(panelsContainer,BorderLayout.CENTER);

        bodyPanel.add(bodyContentPanel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);
    }

    public static void updateBodyContentPanel(JPanel prentPanel, JPanel panel){
        prentPanel.removeAll();
        prentPanel.revalidate();
        prentPanel.repaint();
        prentPanel.add(panel, BorderLayout.CENTER);
    }

    public static void updateBodyContentPanel(JPanel panel){
        panelsContainer.removeAll();
        panelsContainer.revalidate();
        panelsContainer.repaint();
        panelsContainer.add(panel, BorderLayout.CENTER);
    }

    private JPanel rendezVousPanel(){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel headerMainPanel = new JPanel(new BorderLayout());
        headerMainPanel.setOpaque(false);
        MyLabel title = new MyLabel(currentTheme,"Les Rendez-vous",22,1);
        headerMainPanel.add(title, BorderLayout.WEST);
        MyLabel addRendezVousPanelBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/add_rendezVous_btn.png",32);
        addRendezVousPanelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        headerMainPanel.add(addRendezVousPanelBtn, BorderLayout.EAST);

        mainPanel.add(headerMainPanel,BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        bodyPanel.setOpaque(false);
        JPanel sideBodyContent = new JPanel(new BorderLayout());
        sideBodyContent.setOpaque(false);
        sideBodyContent.setBorder(new EmptyBorder(0, 20, 0, 0));
        sideBodyContent.setPreferredSize(new Dimension(400, 100));
        sideBodyContent.setVisible(false);
        JPanel mainBodyContent = new JPanel(new GridLayout(7,1,0,10));
        mainBodyContent.setOpaque(false);

        addRendezVousPanelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sideBodyContent.setPreferredSize(new Dimension(400, 100));
                sideBodyContent.setVisible(true);

                // ===========================================================================
                JPanel formPanel = new JPanel(new GridLayout(5,1));
                formPanel.setOpaque(false);
                FormGroup dateFormGroup = new FormGroup(currentTheme,"Date","src/main/resources/images/icons/date.png","YYYY-MM-DD");
                formPanel.add(dateFormGroup);
                FormGroup timeFormGroup = new FormGroup(currentTheme,"Temp","src/main/resources/images/icons/time.png","HH:MM");
                formPanel.add(timeFormGroup);
                SelectFormGroup typeFormGroup = new SelectFormGroup(currentTheme,"Type","src/main/resources/images/edit_btn.png",new String[]{"PRIORITAIRE","NORMAL","VIP","URGENT","POUR_CONSEIL","CONTROLE"});
                formPanel.add(typeFormGroup);
                FormGroup motifFormGroup = new FormGroup(currentTheme,"Motif","src/main/resources/images/icons/motif_icon.png","motif de rendez-vous");
                formPanel.add(motifFormGroup);

                JPanel buttonsPanel = new JPanel(new BorderLayout());
                buttonsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
                buttonsPanel.setOpaque(false);
                MyButton ajouterRendezVousBtn = new MyButton(currentTheme,"Ajouter",18,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/icons/edit_btn.png",21);
                ajouterRendezVousBtn.changeMargin(0,20,0,20);

                //String motif, LocalTime temps, Long dossierId, TypeRDV typeRDV, LocalDate dateRDV
                ajouterRendezVousBtn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        AppFactory.getRendezVousController()
                                .createRendezVous(motifFormGroup.getInputField().getText(),
                                                  timeFormGroup.getInputField().getText(),
                                                  dossierMedicale.getId(),
                                        (String) typeFormGroup.getSelectField().getSelectedItem(),
                                                  dateFormGroup.getInputField().getText());
                    }
                });

                buttonsPanel.add(ajouterRendezVousBtn,BorderLayout.WEST);
                MyLabel clearBtn = new MyLabel(currentTheme,"src/main/resources/images/refresh.png",38);
                clearBtn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        motifFormGroup.getInputField().setText("");
                        timeFormGroup.getInputField().setText("");
                        dateFormGroup.getInputField().setText("");
                    }
                });
                clearBtn.setOpaque(false);
                buttonsPanel.add(clearBtn,BorderLayout.EAST);
                formPanel.add(buttonsPanel);
                // ===========================================================================

                updateBodyContentPanel(sideBodyContent,formPanel);
            }
        });

        // Content==========================================
        JPanel headerTablePanel = new JPanel(new GridLayout(1,5));
        headerTablePanel.setBackground(currentTheme.greenColor());
        // Header Content ----------------------------------
        headerTablePanel.add(new Label());
        MyLabel dateColLabel = new MyLabel(currentTheme,"Date",18,1);
        headerTablePanel.add(dateColLabel);
        MyLabel tempColLabel = new MyLabel(currentTheme,"Temp",18,1);
        headerTablePanel.add(tempColLabel);
        MyLabel typeColLabel = new MyLabel(currentTheme,"Type",18,1);
        headerTablePanel.add(typeColLabel);
        MyLabel motifColLabel = new MyLabel(currentTheme,"Motif",18,1);
        headerTablePanel.add(motifColLabel);

        // -------------------------------------------------
        mainBodyContent.add(headerTablePanel);
        // Col Content ----------------------------------
        rendezVousList.forEach(rendezVous -> {
            boolean isRendezVousHaveConsultation = rendezVous.getConsultation() != null;


            JPanel colTablePanel = new JPanel(new GridLayout(1,5));

            if(!isRendezVousHaveConsultation){
                colTablePanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()), // Green bottom border
                        BorderFactory.createEmptyBorder(0, 0, 0, 0) // Padding
                ));

                colTablePanel.setBackground(currentTheme.fieldsBgColor());
            } else {
                colTablePanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.blueHoverColor()), // Green bottom border
                        BorderFactory.createEmptyBorder(0, 0, 0, 0) // Padding
                ));

                colTablePanel.setBackground(currentTheme.darkBgColor());
            }

            colTablePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            colTablePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    sideBodyContent.setPreferredSize(new Dimension(300, 100));
                    sideBodyContent.setVisible(true);
                    JPanel rendezvousContent = new JPanel(new BorderLayout());
                    rendezvousContent.setBorder(new EmptyBorder(15, 0, 0, 0));
                    rendezvousContent.setOpaque(false);
                    JPanel headerContent = new JPanel(new BorderLayout());
                    headerContent.setOpaque(false);
                    MyLabel headerContentTitleLabel = new MyLabel(currentTheme,"Rendez-vous #" + rendezVous.getId(),20,1,currentTheme.greenColor());
                    MyLabel closeSidePanelBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/back.png",28);
                    closeSidePanelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    closeSidePanelBtn.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            sideBodyContent.setVisible(false);
                        }
                    });
                    headerContent.add(headerContentTitleLabel,BorderLayout.WEST);
                    headerContent.add(closeSidePanelBtn,BorderLayout.EAST);
                    rendezvousContent.add(headerContent, BorderLayout.NORTH);

                    JPanel contentPanel = new JPanel(new GridLayout(7,1));
                    contentPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
                    contentPanel.setOpaque(false);
                    MyLabel dateDeRendezVous = new MyLabel(currentTheme,"Date du rendez-vous : " + rendezVous.getDateRDV().toString(),16,0);
                    contentPanel.add(dateDeRendezVous);
                    MyLabel tempDeRendezVous = new MyLabel(currentTheme, "Heure du rendez-vous : "+ rendezVous.getTemps().toString(),16,0);
                    contentPanel.add(tempDeRendezVous);
                    MyLabel typeDeRendezVous = new MyLabel(currentTheme, "Type de rendez-vous : "+ rendezVous.getTypeRDV().toString(),16,0);
                    contentPanel.add(typeDeRendezVous);
                    MyLabel motifDeRendezVous = new MyLabel(currentTheme, "Motif : \n"+ rendezVous.getMotif(),16,0);
                    contentPanel.add(motifDeRendezVous);

                    JPanel buttonsPanel = new JPanel(new BorderLayout());
                    buttonsPanel.setOpaque(false);

                    MyButton createConsultationButton = new MyButton(currentTheme,"Consultation",16,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/icons/white_add.png",20);
                    createConsultationButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            updateBodyContentPanel(panelsContainer,new ConsultationPanel(currentTheme).creerConsultationDeRendezVous(rendezVous));
                        }
                    });

                    if(!isRendezVousHaveConsultation)
                        buttonsPanel.add(createConsultationButton, BorderLayout.WEST);

                    JPanel rightButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    rightButtonsPanel.setOpaque(false);
                    MyLabel updateRendezVousBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/green_edit.png",32);
                    updateRendezVousBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    rightButtonsPanel.add(updateRendezVousBtn);

                    updateRendezVousBtn.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            sideBodyContent.setPreferredSize(new Dimension(400, 100));
                            sideBodyContent.setVisible(true);

                            // ===========================================================================
                            JPanel formPanel = new JPanel(new GridLayout(5,1));
                            formPanel.setOpaque(false);
                            FormGroup dateFormGroup = new FormGroup(currentTheme,"Date","src/main/resources/images/icons/date.png",rendezVous.getDateRDV().toString());
                            formPanel.add(dateFormGroup);
                            FormGroup timeFormGroup = new FormGroup(currentTheme,"Temp","src/main/resources/images/icons/time.png",rendezVous.getTemps().toString());
                            formPanel.add(timeFormGroup);
                            SelectFormGroup typeFormGroup = new SelectFormGroup(currentTheme,"Type","src/main/resources/images/edit_btn.png",new String[]{"PRIORITAIRE","NORMAL","VIP","URGENT","POUR_CONSEIL","CONTROLE"});
                            formPanel.add(typeFormGroup);
                            FormGroup motifFormGroup = new FormGroup(currentTheme,"Motif","src/main/resources/images/icons/motif_icon.png",rendezVous.getMotif());
                            formPanel.add(motifFormGroup);

                            JPanel buttonsPanel = new JPanel(new BorderLayout());
                            buttonsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
                            buttonsPanel.setOpaque(false);
                            MyButton ajouterRendezVousBtn = new MyButton(currentTheme,"Modifier",18,currentTheme.greenColor(),currentTheme.greenHoverColor(),"src/main/resources/images/icons/edit_btn.png",21);
                            ajouterRendezVousBtn.changeMargin(0,20,0,20);

                            //String motif, LocalTime temps, Long dossierId, TypeRDV typeRDV, LocalDate dateRDV
                            ajouterRendezVousBtn.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    AppFactory.getRendezVousController()
                                            .updateRendezVous(rendezVous.getId(),motifFormGroup.getInputField().getText(),
                                                    timeFormGroup.getInputField().getText(),
                                                    dossierMedicale.getId(),
                                                    (String) typeFormGroup.getSelectField().getSelectedItem(),
                                                    dateFormGroup.getInputField().getText());
                                }
                            });

                            buttonsPanel.add(ajouterRendezVousBtn,BorderLayout.WEST);
                            MyLabel clearBtn = new MyLabel(currentTheme,"src/main/resources/images/refresh.png",38);
                            clearBtn.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    motifFormGroup.getInputField().setText("");
                                    timeFormGroup.getInputField().setText("");
                                    dateFormGroup.getInputField().setText("");
                                }
                            });
                            clearBtn.setOpaque(false);
                            buttonsPanel.add(clearBtn,BorderLayout.EAST);
                            formPanel.add(buttonsPanel);
                            // ===========================================================================

                            updateBodyContentPanel(sideBodyContent,formPanel);
                        }
                    });

                    MyLabel deleteRendezVousBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/red_delete.png",32);
                    deleteRendezVousBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    deleteRendezVousBtn.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            AppFactory.getRendezVousController().deleteRendezVousById(rendezVous.getId(),dossierMedicale.getId());
                        }
                    });
                    rightButtonsPanel.add(deleteRendezVousBtn);
                    buttonsPanel.add(rightButtonsPanel,BorderLayout.EAST);

                    rendezvousContent.add(contentPanel, BorderLayout.CENTER);
                    rendezvousContent.add(buttonsPanel, BorderLayout.SOUTH);
                    updateBodyContentPanel(sideBodyContent,rendezvousContent);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if(isRendezVousHaveConsultation)
                        return;
                    colTablePanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.blueColor()), // Green bottom border
                            BorderFactory.createEmptyBorder(0, 0, 0, 0) // Padding
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(isRendezVousHaveConsultation)
                        return;
                    colTablePanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()), // Green bottom border
                            BorderFactory.createEmptyBorder(0, 0, 0, 0) // Padding
                    ));
                }
            });
            MyLabel rdv_icon = new MyLabel(currentTheme,"src/main/resources/images/icons/schedule.png",32);
            rdv_icon.setBorder(new EmptyBorder(0, 15, 0, 0));
            colTablePanel.add(rdv_icon);
            MyLabel rdv_dateColLabel = new MyLabel(currentTheme,rendezVous.getDateRDV().toString(),16,0);
            colTablePanel.add(rdv_dateColLabel);
            MyLabel rdv_tempColLabel = new MyLabel(currentTheme,rendezVous.getTemps().toString(),16,0);
            colTablePanel.add(rdv_tempColLabel);
            MyLabel rdv_typeColLabel = new MyLabel(currentTheme,rendezVous.getTypeRDV().toString(),16,0);
            colTablePanel.add(rdv_typeColLabel);
            MyLabel rdv_motifColLabel = new MyLabel(currentTheme,rendezVous.getMotif(),16,0);
            colTablePanel.add(rdv_motifColLabel);
            mainBodyContent.add(colTablePanel);
        });
        // -------------------------------------------------
        // =================================================

        bodyPanel.add(mainBodyContent, BorderLayout.CENTER);
        bodyPanel.add(sideBodyContent, BorderLayout.EAST);

        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel situationFinancierPanel() throws DaoException {
        SituationFinanciere situationFinanciere = AppFactory.getSituationRepo().findByDossierMedicale(dossierMedicale.getId());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        MyLabel title = new MyLabel(currentTheme,"Situation Financier",22,1);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);

        JPanel mainBodyContent = new JPanel(new GridLayout(3,1));
        mainBodyContent.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));
        mainBodyContent.setOpaque(false);

        AtomicReference<Double> montantTotal = new AtomicReference<>(0d);
        AtomicReference<Double> montantTotalPaye = new AtomicReference<>(0d);
        AtomicReference<Double> montantTotalRest = new AtomicReference<>(0d);

        if(!AppFactory.getFactureRepo().findBySituationFinancie(situationFinanciere.getId()).isEmpty()){
            AppFactory.getFactureRepo().findBySituationFinancie(situationFinanciere.getId()).forEach(facture -> {
                montantTotalPaye.updateAndGet(v -> v + facture.getMontantPaye());
                montantTotal.updateAndGet(v -> v + facture.getMontantTotal());
                montantTotalRest.updateAndGet(v -> v + facture.getMontantRestant());
            });
        }

        MyLabel MontantTotalLabel = new MyLabel(currentTheme,"Montant Total: " + montantTotal,18,0);
        MyLabel montantTotalPayeLabel = new MyLabel(currentTheme,"Montant Total Paye: "+montantTotalPaye,18,0);
        MyLabel montantTotalRestLabel = new MyLabel(currentTheme,"Montant Total Restant: "+montantTotalRest,18,0);

        mainBodyContent.add(MontantTotalLabel);
        mainBodyContent.add(montantTotalPayeLabel);
        mainBodyContent.add(montantTotalRestLabel);

        bodyPanel.add(mainBodyContent, BorderLayout.NORTH);

        JPanel mainFactureBodyContent = new JPanel(new GridLayout(5,1,0,10));
        mainFactureBodyContent.setOpaque(false);
        mainFactureBodyContent.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        //ID|MONTANT_TOTAL|MONTANT_RESTANT|MONTANT_PAYE|DATE_FACTURATION|SITUATION_FINANCIERE_ID|TYPE_PAIEMENT|CONSULTATION_ID
        JPanel headerTablePanel = new JPanel(new GridLayout(1,6));
        headerTablePanel.setBackground(currentTheme.greenColor());
        headerTablePanel.add(new Label());  // ID
        MyLabel montantTotaleColLabel = new MyLabel(currentTheme,"totale",18,1);
        headerTablePanel.add(montantTotaleColLabel);
        MyLabel montantRestantColLabel = new MyLabel(currentTheme,"Restant",18,1);
        headerTablePanel.add(montantRestantColLabel);
        MyLabel montantPayeColLabel = new MyLabel(currentTheme,"Paye",18,1);
        headerTablePanel.add(montantPayeColLabel);
        MyLabel dateColLabel = new MyLabel(currentTheme,"Date",18,1);
        headerTablePanel.add(dateColLabel);
        MyLabel typeColLabel = new MyLabel(currentTheme,"Type",18,1);
        headerTablePanel.add(typeColLabel);
        headerTablePanel.add(new Label());
        mainFactureBodyContent.add(headerTablePanel);

        AppFactory.getFactureRepo().findBySituationFinancie(situationFinanciere.getId()).forEach(facture -> {
            JPanel colTablePanel = new JPanel(new GridLayout(1,8));
            colTablePanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 3, 0, currentTheme.greenColor()), // Green bottom border
                    BorderFactory.createEmptyBorder(0, 0, 0, 0) // Padding
            ));
            colTablePanel.setBackground(currentTheme.fieldsBgColor());

            MyLabel interventionMedecinID = new MyLabel( currentTheme,"#" + facture.getId(),16,0);
            interventionMedecinID.setBorder(new EmptyBorder(0, 15, 0, 0));
            colTablePanel.add(interventionMedecinID);
            MyLabel interventionMedecinActeLibelle = new MyLabel(currentTheme,facture.getMontantTotal().toString(),16,0);
            colTablePanel.add(interventionMedecinActeLibelle);
            MyLabel interventionMedecinDent = new MyLabel(currentTheme,facture.getMontantRestant().toString(),16,0);
            colTablePanel.add(interventionMedecinDent);
            MyLabel interventionMedecinPrix = new MyLabel(currentTheme, facture.getMontantPaye().toString(),16,0);
            colTablePanel.add(interventionMedecinPrix);
            MyLabel interventionMedecinNote = new MyLabel(currentTheme, facture.getDataFacturation().toString(),16,0);
            colTablePanel.add(interventionMedecinNote);
            MyLabel typeFacturationLabel = new MyLabel(currentTheme, facture.getTypePaiement().toString(),16,0);
            colTablePanel.add(typeFacturationLabel);

            JPanel actionsButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            actionsButtonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
            actionsButtonContainer.setOpaque(false);
            MyLabel updateBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/green_edit.png",26);
            updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            updateBtn.setBorder(BorderFactory.createEmptyBorder(0,0,0,15));
            updateBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //AppFactory.getInterventionController().updateInterventionPanel(interventionMedecin);
                }
            });
            actionsButtonContainer.add(updateBtn);
            MyLabel deleteBtn = new MyLabel(currentTheme,"src/main/resources/images/icons/red_delete.png",26);
            deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            deleteBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AppFactory.getFactureController().deleteFactureById(facture.getId());
                }
            });
            actionsButtonContainer.add(deleteBtn);
            colTablePanel.add(actionsButtonContainer);

            mainFactureBodyContent.add(colTablePanel);
        });

        bodyPanel.add(mainFactureBodyContent, BorderLayout.CENTER);

        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    public DossierMedicalView(Theme currentTheme, DossierMedicale dossierMedicale) {
        this.currentTheme = currentTheme;
        this.dossierMedicale = dossierMedicale;
        this.patient = dossierMedicale.getPatient();
        this.rendezVousList = dossierMedicale.getRdvs();
        this.consultationList = dossierMedicale.getConsultations();
        _init();
    }
}
