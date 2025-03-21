package fr.heriamc.api.user;

import fr.heriamc.api.data.NonPersistantData;
import fr.heriamc.api.data.SerializableData;
import fr.heriamc.api.user.nick.NickPlayerData;
import fr.heriamc.api.user.rank.HeriaRank;

import java.util.List;
import java.util.UUID;

public class HeriaPlayer implements SerializableData<UUID> {

    private UUID id;
    private String name;
    private HeriaRank rank;

    private long firstConnection;
    private String customPrefix;

    @NonPersistantData
    private String clientBrand;

    @NonPersistantData
    private String connectedTo;

    @NonPersistantData
    private UUID reply;

    @NonPersistantData
    private boolean removedTag;

    @NonPersistantData
    private boolean mod;

    @NonPersistantData
    private boolean vanished;

    @NonPersistantData
    private boolean hasModItems;

    @NonPersistantData
    private NickPlayerData nickData;

    @NonPersistantData
    private UUID queue;

    @NonPersistantData
    private long joinQueueTime;


    private float coins;
    private int hosts;
    private float credits;

    public HeriaPlayer(UUID id, String name, HeriaRank rank, long firstConnection, String customPrefix, String clientBrand,
                       String connectedTo, UUID reply, boolean removedTag, boolean mod, boolean vanished, boolean hasModItems,
                       NickPlayerData nickData, UUID queue, long joinQueueTime, float coins, int hosts, float credits) {

        this.id = id;
        this.name = name;
        this.rank = rank;
        this.firstConnection = firstConnection;
        this.customPrefix = customPrefix;
        this.clientBrand = clientBrand;
        this.connectedTo = connectedTo;
        this.reply = reply;
        this.removedTag = removedTag;
        this.mod = mod;
        this.vanished = vanished;
        this.hasModItems = hasModItems;
        this.nickData = nickData;
        this.queue = queue;
        this.joinQueueTime = joinQueueTime;
        this.coins = coins;
        this.hosts = hosts;
        this.credits = credits;
    }

    public UUID getId() {
        return id;
    }

    public HeriaPlayer setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getNickedName(){
        if(!isNicked()){
            return getName();
        }

        return nickData.getNewName();
    }

    public HeriaPlayer setName(String name) {
        this.name = name;
        return this;
    }


    public HeriaRank getRank() {
        return rank;
    }

    public HeriaPlayer setRank(HeriaRank rank) {
        this.rank = rank;
        return this;
    }

    public String getConnectedTo() {
        return connectedTo;
    }

    public HeriaPlayer setConnectedTo(String connectedTo) {
        this.connectedTo = connectedTo;
        return this;
    }

    public boolean isConnected(){
        return this.connectedTo != null;
    }

    public float getCoins() {
        return coins;
    }

    public HeriaPlayer setCoins(float coins) {
        this.coins = coins;
        return this;
    }

    public int getHosts() {
        return hosts;
    }

    public HeriaPlayer setHosts(int hosts) {
        this.hosts = hosts;
        return this;
    }

    public float getCredits() {
        return credits;
    }

    public void removeCredits(float credits){
        this.credits -= credits;
    }

    public HeriaPlayer setCredits(int credits) {
        this.credits = credits;
        return this;
    }

    public UUID getReply() {
        return reply;
    }

    public HeriaPlayer setReply(UUID reply) {
        this.reply = reply;
        return this;
    }

    public long getFirstConnection() {
        return firstConnection;
    }

    public HeriaPlayer setFirstConnection(long firstConnection) {
        this.firstConnection = firstConnection;
        return this;
    }

    public String getClientBrand() {
        return clientBrand;
    }

    public HeriaPlayer setClientBrand(String clientBrand) {
        this.clientBrand = clientBrand;
        return this;
    }

    public boolean isRemovedTag() {
        return removedTag;
    }

    public HeriaPlayer setRemovedTag(boolean removedTag) {
        this.removedTag = removedTag;
        return this;
    }

    public boolean isMod() {
        return mod;
    }

    public HeriaPlayer setMod(boolean mod) {
        this.mod = mod;
        return this;
    }

    public boolean isNicked(){
        return nickData != null;
    }

    public NickPlayerData getNickData() {
        return nickData;
    }

    public HeriaPlayer setNickData(NickPlayerData nickData) {
        this.nickData = nickData;
        return this;
    }

    public String getCustomPrefix() {
        return customPrefix;
    }

    public HeriaPlayer setCustomPrefix(String customPrefix) {
        this.customPrefix = customPrefix;
        return this;
    }

    public boolean isVanished() {
        return vanished;
    }

    public HeriaPlayer setVanished(boolean vanished) {
        this.vanished = vanished;
        return this;
    }

    public boolean hasModItems() {
        return hasModItems;
    }

    public HeriaPlayer setHasModItems(boolean hasModItems) {
        this.hasModItems = hasModItems;
        return this;
    }

    public UUID getQueue() {
        return queue;
    }

    public HeriaPlayer setQueue(UUID queue) {
        this.queue = queue;
        return this;
    }

    public boolean isInQueue(){
        return this.queue != null;
    }

    public long getJoinQueueTime() {
        return joinQueueTime;
    }

    public HeriaPlayer setJoinQueueTime(long joinQueueTime) {
        this.joinQueueTime = joinQueueTime;
        return this;
    }

    @Override
    public UUID getIdentifier() {
        return this.id;
    }

    @Override
    public void setIdentifier(UUID identifier) {
        this.id = identifier;
    }
}
