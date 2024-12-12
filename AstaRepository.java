package com.ingsw.SpringServer.model;

import jakarta.transaction.Transactional;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AstaRepository extends CrudRepository<Asta,Integer> {
    List<Asta> findByCategoriaAndStato(String categoria, boolean stato);
    List<Asta> findByInglese(Boolean inglese);

    List<Asta> findByVenditore(String venditore);
    List<Asta> findByStatoAndInglese(boolean stato, boolean inglese);

    @Transactional
    @Modifying
    @Query("UPDATE Asta a SET a.stato = :stato WHERE a.nome = :nomeAsta")
    void aggiornaStato(@Param("nome")String nomeAsta, @Param("stato") boolean stato);


    Optional<Asta> findByNome(String nome);



    @Modifying
    @Transactional
    @Query ("UPDATE Asta a SET a.dataFine = :dataFine WHERE a.nome = :nome")
    void aggiornaDataFine(@Param("nome") String nome, @Param("dataFine") LocalDateTime dataFine);

    @Modifying
    @Transactional
    @Query(value = "UPDATE asta SET data_fine = NOW() + INTERVAL '1 hour' WHERE nome = :nome", nativeQuery = true)
    void aggiornaDataFine(@Param("nome") String nome);

    @Transactional
    @Modifying
    @Query("UPDATE Asta a SET a.stato = false WHERE a.nome = :nome")
    void terminaAsta(@Param("nome") String nome);

    @Transactional
    @Modifying
    @Query(value = "UPDATE offerta o SET stato = 1 WHERE o.nome_asta = :nomeAsta AND o.prezzo = " +
            "(SELECT MAX(prezzo) FROM offerta WHERE nome_asta = :nomeAsta)", nativeQuery = true)
    void selezionaVincitore(@Param("nomeAsta") String nomeAsta);



}


