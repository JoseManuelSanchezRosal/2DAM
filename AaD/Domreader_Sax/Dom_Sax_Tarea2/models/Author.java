package Dom_Sax_Tarea2.models;

public class Author {
    private String name;
    private String role;

    public Author() {
    }

    public Author(String name, String role) {
        this.name = name;
        this.role = role;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.role + ")";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}