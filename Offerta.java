package com.ingsw.SpringServer.model;


import jakarta.persistence.*;

@Entity
public class Offerta {

        String nomeUtente;
        Double prezzo ;
        String nomeAsta ;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    public Offerta() {
    }

    public int getStato() {
            return stato;
        }

        public void setStato(int stato) {
            this.stato = stato;
        }

        int stato;
        /* può assumere tre valori : 1 offerta accettata 2 offerta rifiutata 0 offerta in sospeso */

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }



        public Offerta(String nomeAsta, Double prezzo, String utente ,int stato) {
            this.nomeUtente = utente;
            this.prezzo = prezzo;
            this.nomeAsta = nomeAsta;
            this.stato = stato;

        }

        public String getUtente() {
            return nomeUtente;
        }

        public void setUtente(String utente) {
            this.nomeUtente = utente;
        }

        public Double getPrezzo() {
            return prezzo;
        }

        public void setPrezzo(Double prezzo) {
            this.prezzo = prezzo;
        }

        public String getAsta() {
            return nomeAsta;
        }

        public void setAsta(String nomeAsta) {
            this.nomeAsta = nomeAsta;
        }



}


