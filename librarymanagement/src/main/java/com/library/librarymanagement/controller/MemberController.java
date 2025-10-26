package com.library.librarymanagement.controller;

import com.library.librarymanagement.dto.MemberRequestDTO;
import com.library.librarymanagement.dto.MemberResponseDTO;
import com.library.librarymanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDTO> createMember(@RequestBody MemberRequestDTO requestDTO) {
        return ResponseEntity.ok(memberService.createMember(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {
        List<MemberResponseDTO> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id) {
        MemberResponseDTO member = memberService.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable Long id, @RequestBody MemberRequestDTO requestDTO) {
        MemberResponseDTO updated = memberService.updateMember(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok("Member deleted successfully!");
    }

    @GetMapping("/{id}/borrowed-books")
    public ResponseEntity<Set<String>> getBorrowedBooks(@PathVariable Long id) {
        MemberResponseDTO memberDTO = memberService.getMemberById(id);
        return ResponseEntity.ok(memberDTO.getBorrowedBookTitles());
    }
}