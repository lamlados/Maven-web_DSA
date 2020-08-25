package com.pro.service;

import com.pro.pojo.Books;
import java.util.List;

public interface BookService {
    int addBook(Books book);
    int deleteBookById(int bookID);
    int updateBook(Books book);
    Books queryBookById(int bookID);
    List<Books> queryAllBooks();
}
