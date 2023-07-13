package ru.skypro.lessons.courseworkspring.DTO;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.lessons.courseworkspring.model.Bid;
import ru.skypro.lessons.courseworkspring.model.Lot;

@Getter
@Setter
public class LotCSV {
    private Integer id;
    private String title;
    private String status;
    private Bid lastBidder;
    private int currentPrice;

    public static LotCSV fromLot(Lot lot) {
        LotCSV lotCSV = new LotCSV();
        lotCSV.setId(lot.getId());
        lotCSV.setStatus(lot.getStatus());
        lotCSV.setTitle(lot.getTitle());
        if (lot.getBidList().size() != 0) {
            lotCSV.setLastBidder(lot.getBidList().get(lot.getBidList().size() - 1));
        }
        lotCSV.setCurrentPrice(lot.getBidList().size()*lot.getBidPrice()+lot.getStartPrice());
        return lotCSV;
    }

    @Override
    public String toString() {
        return id +
                ", " + title +
                ", " + status +
                ", " + (lastBidder!=null ? lastBidder.getBidderName() : "null") +
                ", " + currentPrice;
    }
}
