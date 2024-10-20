package org.example.case_modul4.controller;
import org.example.case_modul4.model.Author;
import org.example.case_modul4.model.Book;
import org.example.case_modul4.service.AuthorService;
import org.example.case_modul4.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
@Controller
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;
    @GetMapping("/authors")
    public String listAuthors(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "Admin/author/authorList";
    }
    @GetMapping("/authors/{authorId}")
    public String authorDetails(@PathVariable int authorId, Model model) {
        Author author = authorService.getAuthorById(authorId);
        List<Book> booksByAuthor = bookService.getBooksByAuthor(authorId);
        model.addAttribute("author", author);
        model.addAttribute("books", booksByAuthor);
        return "Admin/author/authorDetails";
    }
    @PostMapping("/authors/delete/{authorId}")
    public String deleteAuthor(@PathVariable int authorId) {
        authorService.deleteAuthor(authorId);
        return "redirect:/authors";
    }
    @GetMapping("/authors/edit/{id}")
    public String editAuthor(@PathVariable("id") int id, Model model) {
        Author author = authorService.findById(id);
        model.addAttribute("author", author);
        return "Admin/author/editAuthor";
    }
    @PostMapping("/authors/update")
    public String updateAuthor(@ModelAttribute Author author) {
        authorService.updateAuthor(author);
        return "redirect:/authors";
    }
    @GetMapping("/authors/add")
    public String showAddAuthorForm(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "Admin/author/addAuthor";
    }
    @PostMapping("/authors/save")
    public String saveAuthor(@ModelAttribute Author author) {
        if (author.getNameAuthor() == null || author.getNameAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tác giả không được để trống");
        }
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }
}