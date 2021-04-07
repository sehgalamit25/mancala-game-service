package com.mancala.game.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The type Pit.
 */
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pitIndex;

    @ManyToMany
    private Player owner;

    private Integer numOfStones;

    private Integer nextPitRef;

    private Integer oppPitRef;

}
