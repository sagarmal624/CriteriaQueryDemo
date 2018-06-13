package com.sagarandcompany.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping("/save")
    public Person save(@RequestBody Person person) {
        return personService.save(person);
    }

    @GetMapping("/get/{id}")
    public Person get(@PathVariable Long id) {
        return personService.get(id);
    }

}
