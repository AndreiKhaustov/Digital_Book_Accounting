package Andrei.books.accounting.controllers;

import Andrei.books.accounting.DAO.PersonDao;
import Andrei.books.accounting.models.Book;
import Andrei.books.accounting.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {
    private PersonDao personDao;

    @Autowired
    public PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personDao.index());
        return "people/index";
    }

    @GetMapping("/add")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPerson(@ModelAttribute("person") Person person) {
        this.personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}")
    public String showPerson(Model model, @PathVariable("id") int id) {
        Person person = personDao.find(id);
        model.addAttribute("person", person);
        model.addAttribute("listOfBook", personDao.getPersonBooks(id));

        return "people/show";
    }

    @GetMapping("{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        Person person = personDao.find(id);
        model.addAttribute("person", person);
        return "people/edit";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.PATCH)
    public String edit(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personDao.update(person, id);
        return "redirect:/people";
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") int id) {
        System.out.println(id);
        personDao.delete(id);
        return "redirect:/people";
    }

}
