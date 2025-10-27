package EjemplosDomySax;

import java.util.ArrayList;
import java.util.List;

public class Book {
    public String id;
    public String title;
    public String author;
    // Anadimos la lista de autores
    public List<String> authors = new ArrayList<>();
    public int year;
    public double price;

    // Cambiamos el to string de author a authors
    @Override
    public String toString() {
        return String.format(
                "Book{id=%s, title=%s, authors=%s, year=%d, price=%.2f}",
                id, title, authors, year, price
        );
    }
}