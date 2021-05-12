package org.example.spring.entity;

import org.springframework.stereotype.Component;

import java.util.List;
public class Book {

    private String bookId;
    private String bookname;
    private String bookStatus;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", bookname='" + bookname + '\'' +
                ", bookStatus='" + bookStatus + '\'' +
                '}';
    }
    //    private String bookname;
//    List<String> list;
//
//    //set注入================
//    public void setBookname(String bookname) {
//        this.bookname = bookname;
//    }
//    public String getBookname() {
//        return bookname;
//    }
//    public Book() {
//    }
//    //set注入=================
//
//    //有参构造注入=======================
//    public Book(String bookname) {
//        this.bookname = bookname;
//    }
//    //有参构造注入=======================
//
//
//    public void setList(List<String> list) {
//        this.list = list;
//    }
////
//    @Override
//    public String toString() {
//        return "Book{" +
//                "bookname='" + bookname + '\'' +
//                ", list=" + list +
//                '}';
//    }
}
