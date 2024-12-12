package com.ingsw.SpringServer.model;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffertaRepository  extends CrudRepository<Offerta,Integer> {


  @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END " +
          "FROM Offerta o " +
          "WHERE o.nomeUtente = :nomeUtente AND o.nomeAsta = :nomeAsta AND o.stato <> 3")
  boolean existsByNomeUtenteAndNomeAsta(String nomeUtente, String nomeAsta);
  @Query("SELECT o FROM Offerta o JOIN Asta a ON o.nomeAsta = a.nome WHERE a.venditore = :nomeUtente AND (o.stato = 0 OR o.stato = 5)" )
  List<Offerta> findOffertePerAsteCreateDaNomeUtente(@Param("nomeUtente") String nomeUtente);

    boolean existsById(Long id);
    void deleteById(Long id);

  @Transactional
  @Modifying
  @Query("UPDATE Offerta o SET o.stato = 2 WHERE o.id = :id")
  void aggiornaStatoOffertaARifiutata(@Param("id") Long id);

  @Query("SELECT o FROM Offerta o  WHERE o.nomeUtente = :nomeUtente AND (o.stato = 2 OR o.stato = 1 OR o.stato = 5 )")
  List<Offerta> findOfferteRifiutatePerAste(@Param("nomeUtente") String nomeUtente);


  @Transactional
  @Modifying
  @Query("UPDATE Offerta o SET o.stato = 3 WHERE o.id = :id")
  void aggiornaStatoOfferta(@Param("id") Long id);


  @Transactional
  @Modifying
  @Query("UPDATE Offerta o SET o.stato = :stato WHERE o.id = :id")
  void updateStatoOfferta(@Param("id") Long id, @Param("stato") int stato);

  @Transactional
  @Modifying
  @Query("UPDATE Offerta o SET o.stato = 2 WHERE o.nomeAsta = :nomeAsta AND o.id <> :idOffertaAccettata")
  void aggiornaStatoOfferteRifiutate(@Param("nomeAsta") String nomeAsta, @Param("idOffertaAccettata") Long idOffertaAccettata);

  @Transactional
  @Modifying
  @Query(value = "UPDATE Offerta o " +
          "SET o.stato = 5 " +
          "WHERE o.nome_asta = :nomeAsta " +
          "AND o.id_utente != (" +
          "   SELECT o2.id_utente " +
          "   FROM Offerta o2 " +
          "   WHERE o2.nome_asta = :nomeAsta " +
          "   ORDER BY o2.prezzo DESC " +
          "   LIMIT 1" +
          ")", nativeQuery = true)
  void aggiornaStatOfferteNonVincitori(@Param("nomeAsta")String nomeAsta);

  @Transactional
  @Modifying
  @Query(value = "UPDATE Offerta " +
          "SET stato = 5 " +
          "WHERE id IN (" +
          "    SELECT id " +
          "    FROM (" +
          "        SELECT o.id " +
          "        FROM Offerta o " +
          "        WHERE o.nome_asta = :nomeAsta " +
          "          AND o.nome_utente != (" +
          "              SELECT nome_utente " +
          "              FROM Offerta " +
          "              WHERE nome_asta = :nomeAsta " +
          "              ORDER BY prezzo DESC " +
          "              LIMIT 1" +
          "          ) " +
          "        ORDER BY o.prezzo DESC " +
          "        LIMIT 1" +
          "    ) subquery" +
          ")", nativeQuery = true)
  void aggiornaStatoSingolaOffertaNonVincente(@Param("nomeAsta") String nomeAsta);




}
