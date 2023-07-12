package ru.skypro.lessons.courseworkspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrequentBid {
    private String bidderName;
    private LocalDateTime dateTime;

}
