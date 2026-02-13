public class Usuario {
    private int id;
    private String username;
    private String password;

    public Usuario(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean verificarPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}