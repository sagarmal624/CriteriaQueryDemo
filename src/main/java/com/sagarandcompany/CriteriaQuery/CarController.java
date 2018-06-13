package com.sagarandcompany.CriteriaQuery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CarController {
    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/car")
    public Object getData() {
        return findCarsByYearWithNamedStored();
    }

    private Object findCarsByYearWithNamedStored() {
        StoredProcedureQuery findByYearProcedure =
                entityManager.createNamedStoredProcedureQuery("findByYearProcedure");

        StoredProcedureQuery storedProcedure =
                findByYearProcedure.setParameter("p_year", 2015);

        List<Object> list = storedProcedure.getResultList();
        return list.stream().collect(Collectors.toList());

    }

}
