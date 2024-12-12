package com.ingsw.SpringServer.model;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AstaDAO {
    @Autowired
    private AstaRepository repository;
    @Autowired
    private OffertaRepository offertaRepository;
    @Autowired
    private  AstaTimer astaTimer;
    public Asta save(Asta asta) {

        return repository.save(asta); // Restituisce l'oggetto salvato
    }
    public void delete(Asta asta){
        repository.delete(asta);
    }
    public List<Asta> getAllAsta()
    {
        List<Asta> asta = new ArrayList<>();
        Streamable.of(repository.findAll())
                .forEach(asta::add);
        return asta;

    }

    public List<Asta> findByCategoria(String categoria , boolean stato) {
        return repository.findByCategoriaAndStato(categoria, stato);
    }
 public  List<Asta> findByVenditore(String venditore){
        return repository.findByVenditore(venditore);
}
    public List<Asta> findByInglese(boolean  inglese){
        return repository.findByInglese(inglese);
    }


    public List<Asta> getAsteConStatoTrueEIngleseFalse(boolean stato, boolean inglese) {
        return repository.findByStatoAndInglese(stato, inglese);
    }

    @Transactional
    public void cambiaStato(String nome, boolean stato) {
        Asta asta = repository.findByNome(nome)
                .orElseThrow(() -> new EntityNotFoundException("Asta non trovata"));
        asta.setStato(stato);
        repository.save(asta);
    }

    @Transactional
    public  void updateAsta(String nome, double base_asta){
        Asta asta = repository.findByNome(nome)
                .orElseThrow(() -> new EntityNotFoundException("Asta non trovata"));
        asta.setBaseAsta(base_asta);
        repository.save(asta);

    }

    @Transactional
    public  void updateDataFine(String nome, LocalDateTime dataFine){
      //  Asta asta = repository.findByNome(nome)
        //        .orElseThrow(() -> new EntityNotFoundException("Asta non trovata"));
       // asta.setDataFine(dataFine);
       // repository.save(asta);
           repository.aggiornaDataFine(nome, dataFine);
    }

    /*
    public void avviaTimerAsta(Asta asta) {
        Runnable onTimerEnd = () -> {
            asta.setStato(false);
            repository.save(asta);
            System.out.println("Asta " + asta.getNome() + " terminata.");
        };

        astaTimer.startTimer(asta.getNome(), onTimerEnd, asta.getTimer());
    }*/

    public AstaDAO(AstaRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 60000) // Ogni 60 secondi
    public void aggiornaStatoAste() {
        // Ottieni tutte le aste attive
        List<Asta> asteAttive = repository.findByStatoAndInglese(true, true);

        LocalDateTime now = LocalDateTime.now();
        for (Asta asta : asteAttive) {
            if (asta.getDataFine().isBefore(now)) {
                //asta.setStato(false);
                //repository.save(asta);
                repository.terminaAsta(asta.getNome());// Aggiorna lo stato nel database
                repository.selezionaVincitore(asta.getNome());
                //repository.rifiutaAltreOfferte(asta.getNome());
                offertaRepository.aggiornaStatoSingolaOffertaNonVincente(asta.getNome());

            }
        }
    }

}
