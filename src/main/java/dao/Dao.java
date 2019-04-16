package dao;

import java.util.List;

public abstract class Dao<T> {
    public abstract List<T> getAll();

    public abstract List<T> get(String surName);

    public abstract void delete(String surName);
}
