package ua.com.sinenko.things.book.web;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.sinenko.things.book.dto.BookDto;
import ua.com.sinenko.things.book.dto.BookMapper;
import ua.com.sinenko.things.book.service.BookService;
import ua.com.sinenko.things.place.dto.PlaceDto;

import java.util.List;

@Controller
@RequestMapping("/api/v1/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List> getAllBooks() {
        var booksEntities = bookService.getAllBooks();
        var booksDto = booksEntities.stream().map(BookMapper::mapEntityToDto).toList();

        return new ResponseEntity<>(booksDto, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> addNewBook(@RequestBody PlaceDto placeDto) {
        return new ResponseEntity<String>("new place " + placeDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{bookId}")
    @ResponseBody
    public ResponseEntity<BookDto> getBookById(@PathParam("id") Long id) {
        var booksEntities = bookService.getBookById(id);
        var booksDto = BookMapper.mapEntityToDto(booksEntities);

        return new ResponseEntity<>(booksDto, HttpStatus.OK);
    }
}
