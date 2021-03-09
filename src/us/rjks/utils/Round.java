package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.rjks.core.Data;
import us.rjks.core.Main;

import java.util.ArrayList;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.02.2021 / 18:47
 *
 **************************************************************************/

public class Round {

    private PlayingPlayer A, B;
    private Integer loadingdisplay, ingamecounter;
    private Map map;
    private ArrayList<Player> specs = new ArrayList<>();

    public Round(Player a, Player b) {
        System.out.println("debug 1");
        Challanger.challanger.remove(a);
        Challanger.challanger.remove(b);

        System.out.println("debug 1");

        A = new PlayingPlayer(a, "rot");
        B = new PlayingPlayer(b, "blau");

        Queue.mlgrush.remove(A.getPlayer());
        Queue.mlgrush.remove(B.getPlayer());

        Data.rounds.add(this);

        map = new Map(Map.findLocation());

        Map.activmaps.add(map);

        A.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §aEs wurde ein Gegner gefunden.");
        B.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §aEs wurde ein Gegner gefunden.");
    }
    private int i = 0;

    public boolean loadMap() {
        map.loadMap();
        try {TitleManager.sendActionBar(A.getPlayer(), "§cDie Map wird vorbereitet..."); TitleManager.sendActionBar(B.getPlayer(), "§cDie Map wird vorbereitet..."); } catch (Exception e) {}
        loadingdisplay = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            public void run() {
                try {TitleManager.sendActionBar(A.getPlayer(), "§cDie Map wird vorbereitet..."); TitleManager.sendActionBar(B.getPlayer(), "§cDie Map wird vorbereitet..."); } catch (Exception e) {}
                i++;
                if(i == 3) {
                    teleportPlayers();
                }
            }
        }, 10, 10);

        return true;
    }

    public boolean teleportPlayers() {
        Bukkit.getScheduler().cancelTask(loadingdisplay);
        A.getPlayer().teleport(map.getA());
        B.getPlayer().teleport(map.getB());

        A.getPlayer().getInventory().clear();
        B.getPlayer().getInventory().clear();

        TabRank.setScoreBoard(A.getPlayer());
        TabRank.setScoreBoard(B.getPlayer());


        InventoryPositions.set(A.getPlayer());
        InventoryPositions.set(B.getPlayer());

        Visibility.showPlayerInCurrentState(A.getPlayer());
        Visibility.showPlayerInCurrentState(B.getPlayer());

        TitleManager.sendTitle(A.getPlayer(), "/spawn");
        TitleManager.sendTitle(B.getPlayer(), "/spawn");

        A.getPlayer().showPlayer(B.getPlayer());
        B.getPlayer().showPlayer(A.getPlayer());

        ingamecounter = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            public void run() {

                ArrayList<Player> showedplayer = specs;
                showedplayer.add(A.getPlayer());
                showedplayer.add(B.getPlayer());

                for(Player all : showedplayer) {
                    try {TitleManager.sendActionBar(all, "§c" + A.getPlayer().getName() + " §e" + A.getPoints() + " §8- §9" + B.getPlayer().getName() + " §e" + B.getPoints());} catch (Exception e) {}
                }
            }
        }, 10, 10);

        return true;
    }

    public void tpBack() {
        A.getPlayer().teleport(map.getA());
        B.getPlayer().teleport(map.getB());

        TabRank.setScoreBoard(A.getPlayer());
        TabRank.setScoreBoard(B.getPlayer());

        InventoryPositions.set(A.getPlayer());
        InventoryPositions.set(B.getPlayer());
    }

    public void tpBack(Player p) {
        if(A.getPlayer().equals(p)) {
            A.getPlayer().teleport(map.getA());
        } else if(B.getPlayer().equals(p)) {
            B.getPlayer().teleport(map.getB());
        }
        InventoryPositions.set(A.getPlayer());
        InventoryPositions.set(B.getPlayer());
    }


    public void leavePlayer(Player p) {
        if(A.getPlayer().equals(p)) {
            A.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §a" + B.getPlayer().getName() + " hat Spiel das gewonnen");

            B.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §aDu hast gewonnen");
            B.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §f+1 Win");
            B.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §f+50 Punkte");

            Stats.setPoints(B.getPlayer(), Stats.getPoints(B.getPlayer()) + 50);
            Stats.setWins(B.getPlayer(), Stats.getWinns(B.getPlayer()) + 1);

            Stats.setDeaths(B.getPlayer(), Stats.getDeaths(B.getPlayer()) + A.getPoints());
            Stats.setKills(B.getPlayer(), Stats.getKills(B.getPlayer()) + B.getPoints());

            Stats.setDeaths(A.getPlayer(), Stats.getDeaths(A.getPlayer()) + B.getPoints());
            Stats.setKills(A.getPlayer(), Stats.getKills(A.getPlayer()) + A.getPoints());

            Stats.setGames(B.getPlayer(), Stats.getDeaths(B.getPlayer()) + 1);
            Stats.setGames(A.getPlayer(), Stats.getDeaths(A.getPlayer()) + 1);

            stop();
        } else if(B.getPlayer().equals(p)) {
            B.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §a" + A.getPlayer().getName() + " hat Spiel das gewonnen");

            A.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §aDu hast gewonnen");
            A.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §f+1 Win");
            A.getPlayer().sendMessage("§8» §eMLG Rush §8┃ §f+50 Punkte");

            Stats.setPoints(A.getPlayer(), Stats.getPoints(A.getPlayer()) + 50);
            Stats.setWins(A.getPlayer(), Stats.getWinns(A.getPlayer()) + 1);

            Stats.setDeaths(B.getPlayer(), Stats.getDeaths(B.getPlayer()) + A.getPoints());
            Stats.setKills(B.getPlayer(), Stats.getKills(B.getPlayer()) + B.getPoints());

            Stats.setDeaths(A.getPlayer(), Stats.getDeaths(A.getPlayer()) + B.getPoints());
            Stats.setKills(A.getPlayer(), Stats.getKills(A.getPlayer()) + A.getPoints());

            Stats.setGames(A.getPlayer(), Stats.getDeaths(A.getPlayer()) + 1);
            Stats.setGames(B.getPlayer(), Stats.getDeaths(B.getPlayer()) + 1);

            stop();
        }
    }

    public boolean stop() {
        Bukkit.getScheduler().cancelTask(ingamecounter);
        map.deleteMap();
        Data.rounds.remove(this);

        TabRank.setScoreBoard(A.getPlayer());
        TabRank.setScoreBoard(B.getPlayer());

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.showPlayer(A.getPlayer());
            all.showPlayer(B.getPlayer());

            A.getPlayer().showPlayer(all);
            B.getPlayer().showPlayer(all);
        }

        Map.activmaps.remove(map);

        for(Player specs : specs) {
            SpawnManager.tp(specs, "spawn");
            Inventory.setLobbyItems(specs);
            TabRank.setScoreBoard(specs);
        }

        Inventory.setLobbyItems(A.getPlayer());
        Inventory.setLobbyItems(B.getPlayer());

        return true;
    }

    public boolean joinSpectator(Player p) {
        Inventory.setSpecItems(p);

        A.getPlayer().hidePlayer(p);
        B.getPlayer().hidePlayer(p);

        p.showPlayer(A.getPlayer());
        p.showPlayer(B.getPlayer());


        specs.add(p);
        return true;
    }

    public boolean leaveSpectator(Player p) {
        Inventory.setLobbyItems(p);

        specs.remove(p);
        return true;
    }

    public void addPointTo(Player p) {
        if(A.getPlayer().equals(p)) {
            A.addPont();
        } else if(B.getPlayer().equals(p)) {
            B.addPont();
        }
        TabRank.setScoreBoard(A.getPlayer());
        TabRank.setScoreBoard(B.getPlayer());
        for(Player spec : specs) {
            TabRank.setScoreBoard(spec);
        }
    }

    public PlayingPlayer getA() {
        return A;
    }

    public PlayingPlayer getB() {
        return B;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Player> getSpecs() {
        return specs;
    }

    public static Round playerInRound(Player a) {
        for(Round round : Data.rounds) {
            if(round.getA().getPlayer().equals(a) || round.getB().getPlayer().equals(a)) {
                return round;
            }
        }
        return null;
    }

    public static Round playerInSpec(Player a) {
        for(Round round : Data.rounds) {
            if(round.getSpecs().contains(a)) {
                return round;
            }
        }
        return null;
    }

    public class PlayingPlayer {

        private Player player;
        private String teamname;

        private int points;

        PlayingPlayer(Player p, String team) {
            player = p;
            teamname = team;
            points = 0;
        }

        public void addPont() {
            points++;
        }

        public Player getPlayer() {
            return player;
        }

        public String getTeamname() {
            return teamname;
        }

        public int getPoints() {
            return points;
        }
    }

}
