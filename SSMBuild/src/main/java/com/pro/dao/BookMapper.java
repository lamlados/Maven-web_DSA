package com.pro.dao;

import com.pro.pojo.Books;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface BookMapper {
    int addBook(Books book);
    int deleteBookById(@Param("bookID") int bookID);
    int updateBook(Books book);
    Books queryBookById(@Param("bookID") int bookID);
    List<Books> queryAllBooks();
}
