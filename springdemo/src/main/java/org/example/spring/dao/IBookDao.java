package org.example.spring.dao;

import org.example.spring.entity.Book;

import java.util.List;

public interface IBookDao {
    void add(Book book);
    void update(Book book);
    void delete(String id);
    int count();
    Book findById(String id);
    List<Book> findAll();
    void batchAddBook(List<Object[]> args);
    void batchUpdateBook(List<Object[]> args);
    void batchDeleteBook(List<Object[]> args);
}
