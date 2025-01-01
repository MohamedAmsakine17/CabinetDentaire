package ma.CabinetDentaire.presentation.view.Auth;

import lombok.Data;
import ma.CabinetDentaire.presentation.view.palette.buttons.MyButton;
import ma.CabinetDentaire.presentation.view.themes.Theme;
import ma.CabinetDentaire.presentation.view.fields.MyInputField;
import javax.swing.*;
import java.awt.*;

@Data
public class LoginView extends JFrame {
    private Theme currentTheme;
    private JPanel pnl_main;
    private JPanel pnl_left;
    private JPanel pnl_right;
    private JSplitPane splitPane;
    private static int height = 700;
    private static int width = 1000;

    private void initComponent(){
        pnl_main = new JPanel(new BorderLayout());
        pnl_main.setBackground(currentTheme.bgColor());

        pnl_left = new JPanel();
        pnl_left.setLayout(new BorderLayout());
        pnl_left.setBackground(Color.LIGHT_GRAY);
        pnl_left.setBorder(BorderFactory.createEmptyBorder()); // Remove any borders
        addPictureToLeftPanel();

        pnl_right = new JPanel();
        pnl_right.setLayout(new GridLayout(0, 1)); // Empty layout for now
        pnl_right.setBorder(BorderFactory.createEmptyBorder());
        addRightPanelContent();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnl_left, pnl_right);
        splitPane.setDividerLocation(width / 2); // Adjust width of the left panel
        splitPane.setDividerSize(5); // Thickness of the divider
        splitPane.setBorder(BorderFactory.createEmptyBorder());

        pnl_main.add(splitPane, BorderLayout.CENTER);

        setContentPane(pnl_main);
    }

    private void addPictureToLeftPanel() {
        // Load an image
        ImageIcon icon = new ImageIcon("src/main/resources/images/auth_img.jpg"); // Update the path to your image
        Image img = icon.getImage().getScaledInstance(width / 2, height, Image.SCALE_SMOOTH); // Scale the image to fit the panel
        JLabel picLabel = new JLabel(new ImageIcon(img));
        picLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pnl_left.add(picLabel, BorderLayout.CENTER);
    }

    private void addRightPanelContent() {
        // Use BoxLayout to arrange components vertically
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // Stack components vertically
        contentPanel.setOpaque(false);  // Make sure background is transparent
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));  // Padding around the content

        // Title label with centered alignment
        JLabel lblTitle = new JLabel("Login", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 40));  // Add a larger font for the title
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the label horizontally
        contentPanel.add(lblTitle);  // Add title to the panel

        // Input Fields
        MyInputField txtUsername = new MyInputField("Username", currentTheme);
        MyInputField txtPassword = new MyInputField("Password", currentTheme);
        contentPanel.add(txtUsername);
        contentPanel.add(txtPassword);

        // Login button with customized theme and size
        MyButton btnLogin = new MyButton("Login", null, currentTheme);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center the button horizontally
        contentPanel.add(btnLogin);  // Add button to the panel

        // Add the contentPanel to pnl_right using BorderLayout.CENTER
        pnl_right.add(contentPanel, BorderLayout.CENTER);
    }


    public LoginView(Theme currentTheme) {
        setResizable(false);
        setCurrentTheme(currentTheme);
        initComponent();
        setTitle("Cabinet Dentaire | Connexion");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}