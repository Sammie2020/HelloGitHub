package org.example.spring.dao.impl;

import org.example.spring.dao.IBookDao;
import org.example.spring.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class BookDaoImpl implements IBookDao {
  @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(Book book) {
        //1.创建sql语句
        String sql ="insert into book values(?,?,?)";
        //2.调用方法实现
         int update =  jdbcTemplate.update(sql,book.getBookId(),book.getBookname(),book.getBookStatus());
        System.out.println(update);
    }
//修改
    @Override
    public void update(Book book) {
        //1.创建sql语句
        String sql ="update book set bookname=? ,bookStatus=? where bookId=?";
        //2.调用方法实现
        int update =  jdbcTemplate.update(sql,book.getBookname(),book.getBookStatus(),book.getBookId());
        System.out.println(update);
    }
//删除
    @Override
    public void delete(String id) {
        //1.创建sql语句
        String sql ="delete from  book  where bookId=?";
        int update = jdbcTemplate.update(sql,id);
        System.out.println(update);
    }

    @Override
    public int count() {
        //1.创建sql语句
        String sql ="select count(*) from  book";
        Integer num = jdbcTemplate.queryForObject(sql,Integer.class);
        return  num;
    }

    @Override
    public Book findById(String id) {
        String sql ="select * from  book where bookId =? ";
        Book  book= jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class),id);
        return  book;
    }

    @Override
    public List<Book> findAll() {
        String sql ="select * from  book ";
        List<Book> list= jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
        return list;
    }

    @Override
    public void batchAddBook(List<Object[]> args) {
        String sql ="insert into book values(?,?,?)";
        int[] ints = jdbcTemplate.batchUpdate(sql,args);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchUpdateBook(List<Object[]> args) {
        String sql ="update book set bookname=? ,bookStatus=? where bookId=?";
        int[] ints = jdbcTemplate.batchUpdate(sql,args);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchDeleteBook(List<Object[]> args) {
        String sql ="delete from  book  where bookId=?";
        int[] ints = jdbcTemplate.batchUpdate(sql,args);
        System.out.println(Arrays.toString(ints));
    }
}
