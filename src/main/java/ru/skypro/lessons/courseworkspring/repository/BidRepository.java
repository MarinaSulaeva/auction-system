package ru.skypro.lessons.courseworkspring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.courseworkspring.DTO.FrequentBid;
import ru.skypro.lessons.courseworkspring.DTO.FrequentBidInterface;
import ru.skypro.lessons.courseworkspring.model.Bid;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends CrudRepository<Bid, Integer> {

    @Query(value = "SELECT new ru.skypro.lessons.courseworkspring.DTO." +
            "FrequentBid(bidder_name, MAX (date_time)) " +
            "FROM bid WHERE lot_id=?1 " +
            "GROUP BY bidder_name ORDER BY count(bidder_name) DESC LIMIT 1",
            nativeQuery = true)
    Optional<FrequentBid> findFrequentBid(Integer id);

    @Query("SELECT new ru.skypro.lessons.courseworkspring.DTO." +
            "FrequentBid(b.bidderName , MAX (b.dateTime)) " +
            "FROM Bid  b JOIN FETCH Lot l WHERE l.id=?1 AND l.bidList=b " +
            "GROUP BY b.bidderName ORDER BY COUNT(b.bidderName) DESC")
    List<FrequentBid> findFrequentBid2(Integer id);




    @Query(value = "SELECT bidder_name, MAX (date_time) " +
            "FROM bid WHERE lot_id=?1 " +
            "GROUP BY bidder_name ORDER BY count(bidder_name) DESC LIMIT 1",
            nativeQuery = true)
    Optional<FrequentBidInterface> findFrequentBidInterface(Integer id);


    @Query(value = "SELECT * FROM bid WHERE lot_id=?1 ORDER BY date_time LIMIT 1", nativeQuery = true)
    Optional<Bid> findFirstBid(Integer id);


}
