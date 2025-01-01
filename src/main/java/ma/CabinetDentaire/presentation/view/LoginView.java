package ma.CabinetDentaire.presentation.view;

import lombok.Data;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.palette.fields.PasswordInputField;
import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.palette.fields.TextInputField;
import ma.CabinetDentaire.repository.db_files.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Data
public class LoginView extends JFrame {
    private Theme currentTheme;
    private UserDAO userDAO;

    private JPanel mainPanel, leftPanel, rightPanel;
    private JSplitPane splitPane;

    private void _init(){
        mainPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(165, 50, 165, 50));
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
        MyLabel title = new MyLabel(currentTheme,"Welcome to Pretty-Smile",30,1);
        panel.add(title);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        MyLabel smallTitle = new MyLabel(currentTheme,"Your trusted partner in dental practice management",14,0);
        panel.add(smallTitle);

        panel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between title and input fields

        TextInputField usernameInputField = new TextInputField(currentTheme,"Username",20);
        usernameInputField.setPreferredSize(new Dimension(400, 70));  // Set width and height explicitly
        panel.add(usernameInputField);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between title and input fields

        PasswordInputField passwordInputField = new PasswordInputField(currentTheme, "Password",20);
        passwordInputField.setPreferredSize(new Dimension(400, 70));
        panel.add(passwordInputField);

        panel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between title and input fields


        MyButton loginBtn = new MyButton(currentTheme,"Login", currentTheme.greenColor(), new Color(64,174,116));
        panel.add(loginBtn);

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
                } else {
                    // Show an error message if authentication fails
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
        this.userDAO = new UserDAO();

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