package Model;

public class User {
    private int id;
    private String name;
    private String password;

    // Constructeur pour créer un utilisateur avec un ID, un nom, et un mot de passe
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    // Constructeur pour créer un utilisateur sans ID
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters et setters pour les attributs

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}