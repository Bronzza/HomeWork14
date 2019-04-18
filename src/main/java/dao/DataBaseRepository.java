package dao;

import java.util.List;

interface DataBaseRepository<T> {
    public abstract List<T> getAll();

    public abstract List<T> get(String surname);

    public abstract void delete(String surname);
}
