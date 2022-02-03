package ru.geraskindenis.word_statistics.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geraskindenis.word_statistics.db.Person;

@Repository
public interface PersonRep extends CrudRepository<Person, Long> {
}
