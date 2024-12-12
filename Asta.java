package com.ingsw.SpringServer.model;

import jakarta.persistence.*;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "asta")
public class Asta {
private String venditore;
private String categoria;
private boolean inglese;

private String descrizione;

@Column(name = "nome")
private String  nome;
private double baseAsta;

    private boolean stato; // true = attiva, false = conclusa
    private double prezzoCorrente;
    private Long timer; // in secondi
    private double sogliaRialzo; // default 10€

    public LocalDateTime getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDateTime dataFine) {
        this.dataFine = dataFine;
    }


    @Column(name = "data_fine")
    private LocalDateTime dataFine;




    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }

    public double getSogliaRialzo() {
        return sogliaRialzo;
    }

    public void setSogliaRialzo(double sogliaRialzo) {
        this.sogliaRialzo = sogliaRialzo;
    }

    public boolean isStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public double getPrezzoCorrente() {
        return prezzoCorrente;
    }

    public void setPrezzoCorrente(double prezzoCorrente) {
        this.prezzoCorrente = prezzoCorrente;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    public String getVenditore() {
        return venditore;
    }

    public void setVenditore(String venditore) {
        this.venditore = venditore;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isInglese() {
        return inglese;
    }

    public void setInglese(boolean inglese) {
        this.inglese = inglese;
    }

    public double getBaseAsta() {
        return baseAsta;
    }

    public void setBaseAsta(double baseAsta) {
        this.baseAsta = baseAsta;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


}
