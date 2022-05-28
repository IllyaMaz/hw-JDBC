package dl;

import java.sql.SQLException;

public interface Repository<T> {
    T findByIndex(Integer index) throws SQLException;

    void save(T t);

    void remove(T t);

    void update(T t);
}
