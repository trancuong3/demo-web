package org.example.case_modul4.controller;

import jakarta.servlet.http.HttpSession;
import org.example.case_modul4.model.Author;
import org.example.case_modul4.model.Book;
import org.example.case_modul4.model.Category;
import org.example.case_modul4.service.AuthorService;
import org.example.case_modul4.service.BookService;
import org.example.case_modul4.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AuthorService authorService;



    @GetMapping("/admin")
    public String adminPage(HttpSession session, Model model) {
        // Kiểm tra trạng thái đăng nhập
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn != null && loggedIn) {

            List<Book> books = bookService.getAllBooks();
            model.addAttribute("books", books);
            return "Admin/admin"; // Trả về trang quản trị nếu đã đăng nhập
        } else {
            return "redirect:/login"; // Chuyển đến trang login nếu chưa đăng nhập
        }

    }
    @PostMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBookById(id);
        return "redirect:/admin";
    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        System.out.println("ID received: " + id);
        Book book = bookService.findById(id);

        if (book == null) {
            System.out.println("Book not found for ID: " + id);
            return "redirect:/404";
        }

        System.out.println("Book found: " + book);
        model.addAttribute("book", book);
        return "Admin/editProduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute("book") Book book) {
        String categoryName = book.getCategory().getCategoryName();
        if (categoryName != null && !categoryName.isEmpty()) {
            Category category = categoryService.findByName(categoryName);
            if (category != null) {
                book.setCategory(category);
            } else {
                throw new IllegalArgumentException("Category cannot be null");
            }
        }

        bookService.updateBook(book);
        return "redirect:/admin";
    }




    @GetMapping("/addProduct")
    public String showAddProductForm(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);

        return "Admin/addProduct";
    }


    @PostMapping("/saveBook")
    public String saveBook(@RequestParam("title") String title,
                           @RequestParam("author") int authorId,
                           @RequestParam("category") int categoryId,
                           @RequestParam("price") int price,
                           @RequestParam("quantity") int quantity,
                           @RequestParam("imageUrl") String imageUrl) {

        Author author = authorService.findById(authorId);
        Category category = categoryService.findById(categoryId);
        if (author == null) {
            return "redirect:/addBook?error=authorNotFound";
        }
        if (category == null) {
            return "redirect:/addBook?error=categoryNotFound";
        }

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setPrice(price);
        book.setQuantity(quantity);
        book.setCoverImage(imageUrl);
        book.setFavorite(false);
        bookService.saveBook(book);

        return "redirect:/admin";
    }



}
