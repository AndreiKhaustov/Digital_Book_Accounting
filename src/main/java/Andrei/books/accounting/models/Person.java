package Andrei.books.accounting.models;

public class Person {
    private int id;
    private String FIO;
    private int dateOfBirth;

    public Person(int id, String FIO, int dateOfBirth) {
        this.id = id;
        this.FIO = FIO;
        this.dateOfBirth = dateOfBirth;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
