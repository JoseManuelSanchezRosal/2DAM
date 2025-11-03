package ExamenCodigoAaD;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Ejercicio7 {
    public static void main(String[] args) throws Exception {

        SAXParser p = SAXParserFactory.newInstance().newSAXParser();
        p.parse(new File("ExamenCodigoAaD/ejercicio7.xml"), new H());
    }
    static class H extends DefaultHandler {
        boolean inPrice=false, eur=false;
        StringBuilder sb=new StringBuilder();
        int sum=0;

        public void startElement(String u,String l,String q,Attributes a){
            if("price".equals(q)){
                inPrice=true; sb.setLength(0);
                eur=("EUR".equals(a.getValue("currency")));
            }
        }
        public void characters(char[] c,int s,int n){ if(inPrice) sb.append(c,s,n); }
        public void endElement(String u,String l,String q){
            if("price".equals(q)){
                if(eur) sum=Integer.parseInt(sb.toString().trim());
                inPrice=false;
            }
            if("library".equals(q)) System.out.println(sum);
        }
    }
}
