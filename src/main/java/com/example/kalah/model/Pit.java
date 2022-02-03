package com.example.kalah.model;

import com.example.kalah.model.enums.PitType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pit implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @NotNull
    @ManyToOne
    private Game game;

    @NotNull
    private Integer player;

    @NotNull
    @Min(1)
    @Max(14)
    private Integer pitNumber;

    @NotNull
    @Enumerated
    private PitType type;

    @Min(0)
    private Integer stoneCount;

    @Version
    private Long version;
}
