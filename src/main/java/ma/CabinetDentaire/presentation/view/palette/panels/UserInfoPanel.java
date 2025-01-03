package ma.CabinetDentaire.presentation.view.palette.panels;

import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserInfoPanel extends JPanel {
    private Theme currentTheme;
    private JPanel userDetailsPanel;
    private JLabel profilePic, usernameLabel, phoneLabel;

    private void _init(){
        setBackground(currentTheme.bgColor());
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 30));

        ImageIcon icon = new ImageIcon("src/main/resources/images/doctor_icon.png");
        Image img = icon.getImage().getScaledInstance(60,60, Image.SCALE_SMOOTH);


        profilePic = new JLabel();
        profilePic.setPreferredSize(new Dimension(60, 60));
        profilePic.setOpaque(false);
        profilePic.setHorizontalAlignment(SwingConstants.LEFT);
        profilePic.setVerticalAlignment(SwingConstants.CENTER);
        profilePic.setText("PIC");
        profilePic.setIcon(new ImageIcon(img));

        JPanel profilePicPanel = new JPanel();
        profilePicPanel.setOpaque(false);
        profilePicPanel.add(profilePic);// Adds horizontal space to the right
        profilePicPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));


        userDetailsPanel = new JPanel(new GridLayout(3,1,0,-12));
        userDetailsPanel.setOpaque(false);

        usernameLabel = new JLabel("Amsakine Mohamed");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 17));
        usernameLabel.setForeground(currentTheme.greenColor());

        JLabel emailLabel = new JLabel("mohamedamsakine17@gmail.com");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 11));

        phoneLabel = new JLabel("+212 689 78 2475");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 11));

        userDetailsPanel.add(usernameLabel);
        userDetailsPanel.add(emailLabel);
        userDetailsPanel.add(phoneLabel);

        add(profilePicPanel, BorderLayout.WEST);
        add(userDetailsPanel, BorderLayout.CENTER);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(currentTheme.greenColor());
                usernameLabel.setForeground(currentTheme.bgColor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(currentTheme.bgColor());
                usernameLabel.setForeground(currentTheme.greenColor());
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setBackground(currentTheme.greenColor());
                usernameLabel.setForeground(currentTheme.bgColor());
            }
        });
    }


    public UserInfoPanel(Theme currentTheme){
        this.currentTheme = currentTheme;
        _init();
    }
}
