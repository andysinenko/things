package ua.com.sinenko.things.book.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.sinenko.things.book.dto.AuthorDto;
import ua.com.sinenko.things.book.dto.AuthorMapper;
import ua.com.sinenko.things.book.dto.AuthorRequest;
import ua.com.sinenko.things.book.service.AuthorService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/authors")
@AllArgsConstructor
public class AuthorController {

    private AuthorService authorService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<AuthorDto>> getAllBooks() {
        var authors = authorService.getAllAuthors();
        var authorDtos = authors.stream().map(AuthorMapper::mapEntityToDto).toList();

        return new ResponseEntity<>(authorDtos, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> addAuthor(@RequestBody AuthorRequest authorRequest) {
        authorService.saveAuthor(AuthorMapper.mapRequestToEntity(authorRequest));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
