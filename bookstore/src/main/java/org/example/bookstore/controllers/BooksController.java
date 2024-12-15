package org.example.bookstore.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.bookstore.models.Book;
import org.example.bookstore.models.BooksStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BooksController {

    @GetMapping("/")
    public String bookList(Model model) {
        model.addAttribute("books", BooksStorage.getBooks());
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
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            //String referer = request.getHeader("Referer");
            //return "redirect:" + referer; // redirect to previous page
            return "edit-form";
        }
        delete(book.getId());
        BooksStorage.getBooks().add(book);
        return "redirect:/";
    }
}