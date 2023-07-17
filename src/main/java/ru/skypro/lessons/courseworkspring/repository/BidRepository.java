package ru.skypro.lessons.courseworkspring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.courseworkspring.DTO.FrequentBidInterface;
import ru.skypro.lessons.courseworkspring.model.Bid;

import java.util.Optional;

@Repository
public interface BidRepository extends CrudRepository<Bid, Integer> {
    @Query(value = "SELECT bidder_name as bidderName, MAX (date_time) as dateTime " +
            "FROM bid WHERE lot_id=?1 " +
            "GROUP BY bidder_name ORDER BY count(bidder_name) DESC LIMIT 1",
            nativeQuery = true)
    Optional<FrequentBidInterface> findFrequentBidInterface(Integer id);

    @Query(value = "SELECT * FROM bid WHERE lot_id=?1 ORDER BY date_time LIMIT 1", nativeQuery = true)
    Optional<Bid> findFirstBid(Integer id);
}
