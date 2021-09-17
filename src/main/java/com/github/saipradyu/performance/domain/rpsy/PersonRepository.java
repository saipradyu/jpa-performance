package com.github.saipradyu.performance.domain.rpsy;

import com.github.saipradyu.performance.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface PersonRepository extends CrudRepository<Person, Long> {

}
