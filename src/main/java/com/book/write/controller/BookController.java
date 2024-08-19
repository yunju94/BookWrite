package com.book.write.controller;

import com.book.write.entity.Book;
import com.book.write.service.BookAPIService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
@AllArgsConstructor
public class BookController {

    @Autowired
    private  static BookAPIService bookAPIService;

    @GetMapping(value = "/book/Search")
    public String bookSearch (){

        return "book/SearchBook";
    }

    @PostMapping(value = "/book/SearchBook/{search}")
    public  @ResponseBody ResponseEntity kakaoBook(@PathVariable String search) throws IOException {

        StringBuilder sb = bookAPIService.apiCall(search);
        ArrayList<Book> books =bookAPIService.stringtoJSON(sb);

        return new ResponseEntity(books, HttpStatus.OK);
    }

}
