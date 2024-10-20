package org.example.case_modul4.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.case_modul4.model.Book;
import org.example.case_modul4.model.Category;
import org.example.case_modul4.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {


    @Autowired
    private DataSource dataSource;
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }

    public Book getBookDetails(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sách không tồn tại"));

        if (book.getCategory() != null) {
            String categoryName = book.getCategory().getCategoryName();

        }

        return book;
    }


    public List<Book> findSuggestedBooks(Category category) {

        return bookRepository.findByCategory(category);
    }

    public List<String> findGenresByBookId(int categoryId) {
        String sql = "SELECT category_name FROM categories WHERE id = ?";
        List<String> genres = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, categoryId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                genres.add(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public List<Book> findBooksByTitleContaining(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }

    public void updateBook(Book book) {
        if (book.getCategory() == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        Optional<Book> existingBookOptional = bookRepository.findById(book.getId());
        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();
            existingBook.setTitle(book.getTitle());
            existingBook.setPrice(book.getPrice());
            existingBook.setQuantity(book.getQuantity());
            existingBook.setCategory(book.getCategory());
            existingBook.setCoverImage(book.getCoverImage());
            bookRepository.save(existingBook);
        } else {
            throw new EntityNotFoundException("Book not found with ID: " + book.getId());
        }
    }



    public void saveBook(Book book) {
        if (book.getCategory() == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        bookRepository.save(book);
    }

    public Book findById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void updateProduct(Book updatedBook) {
        Optional<Book> existingBookOptional = bookRepository.findById(updatedBook.getId());
        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setQuantity(updatedBook.getQuantity());
            existingBook.setCategory(updatedBook.getCategory());
            existingBook.setCoverImage(updatedBook.getCoverImage());
            bookRepository.save(existingBook);
        } else {
            throw new RuntimeException("Book not found with ID: " + updatedBook.getId());
        }
    }
    public List<Book> getBooksByAuthor(int authorId) {
        return bookRepository.findByAuthor_Id(authorId);
    }
}
