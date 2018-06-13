package com.sagarandcompany.CriteriaQuery;

import javax.persistence.*;

@Entity
@Table(name = "CAR")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "findByYearProcedure",
                procedureName = "FIND_CAR_BY_YEAR",
                resultClasses = {Car.class},
                parameters = {
                        @StoredProcedureParameter(
                                name = "p_year",
                                type = Integer.class,
                                mode = ParameterMode.IN)})
})
public class Car {

    private long id;
    private String model;
    private Integer year;

    public Car(String model, Integer year) {
        this.model = model;
        this.year = year;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Car() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public long getId() {
        return id;
    }

    @Column(name = "MODEL")
    public String getModel() {
        return model;
    }

    @Column(name = "YEAR")
    public Integer getYear() {
        return year;
    }

    // standard setter methods
}