package com.training.springdata.repository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.training.springdata.model.RefFormeJuridique;

/**
 * @author y.nadir
 *
 */
public interface RefFormeJuridiqueRepository extends JpaRepository<RefFormeJuridique, Long> {
    List<RefFormeJuridique> findByLibelle(String libelle);
    
    List<RefFormeJuridique> findByLibelleLike(String libelle);
    
    List<RefFormeJuridique> findByDateCreationLessThan(Date date);
    
    Page<RefFormeJuridique> findByLibelle(String libelle, Pageable page);
    
    @Query("Select f from RefFormeJuridique f where f.libelle=(:lib)")
    List<RefFormeJuridique> findByLibelleCustom(@Param("lib") String libelle);
    
    @Query("Select f from RefFormeJuridique f where f.libelle=(:lib)")
    Stream<RefFormeJuridique> findByLibelleStream(@Param("lib") String libelle);
    
    @Async
    CompletableFuture<List<RefFormeJuridique>> readAllBy();
    
}
