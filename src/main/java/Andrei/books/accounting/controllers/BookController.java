package Andrei.books.accounting.controllers;
import Andrei.books.accounting.DAO.BookDao;
import Andrei.books.accounting.DAO.PersonDao;
import Andrei.books.accounting.models.Book;
import Andrei.books.accounting.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private BookDao bookDao;
    private PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;

        this.personDao = personDao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());

        return "books/index";
    }

    @GetMapping("/add")
    public String createBook(@ModelAttribute("book") Book book) {

        return "books/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book")Book book) {
        this.bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("{id}")
    public String showBook(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person) {
        Book book = bookDao.find(id);
        model.addAttribute("book", book);
        Optional<Person> bookOwner = bookDao.getBookOwner(id);

        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        }
        else {
            model.addAttribute("people", personDao.index());
        }
        return "books/show";
    }

    @GetMapping("{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
        Book book = bookDao.find(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.PATCH)
    public String edit(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        bookDao.update(book, id);
        return "redirect:/books";
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") int id) {

        bookDao.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDao.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookDao.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}
