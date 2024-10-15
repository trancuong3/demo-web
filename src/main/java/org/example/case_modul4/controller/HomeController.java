package org.example.case_modul4.controller;

import org.example.case_modul4.model.Book;
import org.example.case_modul4.model.Category;
import org.example.case_modul4.service.BookService;
import org.example.case_modul4.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    // Thêm categories vào model cho mọi phương thức
    @ModelAttribute
    public void addCategoriesToModel(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
    }

    // Hiển thị trang chủ với tất cả sách
    @GetMapping("/static")
    public String homePage(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "static";
    }

    // Hiển thị sách theo danh mục
    @GetMapping("/categories/{categoryName}")
    public String getBooksByCategory(@PathVariable String categoryName, Model model) {
        Category selectedCategory = categoryService.getCategoryByName(categoryName);
        if (selectedCategory == null) {
            // Xử lý khi không tìm thấy danh mục
            model.addAttribute("errorMessage", "Danh mục không tồn tại.");
            return "error"; // Tạo một trang error.html hoặc xử lý theo cách bạn muốn
        }
        List<Book> books = bookService.getBooksByCategory(selectedCategory);
        model.addAttribute("selectedCategory", selectedCategory);
        model.addAttribute("books", books);
        return "book_list";
    }

    @GetMapping("/books/{id}")
    public String getBookDetails(@PathVariable int id, Model model) {
        Book book = bookService.getBookDetails(id);
        model.addAttribute("book", book);
        model.addAttribute("category", book.getCategory()); // Thêm thể loại sách
        return "bookDetail"; // Đảm bảo rằng bạn có file template bookDetail.html
    }

    // Các phương thức khác như /bestsellers, /weekly-discounts, etc.
}
