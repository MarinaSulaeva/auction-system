package ru.skypro.lessons.courseworkspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.courseworkspring.model.Lot;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotRepository extends CrudRepository <Lot, Integer> {


    @Override
    Optional<Lot> findById(Integer integer);

    @Override
    List<Lot> findAll();

}
