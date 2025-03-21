package fr.heriamc.api.data.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoConnection {

    private final MongoCredentials credentials;
    private MongoClient client;

    public MongoConnection(MongoCredentials credentials) {
        this.credentials = credentials;

        this.initConnection();
    }

    public void initConnection(){
        ConnectionString connectionString = new ConnectionString(this.credentials.uri());
        CodecRegistry pojoCodecProvider = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecProvider);

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        this.client = MongoClients.create(mongoClientSettings);
    }

    public MongoCredentials getCredentials() {
        return this.credentials;
    }

    public MongoClient getClient() {
        return this.client;
    }

    public MongoDatabase getDatabase() {
        return this.client.getDatabase(this.credentials.database());
    }
}
