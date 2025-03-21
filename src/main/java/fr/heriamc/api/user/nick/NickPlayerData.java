package fr.heriamc.api.user.nick;

import fr.heriamc.api.user.rank.HeriaRank;

public class NickPlayerData {

    private String newName;
    private HeriaRank newRank;
    private String newSkin;
    private long instant;

    public NickPlayerData(String newName, HeriaRank newRank, String newSkin, long instant) {
        this.newName = newName;
        this.newRank = newRank;
        this.newSkin = newSkin;
        this.instant = instant;
    }

    public String getNewName() {
        return newName;
    }

    public NickPlayerData setNewName(String newName) {
        this.newName = newName;
        return this;
    }

    public HeriaRank getNewRank() {
        return newRank;
    }

    public NickPlayerData setNewRank(HeriaRank newRank) {
        this.newRank = newRank;
        return this;
    }

    public String getNewSkin() {
        return newSkin;
    }

    public NickPlayerData setNewSkin(String newSkin) {
        this.newSkin = newSkin;
        return this;
    }

    public long getInstant() {
        return instant;
    }

    public NickPlayerData setInstant(long instant) {
        this.instant = instant;
        return this;
    }
}
