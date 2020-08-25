package com.pro.service;

import com.pro.dao.BookMapper;
import com.pro.pojo.Books;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService{
    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }
    public int addBook(Books book) { return bookMapper.addBook(book); }
    public int deleteBookById(int bookID) {
        return bookMapper.deleteBookById(bookID);
    }
    public int updateBook(Books book) {
        return bookMapper.updateBook(book);
    }
    public Books queryBookById(int bookID) { return bookMapper.queryBookById(bookID); }
    public List<Books> queryAllBooks() {
        return bookMapper.queryAllBooks();
    }
}
