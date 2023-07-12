package ru.skypro.lessons.courseworkspring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Table(name = "lot")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;
    @Max(64)
    @Min(3)
    private String title;
    @Max(4096)
    @Min(3)
    private String description;
    @Min(1)
    private int startPrice;
    @Min(1)
    private int bidPrice;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name="lot_id")
    List<Bid> bidList;
}
