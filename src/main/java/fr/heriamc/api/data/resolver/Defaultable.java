package fr.heriamc.api.data.resolver;

import fr.heriamc.api.data.SerializableData;

public interface Defaultable<A extends SerializableData<?>> {

    A getDefault();

}
