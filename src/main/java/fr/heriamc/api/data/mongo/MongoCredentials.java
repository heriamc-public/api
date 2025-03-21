package fr.heriamc.api.data.mongo;

public class MongoCredentials {

    private final String uri;
    private final String database;

    public MongoCredentials(String uri, String database) {
        this.uri = uri;
        this.database = database;
    }

    public String uri() {
        return uri;
    }

    public String database() {
        return database;
    }

}
