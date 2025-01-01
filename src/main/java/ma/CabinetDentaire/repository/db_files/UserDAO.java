package ma.CabinetDentaire.repository.db_files;

import ma.CabinetDentaire.entities.User;
import ma.CabinetDentaire.repository.Dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements Dao<User, Integer> {
    private static final String CREDENTIALS_FILE = "src/main/data/credentials.txt";

    public boolean authenticate(String username, String password) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // User authenticated successfully
            }
        }
        return false; // Authentication failed
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2) {
                    users.add(new User(credentials[0], credentials[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public User save(User element) {
        return null;
    }

    @Override
    public boolean update(User element) {
        return false;
    }

    @Override
    public boolean delete(User element) {
        return false;
    }

    @Override
    public boolean deleteById(Integer integer) {
        return false;
    }
}
