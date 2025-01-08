package ma.CabinetDentaire.presentation.view.themes;

import javax.swing.*;
import java.awt.*;

public abstract class Theme {
    public abstract Color bgColor();
    public abstract Color darkBgColor();
    public abstract ImageIcon getButtonIcon(String name);
    public abstract Color fontColor();
    public abstract Color fieldsBgColor();

    // Colors
    public Color whiteColor(){ return new Color(239, 255, 252);}

    public  Color greenColor(){ return new Color(80,216,144); }
    public  Color greenHoverColor() {return new Color(64,174,116);}

    public  Color redColor(){ return new Color(217,93,80); }
    public  Color redHoverColor() {return new Color(174,75,64);}

    public  Color blueColor(){ return new Color(79,152,202); }

    public  Color yellowolor(){ return new Color(255,245,116); }
    public  Color yellowHoverColor() {return new Color(212,205,96);}
}
