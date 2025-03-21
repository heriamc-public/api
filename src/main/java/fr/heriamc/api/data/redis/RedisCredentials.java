package fr.heriamc.api.data.redis;

public class RedisCredentials {
    private final String ip;
    private final int port;
    private final String password;

    public RedisCredentials(String ip, int port, String password) {
        this.ip = ip;
        this.port = port;
        this.password = password;
    }

    public String ip() {
        return ip;
    }

    public int port() {
        return port;
    }

    public String password() {
        return password;
    }

}
