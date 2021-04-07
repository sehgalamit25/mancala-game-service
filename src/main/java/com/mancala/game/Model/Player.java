package com.mancala.game.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;



/**
 * The type Player.
 */
@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "player_name", nullable = false)
    private String playername;
    @ManyToMany
    private List<Pit> pitSet;
}
