package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.BookRequestDTO;
import com.library.librarymanagement.dto.BookResponseDTO;
import com.library.librarymanagement.entity.Author;
import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.Member;
import com.library.librarymanagement.repository.AuthorRepository;
import com.library.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public BookResponseDTO createBook(BookRequestDTO requestDTO) {
        Author author = authorRepository.findById(requestDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + requestDTO.getAuthorId()));

        Book book = new Book();
        book.setTitle(requestDTO.getTitle());
        book.setStock(requestDTO.getStock());
        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);
        return new BookResponseDTO(savedBook.getId(), savedBook.getTitle(), savedBook.getStock(), savedBook.getAuthor().getName());
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(book -> new BookResponseDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getStock(),
                        book.getAuthor().getName()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        return new BookResponseDTO(book.getId(), book.getTitle(), book.getStock(), book.getAuthor().getName());
    }

    @Transactional
    public BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        existingBook.setTitle(requestDTO.getTitle());
        existingBook.setStock(requestDTO.getStock());

        if (requestDTO.getAuthorId() != null) {
            Author author = authorRepository.findById(requestDTO.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found with id: " + requestDTO.getAuthorId()));
            existingBook.setAuthor(author);
        }

        Book updatedBook = bookRepository.save(existingBook);

        return new BookResponseDTO(updatedBook.getId(), updatedBook.getTitle(), updatedBook.getStock(), updatedBook.getAuthor().getName());
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        for (Member member : book.getMembers()) {
            member.getBorrowedBooks().remove(book);
        }

        bookRepository.delete(book);
    }


}
