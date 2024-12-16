package org.example.bookstore.controllers;

import jakarta.validation.Valid;
import org.example.bookstore.dao.BookDao;
import org.example.bookstore.models.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BooksController {

    private final BookDao bookDao;

    public BooksController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping("/")
    public String bookList(Model model) {
        model.addAttribute("books", bookDao.findAll());
        return "books-list";
    }

    @GetMapping("/create-form")
    public String createForm(@ModelAttribute("book") Book book) {
        return "create-form";
    }

    @GetMapping("/edit-form")
    public String editForm(@ModelAttribute("book") Book book) {
        return "edit-form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-form";
        }
        bookDao.save(book);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        Book bookToDelete = bookDao.getById(id);
        bookDao.delete(bookToDelete.getId());
        return "redirect:/";
    }

    @GetMapping("/edit-form/{id}")
    public String createForm(@PathVariable("id") String id, Model model) {
        Book bookToEdit = bookDao.getById(id);
        model.addAttribute("book", bookToEdit);
        return "edit-form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-form";
        }
        bookDao.update(book);
        return "redirect:/";
    }
}