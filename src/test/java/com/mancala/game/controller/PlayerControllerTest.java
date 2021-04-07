package com.mancala.game.controller;

import com.google.gson.Gson;
import com.mancala.game.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PlayerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private Player player1;
    private Player player2;
    private List<Player> players;
    private String json;

    @BeforeEach
    public void init() {
        players = new ArrayList<>();

        player1 = Player.builder().id(1L).playername("Rajesh").build();
        player2 = Player.builder().id(2L).playername("Mahesh").build();

        players.add(player1);
        players.add(player2);

        json = new Gson().toJson(players);
    }

    @Test
    public void getPlayerTest() throws Exception {
        mockMvc.perform(get("/player"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addPlayerTest() throws Exception {
        mockMvc.perform(post("/player")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
