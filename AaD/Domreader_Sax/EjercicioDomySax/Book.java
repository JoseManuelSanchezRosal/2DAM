package EjercicioDomySax;
import java.util.ArrayList;
import java.util.List;

public class Book {// Clase Book
    private String id;
    private String isbn;
    private List<String> authors;
    private List<String> categories;
    private int year;
    private double price;
    private String currency;

    //Constructor
    public Book(String id, String isbn, List<String> authors, List<String> categories, int year, double price, String currency) {
        this.id = id;
        this.isbn = isbn;
        this.authors = authors;
        this.categories = categories;
        this.year = year;
        this.price = price;
        this.currency = currency;
    }
    //Getters y Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public List<String> getAuthors() {
        return authors;
    }
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    //ToString

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", isbn='" + isbn + '\'' +
                ", authors=" + authors +
                ", categories=" + categories +
                ", year=" + year +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }
}