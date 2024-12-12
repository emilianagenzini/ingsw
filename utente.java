package com.ingsw.SpringServer.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class utente {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String cognome;
    private String email;

    private String pwd;

    private boolean acquirente;



    public boolean isAcquirente() {
        return acquirente;
    }

    public void setAcquirente(boolean acquirente) {
        this.acquirente = acquirente;
    }



    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "utente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + pwd + '\'' +
                ", acquirente='" + acquirente + '\'' +
                '}';
    }
}
