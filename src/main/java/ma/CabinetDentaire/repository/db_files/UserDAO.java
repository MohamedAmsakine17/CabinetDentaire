package ma.CabinetDentaire.repository.db_files;

import ma.CabinetDentaire.entities.Utilisateur;
import ma.CabinetDentaire.repository.CRUDRepository;
import ma.CabinetDentaire.repository.CRUDRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements CRUDRepository<Utilisateur, Integer> {
    private static final String CREDENTIALS_FILE = "src/main/data/credentials.txt";

    public boolean authenticate(String username, String password) {
        List<Utilisateur> users = findAll();
        for (Utilisateur user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // User authenticated successfully
            }
        }
        return false; // Authentication failed
    }

    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2) {
                    users.add(new Utilisateur(credentials[0], credentials[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Utilisateur findById(Integer integer) {
        return null;
    }

    //@Override
    //public Optional<Utilisateur> findById(Integer integer) {
    //    return Optional.empty();
    //}

    @Override
    public Utilisateur save(Utilisateur element) {
        return null;
    }

    @Override
    public void update(Utilisateur element) {
    }

    @Override
    public void delete(Utilisateur element) {
    }

    @Override
    public void deleteById(Integer integer) {
    }
}
