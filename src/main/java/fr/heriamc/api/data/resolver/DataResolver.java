package fr.heriamc.api.data.resolver;

import org.bson.Document;

import java.util.Set;

public final class DataResolver {

    public static Document resolveJson(Defaultable<?> defaultable, Document actualDocument) {
        Document defaultDocument = Document.parse(defaultable.getDefault().toJson());
        mergeDocuments(defaultDocument, actualDocument);
        return actualDocument;
    }

    private static void mergeDocuments(Document defaultDocument, Document actualDocument) {
        for (String key : defaultDocument.keySet()) {
            if (!actualDocument.containsKey(key)) {
                actualDocument.append(key, defaultDocument.get(key));
            } else {
                Object defaultValue = defaultDocument.get(key);
                Object actualValue = actualDocument.get(key);

                if (defaultValue instanceof Document && actualValue instanceof Document) {
                    mergeDocuments((Document) defaultValue, (Document) actualValue);
                }
            }
        }
    }
}
