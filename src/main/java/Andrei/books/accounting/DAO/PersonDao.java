package Andrei.books.accounting.DAO;

import Andrei.books.accounting.models.Book;
import Andrei.books.accounting.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(fio, dateofbirth) VALUES (?,?)", person.getFIO(), person.getDateOfBirth());
    }

    public Person find(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void update(Person person, int id) {
        jdbcTemplate.update("UPDATE Person SET FIO=?, dateofbirth=? WHERE id=?", person.getFIO(), person.getDateOfBirth(), id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
    public List<String> getPersonBooks(int id){
        List<Book> books = jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
        if(books.isEmpty()){
            return null;
        }
        List<String> namesOfBooks = new ArrayList<>();
        books.forEach(book -> namesOfBooks.add(book.getName()));
        return namesOfBooks;
    }
}
