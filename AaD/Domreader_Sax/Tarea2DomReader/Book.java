package Tarea2DomReader;
public class Book {
    private final String id;
    private final String isbn;
    private final String title;
    private final String authors;    // String ya formateado: "Nombre (rol), Nombre2"
    private final String categories; // String formateado: "Cat1, Cat2"
    private final String year;
    private final String price;
    private final String currency;

    // Implementamos getters para acceder a la informacion par la implementación de métodos.
    public String getTitle() {
        return title;
    }
    public String getAuthors() {
        return authors;
    }
    public String getYear() {
        return year;
    }
    public String getPrice() {
        return price;
    }
    public String getCurrency() {
        return currency;
    }

    public Book(String id, String isbn, String title, String authors, String categories,
                String year, String price, String currency) {
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
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(id).append("] ").append(title).append(" (").append(year).append(")\n");
        sb.append("ISBN: ").append(isbn).append("\n");
        sb.append("Autor: ").append(authors).append("\n");
        sb.append("Categoria: ").append(categories).append("\n");
        sb.append("Año: ").append(year).append("\n");
        sb.append("Precio: ").append(price).append(" ").append(currency).append("\n");
        return sb.toString();
    }
}