package ma.CabinetDentaire.presentation.view;

import ma.CabinetDentaire.presentation.view.palette.panels.UserInfoPanel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private Theme currentTheme;
    private UserInfoPanel userInfoPanel;
    private JPanel leftPanel, rightPanel;

    public void _init(){
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        userInfoPanel = new UserInfoPanel(currentTheme);
        NavPanel navPanel = new NavPanel(currentTheme,rightPanel,this);

        leftPanel.setBackground(currentTheme.bgColor());
        leftPanel.setPreferredSize(new Dimension(300, getHeight()));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        leftPanel.add(userInfoPanel);
        leftPanel.add(navPanel);
        rightPanel.setBackground(currentTheme.darkBgColor());
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(new DashboardPanel(currentTheme));

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    public MainView(Theme currentTheme) {
        this.currentTheme = currentTheme;

        setSize(1280, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);


        _init();

        setVisible(true);
    }
}
