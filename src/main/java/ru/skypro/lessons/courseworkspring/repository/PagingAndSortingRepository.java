package ru.skypro.lessons.courseworkspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.lessons.courseworkspring.model.Lot;

public interface PagingAndSortingRepository extends org.springframework.data.repository.PagingAndSortingRepository<Lot, Integer> {

    @Query("SELECT l FROM Lot l " +
            "WHERE l.status =:status")
    Page<Lot> findAllByStatus(Pageable pageable, String status);
}
