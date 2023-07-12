package ru.skypro.lessons.courseworkspring.DTO;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.lessons.courseworkspring.model.Bid;
import ru.skypro.lessons.courseworkspring.model.Lot;

@Getter
@Setter
public class LotCRV {
    private Integer id;
    private String title;
    private String status;
    private Bid lastBidder;
    private int currentPrice;

    public static LotCRV fromLot(Lot lot) {
        LotCRV lotCRV = new LotCRV();
        lotCRV.setId(lot.getId());
        lotCRV.setStatus(lot.getStatus());
        lotCRV.setTitle(lot.getTitle());
        if (lot.getBidList().size() != 0) {
            lotCRV.setLastBidder(lot.getBidList().get(lot.getBidList().size() - 1));
        }
        lotCRV.setCurrentPrice(lot.getBidList().size()*lot.getBidPrice()+lot.getStartPrice());
        return lotCRV;
    }

    @Override
    public String toString() {
        return "LotCRV{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", lastBidder=" + lastBidder +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
