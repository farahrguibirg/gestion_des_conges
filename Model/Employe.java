package Model;

public class Employe {
    private int idEmploye;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private double salaire;
    private Role role;
    private Poste poste;
    private int solde;

    // Constructeur
    public Employe(int idEmploye, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste) {
        this.idEmploye = idEmploye;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.salaire = salaire;
        this.role = role;
        this.poste = poste;
        this.solde = 25; // Valeur par d�faut pour le solde
    }

    // Getter et setter pour le solde
    public int getSolde() {
        return solde;
    }

    public void setSolde(int newSolde) {
        this.solde = newSolde;
    }

    // M�thode pour mettre � jour le solde
    public void updateSolde(int newSolde) {
        this.solde = newSolde;
    }

    // Getters et setters pour les autres attributs
    public int getId() {
        return idEmploye;
    }

    public void setId(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}
