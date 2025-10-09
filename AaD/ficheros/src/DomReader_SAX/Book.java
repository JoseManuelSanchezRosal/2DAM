package DomReader_SAX;
public class Book {
    public String id;
    public String title;
    public String author;
    public int year;
    public double price;
    @Override
    public String toString() {
        return String.format(
                "Book{id=%s, title=%s, author=%s, year=%d, price=%.2f}",
                id, title, author, year, price
        );
    }
}