package com.ingsw.SpringServer.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class utenteDAO {
    @Autowired
    private utenteRepository repository;


    public utente save(utente utente) {
        return repository.save(utente);
    }
     public void delete(utente utente){
         repository.delete(utente);
     }
     public List<utente> getAllUtenti()
     {
         List<utente> utenti = new ArrayList<>();
         Streamable.of(repository.findAll())
                 .forEach(utenti::add);
         return utenti;

     }
    public boolean isUtentePresente(String email, boolean acquirente) {
        Optional<utente> utente = repository.findByEmailAndAcquirente(email, acquirente);
        return utente.isPresent();
    }
    public utente findByEmailAndPwd(String email, String password, boolean acquirente) {
        return repository.findByEmailAndPwd(email, password, acquirente);
    }




}
