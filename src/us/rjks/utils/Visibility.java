package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 28.02.2021 / 09:25
 *
 **************************************************************************/

public class Visibility {

    public void hidePlayer(Player p, Player t) {
        p.hidePlayer(t);
    }

    public void showPlayer(Player p, Player t) {
        p.showPlayer(t);
    }

    public static void hidePlayersInGame(Player p) {
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(Round.playerInRound(all) != null) {
                Round r = Round.playerInRound(all);
                all.hidePlayer(r.getA().getPlayer());
                all.hidePlayer(r.getB().getPlayer());

                r.getA().getPlayer().hidePlayer(p);
                r.getB().getPlayer().hidePlayer(p);
            } else if(Round.playerInSpec(all) != null) {
                Round r = Round.playerInSpec(all);
                all.hidePlayer(r.getA().getPlayer());
                all.hidePlayer(r.getB().getPlayer());

                r.getA().getPlayer().hidePlayer(p);
                r.getB().getPlayer().hidePlayer(p);
            }
        }
    }

    public static void showPlayerInCurrentState(Player p) {
        try {
            if(false) {
                if(Round.playerInRound(p) != null) {
                    Round r = Round.playerInRound(p);
                    r.getA().getPlayer().showPlayer(r.getB().getPlayer());
                    r.getB().getPlayer().showPlayer(r.getA().getPlayer());

                    for(Player all : Bukkit.getOnlinePlayers()) {
                        if(Round.playerInRound(all) != null && Round.playerInRound(all).equals(r)) {
                            all.showPlayer(r.getA().getPlayer());
                            all.showPlayer(r.getB().getPlayer());
                        }
                        if(Round.playerInSpec(all) != null && Round.playerInSpec(all).equals(r)) {
                            r.getA().getPlayer().hidePlayer(all);
                            r.getB().getPlayer().hidePlayer(all);

                            all.showPlayer(r.getA().getPlayer());
                            all.showPlayer(r.getB().getPlayer());
                        }

                        if((Round.playerInRound(all) != null && !Round.playerInRound(all).equals(r)) || (Round.playerInSpec(all) != null && !Round.playerInSpec(all).equals(r))) {
                            all.hidePlayer(r.getA().getPlayer());
                            all.hidePlayer(r.getB().getPlayer());

                            for(Player specs : r.getSpecs()) {
                                r.getA().getPlayer().hidePlayer(specs);
                                r.getB().getPlayer().hidePlayer(specs);

                                specs.showPlayer(r.getA().getPlayer());
                                specs.showPlayer(r.getB().getPlayer());
                            }
                        }
                    }
                } else if(Round.playerInSpec(p) != null)  {
                    Round r = Round.playerInSpec(p);
                    r.getA().getPlayer().showPlayer(r.getB().getPlayer());
                    r.getB().getPlayer().showPlayer(r.getA().getPlayer());

                    for(Player all : Bukkit.getOnlinePlayers()) {
                        if(Round.playerInRound(all) != null && Round.playerInRound(all).equals(r)) {
                            all.showPlayer(r.getA().getPlayer());
                            all.showPlayer(r.getB().getPlayer());
                        }
                        if(Round.playerInSpec(all) != null && Round.playerInSpec(all).equals(r)) {
                            r.getA().getPlayer().hidePlayer(all);
                            r.getB().getPlayer().hidePlayer(all);

                            all.showPlayer(r.getA().getPlayer());
                            all.showPlayer(r.getB().getPlayer());
                        }

                        if((Round.playerInRound(all) != null && !Round.playerInRound(all).equals(r)) || (Round.playerInSpec(all) != null && !Round.playerInSpec(all).equals(r))) {
                            all.hidePlayer(r.getA().getPlayer());
                            all.hidePlayer(r.getB().getPlayer());

                            for(Player specs : r.getSpecs()) {
                                r.getA().getPlayer().hidePlayer(specs);
                                r.getB().getPlayer().hidePlayer(specs);

                                specs.showPlayer(r.getA().getPlayer());
                                specs.showPlayer(r.getB().getPlayer());
                            }
                        }
                    }
                } else {
                    for(Player all : Bukkit.getOnlinePlayers()) {
                        if(Round.playerInRound(all) == null) {
                            Round r = Round.playerInRound(all);
                            p.hidePlayer(r.getA().getPlayer());
                            p.hidePlayer(r.getB().getPlayer());

                            for(Player specs : r.getSpecs()) {
                                p.hidePlayer(specs);
                                p.hidePlayer(specs);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
