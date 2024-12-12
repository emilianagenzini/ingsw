package com.ingsw.SpringServer.controller;


import com.ingsw.SpringServer.model.LoginRequest;
import com.ingsw.SpringServer.model.utente;
import com.ingsw.SpringServer.model.utenteDAO;
import com.ingsw.SpringServer.model.utenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid; // Usa questo se stai usando Jakarta EE



import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utente")
public class utenteController {

    @Autowired
    private utenteDAO utenteDAO;
    @Autowired
    private com.ingsw.SpringServer.model.utenteRepository utenteRepository;

    @GetMapping("/utente/get-all")
    public List<utente> getAllUtenti(){
        System.out.println("Metodo getAllUtenti invocato");
    return  utenteDAO.getAllUtenti();
    }




    @PostMapping("/save")
    public ResponseEntity<utente> save(@Valid @RequestBody utente utente) {
        // Controlla se l'utente esiste già
        if (utenteDAO.isUtentePresente(utente.getEmail(),utente.isAcquirente())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
        }

        utente savedUtente = utenteDAO.save(utente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUtente);
    }

    @PostMapping("/login")
    public ResponseEntity<utente> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Verifica se l'utente esiste con le credenziali fornite
        utente existingUtente = utenteDAO.findByEmailAndPwd(loginRequest.getEmail(), loginRequest.getPassword(),loginRequest.isAcquirente());

        if (existingUtente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
        }

        return ResponseEntity.ok(existingUtente); // 200 OK con l'utente
    }

    @GetMapping("/cerca")
    public ResponseEntity<utente> cercaVenditore(@RequestParam String email) {
        // Trova il venditore in base all'email
        Optional<utente> venditore = utenteRepository.findByEmailAndAcquirente(email, false);

        // Se il venditore è presente, restituisci una risposta con lo stato 200 OK
        if (venditore.isPresent()) {
            return ResponseEntity.ok(venditore.get());
        } else {
            // Se il venditore non è trovato, restituisci una risposta con lo stato 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/cerca-acquirente")
    public ResponseEntity<utente> cercaAcquirente(@RequestParam String email) {
        Optional<utente> venditore = utenteRepository.findByEmailAndAcquirente(email, true);


        if (venditore.isPresent()) {
            return ResponseEntity.ok(venditore.get());
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
