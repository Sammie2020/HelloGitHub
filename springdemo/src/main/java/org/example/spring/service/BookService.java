package org.example.spring.service;

import org.example.spring.dao.IBookDao;
import org.example.spring.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private IBookDao bookDao;
    public void addBook(Book book){
        bookDao.add(book);
    }
    public void updateBook(Book book){
        bookDao.update(book);
    }
    public void deleteBook(String id){
        bookDao.delete(id);
    }
    public int selectCount(){
       return bookDao.count();
    }
    public Book selectOne(String id){
        return bookDao.findById(id);
    }
    public List<Book> selectAll(){
        return bookDao.findAll();
    }
    public void batchAdd(List<Object[]> args){
         bookDao.batchAddBook(args);
    }
    public void batchUpdate(List<Object[]> args){
        bookDao.batchUpdateBook(args);
    }
    public void batchDelete(List<Object[]> args){
        bookDao.batchDeleteBook(args);
    }
}
