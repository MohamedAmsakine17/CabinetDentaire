package ma.CabinetDentaire.repository;

import java.util.List;
import java.util.Optional;

public interface Dao <T,ID>{
    List<T> findAll();

    Optional<T> findById(ID id);

    T save(T element);

    boolean update(T element);

    boolean delete(T element);

    boolean deleteById(ID id);
}
