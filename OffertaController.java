package com.ingsw.SpringServer.controller;

import com.ingsw.SpringServer.model.Asta;
import com.ingsw.SpringServer.model.AstaDAO;
import com.ingsw.SpringServer.model.Offerta;
import com.ingsw.SpringServer.model.OffertaDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/offerta")
public class OffertaController {

    @Autowired
    private OffertaDAO offertaDAO;

    @Autowired
    private AstaDAO astaDAO;

    @PostMapping("/save")
    public ResponseEntity<Offerta> save(@RequestBody Offerta offerta) {
       // Offerta savedOfferta = offertaDAO.save(offerta);
       //return ResponseEntity.status(HttpStatus.CREATED).body(savedOfferta);

        /* Verifica se l'offerta esiste già
        if (offertaDAO.verificaOfferta(offerta.getNomeUtente(), offerta.getNomeAsta())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();  // 409 Conflict
        }

        // Salva l'offerta se non esiste
        Offerta savedOfferta = offertaDAO.save(offerta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOfferta);
    */
        if (offertaDAO.verificaOfferta(offerta.getUtente(), offerta.getAsta())) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }


        Offerta savedOfferta = offertaDAO.save(offerta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOfferta);

    }


    @PostMapping("/save-inglese")
    public ResponseEntity<Offerta> saveInglese(@RequestBody Offerta offerta) {
        Offerta savedOfferta = offertaDAO.save(offerta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOfferta);

    }

  @GetMapping("/get-offerta")
  public ResponseEntity<Boolean> checkOfferta(@RequestParam String utente, @RequestParam String asta) {

      if (utente == null || utente.isEmpty() || asta == null || asta.isEmpty()) {
          return ResponseEntity.badRequest().body(false);
      }

      try {
          boolean exists = offertaDAO.verificaOfferta(utente, asta );
          return ResponseEntity.ok(exists);
      } catch (Exception e) {
          // Gestisci eventuali eccezioni (ad esempio errori nel database)

          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
      }
  }

    @GetMapping("/get-offerte-per-aste-create")
    public ResponseEntity<List<Offerta>> getOffertePerAsteCreate(@RequestParam String utente) {
        try {
            // Recupera tutte le aste create dall'utente
            List<Asta> aste = astaDAO.findByVenditore(utente);

            // Se l'utente non ha aste, restituisci una lista vuota
            if (aste.isEmpty()) {
                return ResponseEntity.ok(new ArrayList<>());
            }

            // Recupera tutte le offerte per le aste create dall'utente
            List<Offerta> offerte = offertaDAO.findOffertePerAsteCreateDaNomeUtente(utente);

            // Restituisci le offerte trovate
            return ResponseEntity.ok(offerte);
        } catch (Exception e) {
            // Gestisci gli errori
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-offerte-rifiutate")
    public ResponseEntity<List<Offerta>> getOfferteRifiutate(@RequestParam String utente) {
        try {


            // Recupera tutte le offerte per le aste create dall'utente
            List<Offerta> offerte = offertaDAO.findOfferteRifiutatePerAste(utente);

            // Restituisci le offerte trovate
            return ResponseEntity.ok(offerte);
        } catch (Exception e) {
            // Gestisci gli errori
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> eliminaOfferta(@PathVariable Long id) {
        // Verifica se l'offerta esiste
        if (offertaDAO.existsById(id)) {
            offertaDAO.deleteById(id);
            return ResponseEntity.ok("Offerta eliminata con successo");
        } else {
            return ResponseEntity.status(404).body("Offerta non trovata");
        }
    }

    @PutMapping("/rifiuta-offerta/{idOfferta}")
    public ResponseEntity<String> rifiutaOfferta(@PathVariable Long idOfferta) {
        try {
            offertaDAO.aggiornaStatoOffertaARifiutata(idOfferta);
            return ResponseEntity.ok("Offerta rifiutata con successo");
        } catch (Exception e) {
            e.printStackTrace();  // Log dell'eccezione
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il rifiuto dell'offerta: " + e.getMessage());
        }
    }

    @PutMapping("/cambia-stato/{idOfferta}")
    public ResponseEntity<String> cambiaStatoOfferta(@PathVariable Long idOfferta ) {
        try {
            offertaDAO.aggiornaStatoOfferta(idOfferta);
            return ResponseEntity.ok("Offerta aggiornata  con successo");
        } catch (Exception e) {
            e.printStackTrace();  // Log dell'eccezione
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il rifiuto dell'offerta: " + e.getMessage());
        }
    }
    @PutMapping("/update-stato/{idOfferta}")
    public ResponseEntity<String> UpdateStatoOfferta(@PathVariable Long idOfferta, @Param("stato") int stato) {
        try {
            offertaDAO.updateStatoOfferta(idOfferta,stato);
            return ResponseEntity.ok("Offerta aggiornata  con successo");
        } catch (Exception e) {
            e.printStackTrace();  // Log dell'eccezione
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il rifiuto dell'offerta: " + e.getMessage());
        }
    }

    @PutMapping("/rifiuta-altre-offerte/{idOfferta}")
    public ResponseEntity<Void> rifiutaAltreOfferte(@PathVariable Long idOfferta, @RequestParam String nomeAsta) {
        System.out.println("ID Offerta ricevuto: " + idOfferta);
        System.out.println("Nome Asta ricevuto: " + nomeAsta);

        offertaDAO.rifiutaAltreOfferte(idOfferta, nomeAsta);
        return ResponseEntity.ok().build();
    }


}
