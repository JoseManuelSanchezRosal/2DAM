package Tarea2SAXReader;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String id;
    private String isbn;
    private String title;
    private List<String> authors;
    private List<String> categories;
    private int year;
    private double price;
    private String currency;
    private String role;

    public Book(){
        this.authors = new ArrayList<>();
        this.categories = new ArrayList<>();
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

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public void initializeListAuthors(){
        authors = new ArrayList<>();
    }

    public void addAuthor(String author){
        authors.add(author);
    }

    public void initializeListCategories(){
        categories = new ArrayList<>();
    }

    public void addCategory(String category){
        categories.add(category);
    }

    /**
     * Métodos para devolver las listas de forma bonita(con coma pero sin poner la úlitma coma).
     */

    public String showAuthors(){
        return String.join(", ", getAuthors());
    }

    public String showCategories(){
        return String.join(", ", getCategories());
    }

    @Override
    public String toString() {
        return "[" + id + "]" + " " + title + " (" + year + ")" +
                "\nISBN: " + isbn +
                "\nAutores: " + showAuthors() + " (" + role + ")" +
                "\nCategorías: " + showCategories() +
                "\nPrecio: " + price + " " + currency;
    }
}