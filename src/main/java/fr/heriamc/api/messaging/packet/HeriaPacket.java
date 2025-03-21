package fr.heriamc.api.messaging.packet;

public class HeriaPacket {

    private transient String content;

    private final String channel;
    private final String classPath;

    public HeriaPacket(String channel) {
        this.channel = channel;
        this.classPath = this.getClass().getCanonicalName();
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getClassPath() {
        return this.classPath;
    }

}
