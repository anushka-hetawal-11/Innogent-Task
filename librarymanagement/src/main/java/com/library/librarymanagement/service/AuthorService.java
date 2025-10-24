package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.AuthorRequestDTO;
import com.library.librarymanagement.dto.AuthorResponseDTO;
import com.library.librarymanagement.entity.Author;
import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.repository.AuthorRepository;
import com.library.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public AuthorResponseDTO createAuthor(AuthorRequestDTO authorRequestDTO){
        Author author = new Author();
        author.setName(authorRequestDTO.getName());

        if(authorRequestDTO.getBookIds() != null){
            List<Book> books = bookRepository.findAllById(authorRequestDTO.getBookIds());
            author.setBooks(books);
        }

        Author saved = authorRepository.save(author);

        List<String> booksTitles = saved.getBooks().stream().map(Book::getTitle).collect(Collectors.toList());
        return new AuthorResponseDTO(saved.getId(), saved.getName(), booksTitles);
    }

    @Transactional(readOnly = true)
    public List<AuthorResponseDTO> getAllAuthors(){
        List<Author> authors = authorRepository.findAll();

        return authors.stream().map(author -> {
            List<String> booksTitles = author.getBooks().stream().map(Book::getTitle).collect(Collectors.toList());
            return new AuthorResponseDTO(author.getId(), author.getName(), booksTitles);
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AuthorResponseDTO getAuthorById(Long id){
        Author author = authorRepository.findById(id).orElseThrow(()-> new RuntimeException("Author not found with id: "+id));

        List<String> bookTitles = author.getBooks().stream().map(Book::getTitle).collect(Collectors.toList());
        return new AuthorResponseDTO(author.getId(), author.getName(), bookTitles);
    }

    @Transactional
    public AuthorResponseDTO updateAuthor(Long id,AuthorRequestDTO authorRequestDTO){
        Author author = authorRepository.findById(id).orElseThrow(()->new RuntimeException("Author not found with id: "+id));

        author.setName(authorRequestDTO.getName());
        if(authorRequestDTO.getBookIds() != null){
            List<Book> books = bookRepository.findAllById(authorRequestDTO.getBookIds());
            author.setBooks(books);
        }

        Author updated = authorRepository.save(author);

        List<String> bookTitles = updated.getBooks().stream().map(Book::getTitle).collect(Collectors.toList());
        return new AuthorResponseDTO(author.getId(), author.getName(), bookTitles);
    }

    @Transactional
    public void deleteAuthor(Long id){
         authorRepository.deleteById(id);
    }
}
