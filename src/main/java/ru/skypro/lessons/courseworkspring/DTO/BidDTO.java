package ru.skypro.lessons.courseworkspring.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skypro.lessons.courseworkspring.model.Bid;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BidDTO {
    private Integer id;
    private String bidderName;
    private LocalDateTime dateTime;

    public BidDTO(String bidderName) {
        this.bidderName = bidderName;
        this.dateTime = LocalDateTime.now();
    }

    public static BidDTO formBid(Bid bid) {
        BidDTO bidDTO = new BidDTO();
        bidDTO.setId(bid.getId());
        bidDTO.setBidderName(bid.getBidderName());
        bidDTO.setDateTime(LocalDateTime.now());
        return bidDTO;
    }

}
