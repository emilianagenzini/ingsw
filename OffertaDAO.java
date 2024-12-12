package com.ingsw.SpringServer.model;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OffertaDAO {

    @Autowired
    private OffertaRepository repository;


    public Offerta save(Offerta  offerta) {
        return repository.save(offerta);
    }


    public void delete(Offerta offerta){
        repository.delete(offerta);
    }


    public boolean verificaOfferta(String nomeUtente, String nomeAsta) {
        return repository.existsByNomeUtenteAndNomeAsta(nomeUtente, nomeAsta);
    }

public   List<Offerta> findOffertePerAsteCreateDaNomeUtente(String nomeUtente){
        return  repository.findOffertePerAsteCreateDaNomeUtente(nomeUtente);
}

    public   List<Offerta> findOfferteRifiutatePerAste(String nomeUtente){
        return  repository.findOfferteRifiutatePerAste(nomeUtente);
    }

public  boolean  existsById( Long id){
        return repository.existsById(id);
}

public void deleteById( Long id){
        repository.deleteById(id);
}
public  void aggiornaStatoOffertaARifiutata(Long id){
        repository.aggiornaStatoOffertaARifiutata(id);
}

public  void aggiornaStatoOfferta(Long id){
        repository.aggiornaStatoOfferta(id);
    }

    public  void updateStatoOfferta(Long id,int stato){
        repository.updateStatoOfferta(id,stato);
    }
    @Transactional
    public void rifiutaAltreOfferte(Long idOffertaAccettata, String nomeAsta) {


        // Aggiorna tutte le altre offerte relative alla stessa asta a "2" (rifiutate)
        repository.aggiornaStatoOfferteRifiutate(nomeAsta, idOffertaAccettata);

    }




}

