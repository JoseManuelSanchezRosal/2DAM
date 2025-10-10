package Dom_Sax_Tarea2.models;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Book {
    private String id;
    private String isbn;
    private String title;
    private ArrayList<Author> authors;
    private ArrayList<String> categories;
    private int year;
    private double price;
    private String currency;

    public Book() {}

    public Book(String id, String isbn, String title, ArrayList<Author> authors, ArrayList<String> categories, int year, double price, String currency) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.categories = categories;
        this.year = year;
        this.price = price;
        this.currency = currency;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("[").append(this.id).append("] ").append(this.title).append(" (").append(this.year).append(")")
                .append("\n\t").append("ISBN: ").append(this.isbn)
                .append("\n\t").append("Autores: ").append(this.authors.stream().map(Author::toString).collect(Collectors.joining(", ")))
                .append("\n\t").append("Categorías: ").append(String.join(", ", this.categories))
                .append("\n\t").append("Precio: ").append(this.price).append(" ").append(this.currency);

        return stringBuilder.toString();
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
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
}