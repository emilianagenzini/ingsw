package com.ingsw.SpringServer.controller;


import com.ingsw.SpringServer.model.*;
import jakarta.transaction.Transactional;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/asta")
public class AstaController {

    @Autowired
    private AstaDAO astaDAO;
    @Autowired
    private AstaRepository astaRepository;
    @Autowired
    private OffertaRepository offertaRepository;


    @PostMapping("/save")
    public ResponseEntity<Asta> save(@RequestBody Asta asta) {
        Asta savedAsta = astaDAO.save(asta);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedAsta);
    }

    @GetMapping("/get-all")
    public List<Asta> getAllAsta(){
        System.out.println("Metodo getAllAsta invocato");
        return  astaDAO.getAllAsta();
    }

    @GetMapping("/aste")
    public List<Asta> getAsteByCategoria(@RequestParam(required = false) String categoria ,@RequestParam boolean stato) {
        if (categoria != null) {
            return astaDAO.findByCategoria(categoria, stato);
        }
        return (List<Asta>) astaDAO.getAllAsta();
    }

    @GetMapping("/astaincrementale")
    public List<Asta> getAstaIncrementale(@RequestParam(required = false) Boolean inglese) {
        if (inglese ) {
            return astaDAO.findByInglese(true);
        }
        return (List<Asta>) astaDAO.getAllAsta();
    }

    @GetMapping("/aste-attive")
    public List<Asta> getAsteAttive(@RequestParam boolean stato,@RequestParam boolean inglese ) {
        return astaDAO.getAsteConStatoTrueEIngleseFalse(stato,inglese);
    }

    @PutMapping("/cambia-stato")
    public ResponseEntity<String> cambiaStatoAsta(  @RequestParam("nome") String nome, @RequestParam("stato") boolean stato  ) {
        try {
            astaDAO.cambiaStato(nome, stato);
            return ResponseEntity.ok("Asta aggiornata  con successo");
        } catch (Exception e) {
            e.printStackTrace();  // Log dell'eccezione
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore  " + e.getMessage());
        }
    }


     @PutMapping("/update-asta")
    public ResponseEntity<String> updateAsta(@RequestParam("nome") String nome , @RequestParam("base_asta") double base_asta){
         try {
             astaDAO.updateAsta(nome, base_asta);
             return ResponseEntity.ok("Asta aggiornata  con successo");
         } catch (Exception e) {
             e.printStackTrace();  // Log dell'eccezione
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore  " + e.getMessage());
         }
     }

 /*   @PostMapping("/start-timer")
    public ResponseEntity<?> startTimer(@RequestParam("nome") String astaId) {
        Asta asta = astaRepository.findByNome(astaId)
                .orElseThrow(() -> new RuntimeException("Asta non trovata"));
        astaDAO.avviaTimerAsta(asta);
        return ResponseEntity.ok("Timer avviato per l'asta: " + astaId);
    }

  */

 @Transactional
 @PostMapping("/start-timer")
 public ResponseEntity<?> startTimer(@RequestParam("nome") String astaId) {
     // Cerca l'asta nel database
     Optional<Asta> optionalAsta = astaRepository.findByNome(astaId);

     if (optionalAsta.isEmpty()) {
         // Se l'asta non esiste, restituisci un errore 404 Not Found
         return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body("Asta con nome '" + astaId + "' non trovata.");
     }

     // Se l'asta esiste, avvia il timer
     Asta asta = optionalAsta.get();
    // astaDAO.avviaTimerAsta(asta);
     LocalDateTime dataFine = LocalDateTime.now().plusHours(1);
     // Timer di 1 ora
     // asta.setDataFine(dataFine);
     /// astaDAO.updateDataFine(astaId , dataFine);

     astaRepository.aggiornaDataFine(astaId,dataFine); // Usando SQL nativo ora

    // astaRepository.save(asta); // Salva la data di fine nel database
     // Restituisci una risposta di successo
     return ResponseEntity.ok("Timer avviato per l'asta: " + astaId);
 }





    @GetMapping("/get-timer")
public ResponseEntity<TimerResponse> getTimer(@RequestParam("nome") String astaId) {
    Optional<Asta> optionalAsta = astaRepository.findByNome(astaId);

    if (optionalAsta.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new TimerResponse("errore", "Asta non trovata"));
    }

    Asta asta = optionalAsta.get();
    LocalDateTime dataFine = asta.getDataFine();
    if (dataFine == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new TimerResponse("errore", "Il timer non è stato avviato"));
    }

    LocalDateTime now = LocalDateTime.now();
    Duration duration = Duration.between(now, dataFine);

    if (duration.isNegative() || duration.isZero()) {
       // astaRepository.terminaAsta(astaId);
        //offertaRepository.selezionaVincitore(astaId);
        astaDAO.aggiornaStatoAste();
        return ResponseEntity.ok(new TimerResponse("terminata", "Terminata"));
    }

    long minutes = duration.toMinutes();
    long seconds = duration.getSeconds() % 60;

    String timeRemaining = String.format("%02d:%02d", minutes, seconds);
    return ResponseEntity.ok(new TimerResponse("attivo", timeRemaining));
}



}
