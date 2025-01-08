package ma.CabinetDentaire.presentation.view.palette.panels.dashboard_panels;

import ma.CabinetDentaire.presentation.view.palette.labels.MyLabel;
import ma.CabinetDentaire.presentation.view.themes.Theme;

import javax.swing.*;
import java.awt.*;

public class HeaderCard extends JPanel {
    private Theme currentTheme;
    private String text;
    private Long value;
    private String iconSrc;
    private Color cardBorderColor;

    public void _init(){
        setPreferredSize(new Dimension(250, 150));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 5, 0, cardBorderColor), // Green bottom border
                BorderFactory.createEmptyBorder(10, 10, 10, 15) // Padding
        ));

        setBackground(currentTheme.bgColor());
        setLayout(new BorderLayout());

        JPanel leftPanelContent = new JPanel();
        leftPanelContent.setOpaque(false);
        leftPanelContent.setLayout(new GridLayout(2,1,0,-40));
        MyLabel valueLabel = new MyLabel(currentTheme,value.toString(),32,1);
        MyLabel textLabel = new MyLabel(currentTheme,text,12,1);
        leftPanelContent.add(valueLabel);
        leftPanelContent.add(textLabel);

        JPanel rightPanelContent = new JPanel();
        rightPanelContent.setLayout(new BorderLayout());
        rightPanelContent.setOpaque(false);
        ImageIcon icon = new ImageIcon(iconSrc);
        //Image img = icon.getImage().getScaledInstance(32,32, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);

        rightPanelContent.add(iconLabel, BorderLayout.CENTER);

        add(leftPanelContent, BorderLayout.WEST);
        add(rightPanelContent, BorderLayout.EAST);


    }

    public HeaderCard(Theme currentTheme, String text, Long value, String icon,Color cardBorderColor){
        this.currentTheme = currentTheme;
        this.text = text;
        this.value = value;
        this.iconSrc = icon;
        this.cardBorderColor = cardBorderColor;
        _init();
    }

    public void setBorder(int top, int left, int bottom, int right){
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 5, 0, cardBorderColor), // Green bottom border
                BorderFactory.createEmptyBorder(top, left, bottom, right) // Padding
        ));
    }
}
