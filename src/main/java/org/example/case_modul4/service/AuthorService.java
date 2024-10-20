package org.example.case_modul4.service;

import org.example.case_modul4.model.Author;
import org.example.case_modul4.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public void saveAuthor(Author author) {
        if (author.getNameAuthor() == null || author.getNameAuthor().isEmpty()) {
            throw new IllegalArgumentException("Tên tác giả không được để trống");
        }
        authorRepository.save(author);
    }

    public Author findById(int id) {
        return authorRepository.findById(id).orElse(null);
    }


    public Author getAuthorById(int id) {
        return authorRepository.findById(id).orElse(null);
    }
    public void deleteAuthor(int authorId) {
        authorRepository.deleteById(authorId);
    }
    public Optional<Author> findAuthorById(int authorId) {
        return authorRepository.findById(authorId);
    }
    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }
}
