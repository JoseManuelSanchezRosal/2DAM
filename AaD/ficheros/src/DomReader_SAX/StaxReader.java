package DomReader_SAX;

import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StaxReader {

    public static List<Book> read(File xmlFile) throws Exception {
        XMLInputFactory f = XMLInputFactory.newInstance();
        XMLEventReader r = f.createXMLEventReader(new FileInputStream(xmlFile));

        List<Book> books = new ArrayList<>();
        Book current = null;
        String currentTag = null;

        while (r.hasNext()) {
            XMLEvent ev = r.nextEvent();

            if (ev.isStartElement()) {
                String name = ev.asStartElement().getName().getLocalPart();
                currentTag = name;
                if ("book".equals(name)) {
                    current = new Book();
                    Iterator<?> it = ev.asStartElement().getAttributes();
                    while (it.hasNext()) {
                        Attribute a = (Attribute) it.next();
                        if ("id".equals(a.getName().getLocalPart())) current.id = a.getValue();
                    }
                }
            } else if (ev.isCharacters()) {
                if (current != null && currentTag != null) {
                    String text = ev.asCharacters().getData().trim();
                    if (!text.isEmpty()) {
                        switch (currentTag) {
                            case "title":  current.title  = text; break;
                            case "author": current.author = text; break;
                            case "year":   current.year   = Integer.parseInt(text); break;
                            case "price":  current.price  = Double.parseDouble(text); break;
                        }
                    }
                }
            } else if (ev.isEndElement()) {
                String name = ev.asEndElement().getName().getLocalPart();
                if ("book".equals(name) && current != null) {
                    books.add(current);
                    current = null;
                }
                currentTag = null;
            }
        }
        return books;
    }
}