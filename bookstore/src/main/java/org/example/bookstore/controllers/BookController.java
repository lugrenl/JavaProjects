package org.example.bookstore.controllers;

import org.example.bookstore.models.Book;
import org.example.bookstore.models.BooksStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class BookController {
    @GetMapping("/")
    public String bookList(Model model) {
        model.addAttribute("books", BooksStorage.getBooks());
        return "books-list";
    }

    @GetMapping("/create-form")
    public String createForm() {
        return "create-form";
    }

    @PostMapping("/create")
    public String create(Book book) {
        BooksStorage.getBooks().add(book);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        Book bookToDelete = BooksStorage.getBooks().stream().
                filter(book -> book.getId().equals(id)).
                findFirst().
                orElseThrow(RuntimeException::new);
        BooksStorage.getBooks().remove(bookToDelete);
        return "redirect:/";
    }

    @GetMapping("/edit-form/{id}")
    public String createForm(@PathVariable("id") String id, Model model) {
        Book bookToEdit = BooksStorage.getBooks().stream().
                filter(book -> book.getId().equals(id)).
                findFirst().
                orElseThrow(RuntimeException::new);
        model.addAttribute("book", bookToEdit);
        return "edit-form";
    }

    @PostMapping("/update")
    public String update(Book book) {
        delete(book.getId());
        BooksStorage.getBooks().add(book);
        return "redirect:/";
    }
}