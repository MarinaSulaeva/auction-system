package ru.skypro.lessons.courseworkspring.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.skypro.lessons.courseworkspring.DTO.*;

import java.io.IOException;
import java.util.List;

public interface LotService {

    void createLot(CreatingLot creatingLot);

    List<LotDTO> getLotsByStatus(Integer page, String status);

    ResponseEntity<Resource> exportLotInFile() throws IOException;

    void stopBargaining(Integer id);

    void addBid(Integer id, String bidderName);

    void startBargaining(Integer id);

    FullLotDTO getFullLot(Integer id);

    FrequentBidInterface getTheFrequentBid(Integer id);

    BidDTO getTheFirstBid(Integer id);

    BidDTO getTheFrequentBid1(Integer id);

    BidDTO getTheFrequentBid2(Integer id);
}
