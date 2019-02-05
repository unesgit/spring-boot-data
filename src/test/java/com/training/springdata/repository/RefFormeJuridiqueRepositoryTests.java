/**
 * 
 */
package com.training.springdata.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.training.springdata.model.RefFormeJuridique;

/**
 * @author y.nadir
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RefFormeJuridiqueRepositoryTests {
    
    @Autowired
    RefFormeJuridiqueRepository refFormeJuridiqueRepository;
    
    Runnable r;
    
    @Before
    public void beforeEachTest() throws Exception {
        System.out.println("before");
    }
    
    @Test
    public void displayAllTest() {
        refFormeJuridiqueRepository.findAll().forEach(refFormeJuridique -> {
            System.out.println(refFormeJuridique.getId());
            System.out.println(refFormeJuridique.getLibelle());
        });
    }
    
    @Test
    public void filterByIdEqualsOneTest() {
        assertThat(refFormeJuridiqueRepository.findAll().stream()
                .filter(refFormeJuridique -> refFormeJuridique.getId().equals(Long.valueOf(1))).collect(Collectors.toList()).size())
                        .isEqualTo(1);
        ;
    }
    
    @Test
    public void findByIdTest() {
        refFormeJuridiqueRepository.findById(1l)
                .ifPresent(refForme -> assertThat(refForme.getLibelle().equals("La Societe en nom collectif SNC")));
    }
    
    @Test
    public void findByLibelleTest() {
        assertThat(refFormeJuridiqueRepository.findByLibelle("La Societe en nom collectif SNC").size() == 1);
    }
    
    /* Find by Libelle */
    @Test
    public void findByLibellePagingTest() {
        assertThat(refFormeJuridiqueRepository.findByLibelle("La Societe en nom collectif SNC", PageRequest.of(0, 1)).getSize() == 1);
    }
    
    @Test
    public void findByLibelleLikeStartsTest() {
        assertThat(refFormeJuridiqueRepository.findByLibelleLike("La Societe").size() == 1);
    }
    
    @Test
    public void findByLibelleLikeEndsTest() {
        assertThat(refFormeJuridiqueRepository.findByLibelleLike("%collectif SNC").size() == 1);
    }
    
    @Test
    public void findByLibelleCustomQueryTest() {
        assertThat(refFormeJuridiqueRepository.findByLibelleCustom("La Societe en nom collectif SNC").size() == 1);
    }
    
    @Test
    @Transactional(readOnly = true)
    public void findByLibelleStreamTest() {
        try (Stream<RefFormeJuridique> customers = refFormeJuridiqueRepository.findByLibelleStream("La Societe en nom collectif SNC")) {
            List<Long> ids = customers.map(forme -> forme.getId()).collect(Collectors.toList());
            assertThat(ids.size() == 1);
        }
        
    }
    
    @Test
    public void readAllAsync() throws InterruptedException, ExecutionException {
        CompletableFuture<List<RefFormeJuridique>> future = refFormeJuridiqueRepository.readAllBy()
                .thenApply(this::doSomethingWithFormeJuridiques);
        
        while (!future.isDone()) {
            TimeUnit.MILLISECONDS.sleep(500);
        }
        
        List<RefFormeJuridique> processedCustomers = future.get();
        assertThat(processedCustomers.get(0).getLibelle().equals("Modified. La Societe en nom collectif SNC"));
    }
    
    public List<RefFormeJuridique> doSomethingWithFormeJuridiques(List<RefFormeJuridique> refFormeJuridiqueList) {
        refFormeJuridiqueList.stream().forEach((forme) -> forme.setLibelle("Modified. " + forme.getLibelle()));
        return refFormeJuridiqueList;
    }
    
    @Test
    public void dateCreationLessThanTest() {
        assertThat(refFormeJuridiqueRepository.findByDateCreationLessThan(new Date()).size() == 1);
    }
    
    @Test
    public void findAllPaginationTest() {
        assertThat(refFormeJuridiqueRepository.findAll(PageRequest.of(0, 1)).getSize() == 1);
    }
    
}
