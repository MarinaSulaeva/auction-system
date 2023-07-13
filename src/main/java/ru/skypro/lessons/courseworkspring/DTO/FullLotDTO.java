package ru.skypro.lessons.courseworkspring.DTO;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.lessons.courseworkspring.model.Bid;
import ru.skypro.lessons.courseworkspring.model.Lot;

@Getter
@Setter

public class FullLotDTO {
    private Integer id;
    private String status;
    private String title;
    private String description;
    private int startPrice;
    private int bidPrice;
    private int currentPrice;
    private Bid lastBid;

    public static FullLotDTO fromLot(Lot lot) {
        FullLotDTO fullLotDTO = new FullLotDTO();
        fullLotDTO.setId(lot.getId());
        fullLotDTO.setStatus(lot.getStatus());
        fullLotDTO.setTitle(lot.getTitle());
        fullLotDTO.setDescription(lot.getDescription());
        fullLotDTO.setStartPrice(lot.getStartPrice());
        fullLotDTO.setBidPrice(lot.getBidPrice());
        if (lot.getBidList().size() != 0) {
            fullLotDTO.setLastBid(lot.getBidList().get(lot.getBidList().size()-1));
        }
        fullLotDTO.setCurrentPrice(lot.getBidList().size()*lot.getBidPrice()+lot.getStartPrice());
        return fullLotDTO;
    }

}
