package Tarea2DOMySAXReader.Tarea2SAXReader;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String id;
    private String isbn;
    private String title;
    private List<String> authors;
    private List<String> roles;
    private List<String> categories;
    private int year;
    private double price;
    private String currency;

    public Book() {
        this.authors = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    // Getters y setters
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getIsbn(){
        return isbn;
    }
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public List<String> getAuthors(){
        return authors;
    }
    public void addAuthor(String author){
        this.authors.add(author);
    }

    public List<String> getRoles(){
        return roles;
    }
    public void addRole(String role){
        this.roles.add(role != null ? role : "");
    }
    public List<String> getCategories(){
        return categories;
    }
    public void addCategory(String category){
        this.categories.add(category);
    }
    public int getYear(){
        return year;
    }
    public void setYear(int year){
        this.year = year;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public String getCurrency(){
        return currency;
    }
    public void setCurrency(String currency){
        this.currency = currency;
    }

    // Métodos de presentación
    public String showAuthors() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            sb.append(authors.get(i));
            if (!roles.get(i).isEmpty())
                sb.append(" (").append(roles.get(i)).append(")");
            if (i < authors.size() - 1)
                sb.append(", ");
        }
        return sb.toString();
    }
    public String showCategories() {
        return String.join(", ", categories);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%d)\nISBN: %s\nAutores: %s\nCategorías: %s\nPrecio: %.2f %s\n",
                id, title, year, isbn, showAuthors(), showCategories(), price, currency);
    }
}
