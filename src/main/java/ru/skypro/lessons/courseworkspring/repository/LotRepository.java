package ru.skypro.lessons.courseworkspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.courseworkspring.model.Lot;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotRepository extends JpaRepository<Lot, Integer> {
    @Override
    Optional<Lot> findById(Integer integer);

    @Override
    List<Lot> findAll();

    @Query("SELECT l FROM Lot l " +
            "WHERE l.status =:status")
    Page<Lot> findAllByStatus(Pageable pageable, String status);

}
