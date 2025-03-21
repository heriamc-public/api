package fr.heriamc.api.user.settings;

import fr.heriamc.api.HeriaAPI;
import fr.heriamc.api.data.SerializableData;

import java.util.UUID;

public class HeriaPlayerSettings implements SerializableData<UUID> {

    private UUID id;

    private UserSettingState privateMessage;
    private UserSettingState friendDemand;
    private UserSettingState groupDemand;
    private UserSettingState hubVisibilityPlayer;

    public HeriaPlayerSettings(UUID id, UserSettingState privateMessage, UserSettingState friendDemand, UserSettingState groupDemand, UserSettingState hubVisibilityPlayer) {
        this.id = id;
        this.privateMessage = privateMessage;
        this.friendDemand = friendDemand;
        this.groupDemand = groupDemand;
        this.hubVisibilityPlayer = hubVisibilityPlayer;
    }

    public UUID getId() {
        return id;
    }

    public HeriaPlayerSettings setId(UUID id) {
        this.id = id;
        return this;
    }

    public UserSettingState getPrivateMessage() {
        return privateMessage;
    }

    public void setPrivateMessage(UserSettingState privateMessage) {
        this.privateMessage = privateMessage;
        this.save();
    }

    public UserSettingState getFriendDemand() {
        return friendDemand;
    }

    public void setFriendDemand(UserSettingState friendDemand) {
        this.friendDemand = friendDemand;
        this.save();
    }

    public UserSettingState getGroupDemand() {
        return groupDemand;
    }

    public void setGroupDemand(UserSettingState groupDemand) {
        this.groupDemand = groupDemand;
        this.save();
    }

    public UserSettingState getHubVisibilityPlayer() {
        return hubVisibilityPlayer;
    }

    public void setHubVisibilityPlayer(UserSettingState hubVisibilityPlayer) {
        this.hubVisibilityPlayer = hubVisibilityPlayer;
        this.save();
    }

    @Override
    public UUID getIdentifier() {
        return this.id;
    }

    @Override
    public void setIdentifier(UUID identifier) {
        this.id = identifier;
    }

    public void save(){
        HeriaAPI.get().getSettingsManager().save(this);
    }

    public enum UserSettingState {

        ALL("Tout le monde"),
        FRIENDS("Amis uniquement"),
        NOBODY("Personne");

        private final String name;

        UserSettingState(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
