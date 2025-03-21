package fr.heriamc.api.data;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import fr.heriamc.api.data.mongo.MongoConnection;
import fr.heriamc.api.data.redis.RedisConnection;
import fr.heriamc.api.data.resolver.DataResolver;
import fr.heriamc.api.data.resolver.Defaultable;
import fr.heriamc.api.utils.FieldUtils;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public abstract class PersistentDataManager<A, D extends SerializableData<A>> extends CacheDataManager<A, D>
        implements Defaultable<D> {

    protected final MongoConnection mongoConnection;
    protected final String mongoCollection;

    private static final JsonWriterSettings JSON_SETTINGS = JsonWriterSettings.builder()
            .objectIdConverter((objectId, strictJsonWriter) -> strictJsonWriter.writeString(objectId.toHexString()))
            .int64Converter((value, writer) -> writer.writeNumber(value.toString()))
            .build();

    public PersistentDataManager(RedisConnection redisConnection, MongoConnection mongoConnection,
                                 String redisKey, String mongoCollection) {

        super(redisConnection, redisKey);
        this.mongoConnection = mongoConnection;
        this.mongoCollection = mongoCollection;
    }

    @Override
    public D get(A identifier){
        D data = this.getInLocal(identifier);

        if(data == null){
            data = this.getInCache(identifier);

            if(data == null){
                data = this.loadInPersistant(identifier);
                if(data != null) {
                    this.putInCache(data);
                }
            }

            if(data != null){
                this.putInLocal(data.getIdentifier(), data);
            }
        }

        return data;
    }

    public D loadInPersistant(A identifier){
        Document document = this.mongoConnection.getDatabase().getCollection(this.mongoCollection)
                .find(Filters.eq("id", identifier.toString())).first();

        if(document == null){
            return null;
        }

        Document checked = DataResolver.resolveJson(this, document);
        D data = SerializableData.fromJson(checked.toJson(JSON_SETTINGS), this.getDataClass());

        try (Jedis jedis = this.redisConnection.getResource()) {
            jedis.hset(this.redisKey, identifier.toString(), data.toJson());
        }

        return data;
    }

    public void saveInPersistant(D data){
        Document document = Document.parse(data.toJson());

        for (String annotated : FieldUtils.getAnnotatedFields(this.getDataClass(), NonPersistantData.class)) {
            System.out.println("nonpersistentdata found= " + annotated);
            document.remove(annotated);
        }

        this.mongoConnection.getDatabase().getCollection(this.mongoCollection)
                .replaceOne(new Document("id", data.getIdentifier().toString()), document);

        this.onDataSave(data);
    }

    /**
     * @deprecated This method is deprecated and should not be used unless you know what you are doing.
     * It directly interacts with the persistent storage and can lead to data loss if misused.
     */
    @Deprecated
    public void removeInPersistant(D data){
        this.mongoConnection.getDatabase().getCollection(this.mongoCollection)
                .deleteOne(new Document("id", data.getIdentifier().toString()));

        this.removeInCache(data.getIdentifier());
        this.removeInLocal(data.getIdentifier());
    }

    /**
     * @deprecated This method is deprecated and should not be used unless you know what you are doing.
     * It fetches all data available in mongo database, it can create lag.
     */
    @Deprecated
    public List<D> getAllFromPersistent() {
        List<D> dataList = new ArrayList<>();

        try (MongoCursor<Document> cursor = this.mongoConnection.getDatabase()
                .getCollection(this.mongoCollection)
                .find()
                .iterator()) {

            while (cursor.hasNext()) {
                Document document = cursor.next();
                A identifier = (A) document.get("id");
                D data = this.get(identifier);
                if (data != null) {
                    dataList.add(data);
                }
            }
        }

        return dataList;
    }


    public D createOrLoad(A identifier){
        D data = this.get(identifier);

        if(data != null){
            this.putInCache(data);
            return data;
        }

        D newData = SerializableData.fromJson(this.getDefault().toJson(), this.getDataClass());
        newData.setIdentifier(identifier);
        Document document = Document.parse(newData.toJson());

        this.mongoConnection.getDatabase().getCollection(this.mongoCollection).insertOne(document);
        this.putInCache(newData);
        onDataCreate(identifier, newData);

        return newData;
    }

    public void onDataCreate(A identifier, D data){

    }

    public void onDataSave(D data){

    }

}
