package org.example.case_modul4.controller;

import org.example.case_modul4.model.Author;
import org.example.case_modul4.model.Book;
import org.example.case_modul4.model.Category;
import org.example.case_modul4.service.AuthorService;
import org.example.case_modul4.service.BookService;
import org.example.case_modul4.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
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
    public String adminPage(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "Admin/admin";
    }

    @PostMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBookById(id);
        return "redirect:/admin";
    }

    @GetMapping("/editProduct/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {

        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "Admin/editProduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "Admin/editProduct";
        }
        bookService.updateBook(book);
        return "redirect:/admin";
    }


    @GetMapping("/addProduct")
    public String showAddProductForm(Model model) {
        List<Author> authors = authorService.getAllAuthors(); // Giả sử bạn có phương thức này
        List<Category> categories = categoryService.getAllCategories(); // Lấy danh sách thể loại

        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);

        return "Admin/addProduct";
    }



    @PostMapping("/saveBook")
    public String saveBook(@RequestParam("title") String title,
                           @RequestParam("author") int authorId, // Lấy ID của tác giả từ dropdown
                           @RequestParam("category") int categoryId,
                           @RequestParam("price") int price,
                           @RequestParam("quantity") int quantity,
                           @RequestParam("imageUrl") String imageUrl) {

        Author author = authorService.findById(authorId); // Lấy đối tượng Author từ ID
        if (author == null) {
            // Xử lý nếu không tìm thấy tác giả (nên có thông báo lỗi)
            return "redirect:/addBook?error=authorNotFound"; // Redirect về trang thêm sách với thông báo lỗi
        }

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author); // Sử dụng đối tượng Author
        book.setCategory(new Category(categoryId)); // Thể loại sẽ được tạo trong dịch vụ
        book.setPrice(price);
        book.setQuantity(quantity);
        book.setCoverImage(imageUrl); // Lưu URL hình ảnh
        book.setFavorite(false);
        bookService.saveBook(book);

        return "redirect:/admin"; // Chuyển hướng về trang danh sách sản phẩm
    }



}
