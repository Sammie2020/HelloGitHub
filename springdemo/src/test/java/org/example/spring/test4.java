package org.example.spring;

import org.example.spring.entity.Book;
import org.example.spring.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class test4 {
    @Test
    public void testJdbcTemplate(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        Book book = new Book();
        book.setBookId("1");
        book.setBookname("Java");
        book.setBookStatus("已读");
        bookService.addBook(book);
    }
    @Test
    public void testJdbcTemplate2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        Book book = new Book();
        book.setBookId("1");
        book.setBookname("Java");
        book.setBookStatus("已读未总结");
        bookService.updateBook(book);
    }
    @Test
    public void testJdbcTemplate3(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        String id = "1";
        bookService.deleteBook(id);
    }
    @Test
    public void testJdbcTemplate4(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
       System.out.println(bookService.selectCount());
    }
    @Test
    public void testJdbcTemplate5(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        System.out.println(bookService.selectOne("1"));
    }
    @Test
    public void testJdbcTemplate6(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        System.out.println(bookService.selectAll());
    }
    @Test
    public void testJdbcTemplate7(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        Object[] o1 = {"2","Test","未读"};
        Object[] o2 = {"3","Redis","初略了解"};
        Object[] o3 = {"4","docker","初略了解"};
        List<Object[]> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        list.add(o3);
        bookService.batchAdd(list);
    }
    @Test
    public void testJdbcTemplate8(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        Object[] o1 = {"Test","未读","3"};
        Object[] o2 = {"docker001","已读","4"};
        List<Object[]> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        bookService.batchUpdate(list);
    }
    @Test
    public void testJdbcTemplate9(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        Object[] o1 = {"3"};
        Object[] o2 = {"4"};
        List<Object[]> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        bookService.batchDelete(list);
    }
}
