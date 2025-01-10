package ma.CabinetDentaire.presentation.view;

import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class DossierMedicalView extends JPanel {
    private Theme currentTheme;

    private void _init(){
        setBackground(Color.blue);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.PINK);

        JPanel avatarInfoPanel = new JPanel(new BorderLayout());
        avatarInfoPanel.setBackground(Color.green);

        JPanel avatarPanel = new JPanel(new GridLayout(3,1));
        MyLabel idLabel = new MyLabel(currentTheme,"#ID",10,1);
        JLabel pfpLabel = new JLabel();
        pfpLabel.setBackground(Color.blue);
        pfpLabel.setPreferredSize(new Dimension(100,100));
        MyLabel activeLabel = new MyLabel(currentTheme,"ACTIVE",12,1);
        avatarPanel.add(idLabel);
        avatarPanel.add(pfpLabel);
        avatarPanel.add(activeLabel);

        JPanel infoPanel = new JPanel(new GridLayout(7,1));
        MyLabel cinLabel = new MyLabel(currentTheme,"CIN",10,1);
        infoPanel.add(cinLabel);
        MyLabel nomLabel = new MyLabel(currentTheme,"Nom",14,1);
        infoPanel.add(nomLabel);
        MyLabel prenomLabel = new MyLabel(currentTheme,"Prenom",12,0);
        infoPanel.add(prenomLabel);
        MyLabel emailLabel = new MyLabel(currentTheme,"Email",12,0);
        infoPanel.add(emailLabel);
        MyLabel telephone = new MyLabel(currentTheme,"Telephone",12,0);
        infoPanel.add(telephone);
        MyLabel addressLabel = new MyLabel(currentTheme,"Address",12,0);
        infoPanel.add(addressLabel);
        MyLabel mutuelle = new MyLabel(currentTheme,"Mutuelle",12,0);
        infoPanel.add(mutuelle);
        MyLabel sexe =  new MyLabel(currentTheme,"Sexe",12,0);
        infoPanel.add(sexe);

        avatarInfoPanel.add(avatarPanel, BorderLayout.LINE_START);
        avatarInfoPanel.add(infoPanel, BorderLayout.LINE_END);
        // ------------ BODY PANEL ---------------
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.red);

        add(headerPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);
    }

    public DossierMedicalView(Theme currentTheme) {
        this.currentTheme = currentTheme;
        _init();
    }
}
