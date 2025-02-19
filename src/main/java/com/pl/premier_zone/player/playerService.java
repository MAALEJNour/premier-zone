package com.pl.premier_zone.player;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class playerService {

    private final PlayerRepository playerRepository ;

    public playerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
    public List<Player> getPlayers () {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersFromTeam(String teamName) {
        return playerRepository.findAll().stream().filter(
                player -> teamName.equals(player.getTeam())).collect(Collectors.toList());
    }

    public List<Player> getPlayersByName(String searchText) {
        return playerRepository.findAll().stream().
                filter(player ->
                        player.getName().toLowerCase().contains(searchText.toLowerCase())
                )
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByPos(String searchText) {
         return playerRepository.findAll().stream().filter(
                 player -> player.getPos().toLowerCase().contains(searchText.toLowerCase())
         ).collect(Collectors.toList());
    }

    public List<Player> getPlayersByNation(String searchText) {
        return playerRepository.findAll().stream().filter(
                player -> player.getNation().toLowerCase().contains(searchText.toLowerCase())
        ).collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPos(String team , String position) {
        return playerRepository.findAll().stream().filter(
                player -> team.equals(player.getTeam())  && position.equals(player.getPos())
                )
        .collect(Collectors.toList());
    }

    public Player addPlayer (Player player) {
        playerRepository.save(player);
        return player ;
    }

    public Player updatePlayer (Player player) {
          Optional<Player> playerOptional = playerRepository.findByName(player.getName());
          if (playerOptional.isPresent()) {
              Player player1 = playerOptional.get();
              player1.setName(player.getName());
              player1.setTeam(player.getTeam());
              player1.setPos(player.getPos());
              player1.setNation(player.getNation());
              playerRepository.save(player1);
              return player1 ;
          }
           return null ;
    }
    @Transactional
    public void deletePlayer (String playerName ) {
        playerRepository.deleteByName(playerName);
    }
}
