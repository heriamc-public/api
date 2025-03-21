package fr.heriamc.api.messaging.packet;

import com.google.gson.*;

import java.lang.reflect.Type;

public class HeriaPacketSerializer implements JsonDeserializer<HeriaPacket> {

    private static final Gson gson = new GsonBuilder().create();

    @Override
    public HeriaPacket deserialize(JsonElement jsonElement, Type type,
                                  JsonDeserializationContext jsonDeserializationContext) throws JsonSyntaxException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (!jsonObject.has("classPath")) return null;

        HeriaPacket heriaPacket;
        String classPath = jsonObject.get("classPath").getAsString();
        try {

            Class<?> classFound = Class.forName(classPath);
            Class<? extends HeriaPacket> classCast = classFound.asSubclass(HeriaPacket.class);
            heriaPacket = gson.fromJson(jsonObject, classCast);

        } catch (ClassCastException | ClassNotFoundException e) {
            heriaPacket = null;
        }

        return heriaPacket;
    }
}
