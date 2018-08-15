package com.sagarandcompany.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;
    @PersistenceContext
    EntityManager entityManager;

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person get(Long id) {
        return getSinglePerson(id);
    }

    public Person getSinglePerson(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriQuery = criteriaBuilder.createQuery(Person.class);

        Root person = criteriQuery.from(Person.class);

        criteriQuery.where(criteriaBuilder.equal(person.get("id"), id));


        Query query = entityManager.createQuery(criteriQuery);
        Person person1 = (Person) query.getSingleResult();
        return person1;
    }

    public void fetchMultipleColumn() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
        Root<Person> personRoot = criteriaQuery.from(Person.class);

        criteriaQuery.select(builder.array(personRoot.get("id"), personRoot.get("name")));

        criteriaQuery.where(builder.equal(personRoot.get("email"), "rahul@gmail.com"));


        List<Object[]> dataArray = entityManager.createQuery(criteriaQuery).getResultList();
        for (Object[] values : dataArray) {
            Integer id = (Integer) values[0];
            String name = (String) values[1];
            String email = (String) values[2];
            Integer salary = (Integer) values[3];
            System.out.println("id:" + id + ", name:" + name + " , email:" + email + " , salary:" + salary);
        }
    }
////select deptno,count(id) from person group by deptno;
    public void groupBy() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
        Root<Person> personRoot = criteriaQuery.from(Person.class);
        criteriaQuery.select(builder.array(personRoot.get("deptno"), builder.count(personRoot.get("id"))));

        criteriaQuery.groupBy(personRoot.get("deptno"));
        List<Object[]> dataArray = entityManager.createQuery(criteriaQuery).getResultList();
        for (Object[] values : dataArray) {
            Integer id = (Integer) values[0];
            Long count = (Long) values[1];
            System.out.println("id:" + id + ", count:" + count);
        }

    }
//select deptno,count(id) from person group by deptno having(count(id)>=2);
    public void groupByWithHaving() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
        Root<Person> personRoot = criteriaQuery.from(Person.class);
        criteriaQuery.select(builder.array(personRoot.get("deptno"), builder.count(personRoot.get("id"))));
        criteriaQuery.groupBy(personRoot.get("deptno"));


        Expression countExpression = builder.count(personRoot.get("id"));

        criteriaQuery.having(builder.gt(countExpression, 2));

        List<Object[]> dataArray = entityManager.createQuery(criteriaQuery).getResultList();
        for (Object[] values : dataArray) {
            Integer id = (Integer) values[0];
            Long count = (Long) values[1];
            System.out.println("id:" + id + ", count:" + count);
        }

    }
  //select p.id,p.email,p.name,p.salary,a.city from person p inner join address a where p.address_id=a.id;
    public void fetchJoinBasedData() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
        Root<Person> personRoot = criteriaQuery.from(Person.class);

        Join addressRoot = personRoot.join("address");


        criteriaQuery.select(builder.array(personRoot.get("id"), personRoot.get("name"), addressRoot.get("city")));
        criteriaQuery.where(builder.equal(personRoot.get("address"), addressRoot.get("id")));
//        criteriaQuery.where(builder.equal(addressRoot.get("id"), 2));
        List<Object[]> dataArray = entityManager.createQuery(criteriaQuery).getResultList();
        for (Object[] values : dataArray) {
            Long id = (Long) values[0];
            String name = (String) values[1];
            String city = (String) values[2];
            System.out.println("id:" + id + ", name:" + name + " , city:" + city);
        }

    }

    public Person getSelectedColumnSinglePerson(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriQuery = criteriaBuilder.createQuery(Person.class);
        Root person = criteriQuery.from(Person.class);

        criteriQuery.select(criteriaBuilder.construct(Person.class, person.get("name"), person.get("email")));

        criteriQuery.where(criteriaBuilder.equal(person.get("id"), id));

        Query query = entityManager.createQuery(criteriQuery);
        Person person1 = (Person) query.getSingleResult();
        return person1;
    }

    public void fetchSingleColumn() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
        Root<Person> personRoot = criteriaQuery.from(Person.class);
        criteriaQuery.select(personRoot.get("name").as(String.class));
//        criteriaQuery.where(builder.equal(personRoot.get("id"), "22"));
        List<String> nameList = entityManager.createQuery(criteriaQuery).getResultList();
        for (String name : nameList) {
            System.out.println(name);
        }
    }

    public void aggrigateOperation() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = builder.createQuery(Integer.class);
        Root<Person> studentRoot = criteriaQuery.from(Person.class);

        criteriaQuery.select(builder.max(studentRoot.get("salary").as(Integer.class)));

        Integer maxSalary = entityManager.createQuery(criteriaQuery).getSingleResult();
        System.out.println("maxSalary" + maxSalary);
    }

}
