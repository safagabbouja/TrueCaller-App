package com.example.gestioncontact;


public class Contact {
    private int id;  // Add id field
    private String nom;
    private String pseudo;
    private String numero;

    // Constructor without id (for new contacts)
    public Contact(String nom, String pseudo, String numero) {
        this.nom = nom;
        this.pseudo = pseudo;
        this.numero = numero;
    }

    // Constructor with id (for existing contacts)
    public Contact(int id, String nom, String pseudo, String numero) {
        this.id = id;
        this.nom = nom;
        this.pseudo = pseudo;
        this.numero = numero;
    }

    // Getter and setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Other getters and setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +  // Include id in toString
                ", nom='" + nom + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}

