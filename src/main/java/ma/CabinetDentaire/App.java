package ma.CabinetDentaire;

import ma.CabinetDentaire.presentation.view.Auth.LoginView;
import ma.CabinetDentaire.presentation.view.themes.ThemeLight;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater( () -> new LoginView(new ThemeLight()));
    }
}
