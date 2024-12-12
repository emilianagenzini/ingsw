package com.ingsw.SpringServer.model;

public class LoginRequest {
    private String email;
    private String password;
    private boolean acquirente;

    // Costruttore
    public LoginRequest(String email, String password, boolean acquirente) {
        this.email = email;
        this.password = password;
        this.acquirente = acquirente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isAcquirente() {
        return acquirente;
    }

    public void setAcquirente(boolean acquirente) {
        this.acquirente = acquirente;
    }




}
