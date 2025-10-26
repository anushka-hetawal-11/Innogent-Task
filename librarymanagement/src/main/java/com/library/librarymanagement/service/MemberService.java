package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.MemberRequestDTO;
import com.library.librarymanagement.dto.MemberResponseDTO;
import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.Member;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public MemberResponseDTO createMember(MemberRequestDTO requestDTO) {
        Member member = new Member();
        member.setName(requestDTO.getName());

        Member savedMember = memberRepository.saveAndFlush(member);

        if (requestDTO.getBorrowedBookIds() != null) {
            for (Long bookId : requestDTO.getBorrowedBookIds()) {
                borrowBook(savedMember, bookId);  // ✅ Reuse helper for stock check
            }
        }

        memberRepository.saveAndFlush(savedMember);

        Set<String> borrowedBookTitles = savedMember.getBorrowedBooks()
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toSet());

        return new MemberResponseDTO(savedMember.getId(), savedMember.getName(), borrowedBookTitles);
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();

        return members.stream().map(member -> {
            Set<String> borrowedBookTitles = member.getBorrowedBooks().stream().map(Book::getTitle).collect(Collectors.toSet());
            return new MemberResponseDTO(member.getId(), member.getName(), borrowedBookTitles);
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberResponseDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found with ID: " + id));

        Set<String> borrowedBookTitles = member.getBorrowedBooks().stream().map(Book::getTitle).collect(Collectors.toSet());

        return new MemberResponseDTO(member.getId(), member.getName(), borrowedBookTitles);
    }

    @Transactional
    public MemberResponseDTO updateMember(Long id, MemberRequestDTO requestDTO) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + id));

        existingMember.setName(requestDTO.getName());

        if (requestDTO.getBorrowedBookIds() != null) {
            for (Long bookId : requestDTO.getBorrowedBookIds()) {
                borrowBook(existingMember, bookId);  // Reuse helper method
            }
        }

        memberRepository.saveAndFlush(existingMember);

        Set<String> borrowedBookTitles = existingMember.getBorrowedBooks()
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toSet());

        return new MemberResponseDTO(existingMember.getId(), existingMember.getName(), borrowedBookTitles);
    }


    @Transactional
    public void deleteMember(Long id) {
        Member existingMember = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found with ID: " + id));
        memberRepository.delete(existingMember);
    }

    @Transactional
    private void borrowBook(Member member, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        if (book.getStock() < 1) {
            throw new RuntimeException("Book '" + book.getTitle() + "' is out of stock!");
        }

        book.setStock(book.getStock() - 1);
        bookRepository.saveAndFlush(book);

        member.getBorrowedBooks().add(book);
    }

}

