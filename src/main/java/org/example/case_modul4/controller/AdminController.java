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
public class AdminController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

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



}
