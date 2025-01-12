package ma.CabinetDentaire.presentation.view;

import lombok.Data;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.fields.PasswordInputField;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.palette.fields.TextInputField;
import ma.CabinetDentaire.repository.exceptions.DaoException;
import ma.CabinetDentaire.repository.fileDB_impl.UtilisateurDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Data
public class LoginView extends JFrame {
    private Theme currentTheme;
    private UtilisateurDAO userDAO;

    private JPanel mainPanel, leftPanel, rightPanel;
    private JSplitPane splitPane;

    private void _init(){
        mainPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(150, 50, 150, 50));
        rightPanel.setBackground(currentTheme.bgColor());
        addForm(rightPanel);

        leftPanel.setBackground(currentTheme.bgColor());
        leftPanel.setBorder(BorderFactory.createEmptyBorder());
        addImage(leftPanel);

        splitPane.setDividerLocation(500);
        splitPane.setDividerSize(0);
        splitPane.setBorder(BorderFactory.createEmptyBorder());

        mainPanel.add(splitPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void addImage(JPanel panel){
        ImageIcon icon = new ImageIcon("src/main/resources/images/auth_img.jpg");
        Image img = icon.getImage().getScaledInstance(500,700, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));

        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imgLabel, BorderLayout.CENTER);
    }

    private void addForm(JPanel panel) {
        MyLabel title = new MyLabel(currentTheme,"Bienvenue chez Pretty-Smile",28,1);
        panel.add(title);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        MyLabel smallTitle = new MyLabel(currentTheme,"Votre partenaire fiable en gestion de cabinet dentaire",14,0);
        panel.add(smallTitle);

        panel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between title and input fields

        JPanel inputsPanel = new JPanel(new GridLayout(3,1,0,15));
        inputsPanel.setOpaque(false);

        TextInputField usernameInputField = new TextInputField(currentTheme,"Username",20);
        usernameInputField.setPreferredSize(new Dimension(400, 70));  // Set width and height explicitly
        inputsPanel.add(usernameInputField);

        PasswordInputField passwordInputField = new PasswordInputField(currentTheme, "Password",20);
        passwordInputField.setPreferredSize(new Dimension(400, 70));
        inputsPanel.add(passwordInputField);


        JCheckBox checkBox = new JCheckBox("Se souvenir de moi");
        checkBox.setOpaque(false);
        checkBox.setBounds(50, 50, 200, 30);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 14)); // Font size 14
        checkBox.setFocusPainted(false);
        inputsPanel.add(checkBox);

        panel.add(inputsPanel);


        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between title and input fields

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.setOpaque(false);

        MyButton loginBtn = new MyButton(currentTheme,"Login", currentTheme.greenColor(), currentTheme.greenHoverColor());
        buttonsPanel.add(loginBtn, BorderLayout.WEST);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between title and input fields


        MyLabel messageText = new MyLabel(currentTheme,"      ",16,1);
        panel.add(messageText);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameInputField.getText();
                String password = new String(passwordInputField.getPassword());

                if (userDAO.authenticate(username, password)) {
                    messageText.setText("Login successful!");
                    messageText.setForeground(currentTheme.greenColor());

                    if(checkBox.isSelected()){
                        System.out.print("Hello");

                        try {
                            userDAO.saveSession();
                        } catch (DaoException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    Timer timer = new Timer(750, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                            new MainView(currentTheme);
                        }
                    });

                    timer.setRepeats(false);
                    timer.start();
                } else {
                    messageText.setText("Invalid username or password.");
                    messageText.setForeground(currentTheme.redColor());
                }
                panel.revalidate();
                panel.repaint();
            }
        });

    }

    public LoginView(Theme currentTheme) {
        this.currentTheme = currentTheme;
        this.userDAO = new UtilisateurDAO();

        if(userDAO.isSessionSaved()){
            new MainView(currentTheme);
        } else {
            setTitle("Login");
            setSize(1000, 700);
            setResizable(false);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setUndecorated(true);
            _init();
            setVisible(true);
        }
    }
}