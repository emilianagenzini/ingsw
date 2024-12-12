package com.ingsw.SpringServer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

@Repository
public interface utenteRepository extends CrudRepository<utente,Integer> {

    Optional<utente> findByEmailAndAcquirente(String email, boolean acquirente);


    @Query("SELECT u FROM utente u WHERE u.email = ?1 AND u.pwd = ?2 AND u.acquirente = ?3")
    utente findByEmailAndPwd(String email, String password, boolean acquirente);

}
