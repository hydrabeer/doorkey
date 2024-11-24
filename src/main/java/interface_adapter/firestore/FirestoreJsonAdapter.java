package interface_adapter.firestore;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A JSON Adapter for Firestore.
 * Converts a regular JSON object to a Firestore-compatible JSON object.
 */
public class FirestoreJsonAdapter {
    private static final String STRING_VALUE = "stringValue";
    private static final String INTEGER_VALUE = "integerValue";
    private static final String BOOLEAN_VALUE = "booleanValue";
    private static final String DOUBLE_VALUE = "doubleValue";
    private static final String MAP_VALUE = "mapValue";
    private static final String ARRAY_VALUE = "arrayValue";
    private static final String VALUE = "value";

    /**
     * Convert a regular JSON object to a Firestore-compatible JSON object.
     *
     * @param inputJson The regular JSON object.
     * @return The Firestore-compatible JSON object.
     */
    public static JSONObject toFirestoreJson(JSONObject inputJson) {
        final JSONObject firestoreFields = new JSONObject();
        for (String key : inputJson.keySet()) {
            final Object value = inputJson.get(key);
            final JSONObject firestoreValue = convertLocalValue(value);
            firestoreFields.put(key, firestoreValue);
        }

        return firestoreFields;
    }

    /**
     * Convert a Firestore-compatible JSON object to a regular JSON object.
     *
     * @param firestoreJson The Firestore-compatible JSON object.
     * @return The regular JSON object.
     */
    public static JSONObject fromFirestoreJson(JSONObject firestoreJson) {
        final JSONObject regularJson = new JSONObject();
        for (String key : firestoreJson.keySet()) {
            final Object firestoreValue = firestoreJson.get(key);
            final Object regularValue = convertFirestoreValue(firestoreValue);
            regularJson.put(key, regularValue);
        }
        return regularJson;
    }

    @NotNull
    private static Object convertFirestoreValue(Object firestoreValue) {
        final JSONObject firestoreObject = (JSONObject) firestoreValue;

        if (firestoreObject.has(STRING_VALUE)) {
            return firestoreObject.getString(STRING_VALUE);
        }
        else if (firestoreObject.has(INTEGER_VALUE)) {
            return firestoreObject.getInt(INTEGER_VALUE);
        }
        else if (firestoreObject.has(BOOLEAN_VALUE)) {
            return firestoreObject.getBoolean(BOOLEAN_VALUE);
        }
        else if (firestoreObject.has(DOUBLE_VALUE)) {
            return firestoreObject.getDouble(DOUBLE_VALUE);
        }
        else if (firestoreObject.has(MAP_VALUE)) {
            final JSONObject mapValue = firestoreObject.getJSONObject(MAP_VALUE).getJSONObject("fields");
            return fromFirestoreJson(mapValue);
        }
        else if (firestoreObject.has(ARRAY_VALUE)) {
            final JSONArray firestoreArray = firestoreObject.getJSONObject(ARRAY_VALUE).optJSONArray("values");
            if (firestoreArray == null) {
                return new JSONArray();
            }
            final JSONArray regularArray = new JSONArray();
            for (int i = 0; i < firestoreArray.length(); i++) {
                final JSONObject arrayElement = firestoreArray.getJSONObject(i);
                final Object value = convertFirestoreValue(arrayElement.get(VALUE));
                regularArray.put(value);
            }
            return regularArray;
        }
        else {
            throw new IllegalArgumentException("Unsupported: " + firestoreObject.keySet());
        }
    }

    @NotNull
    private static JSONObject convertLocalValue(Object value) {
        final JSONObject firestoreValue = new JSONObject();
        if (value instanceof String) {
            firestoreValue.put(STRING_VALUE, value);
        }
        else if (value instanceof Integer) {
            firestoreValue.put(INTEGER_VALUE, value);
        }
        else if (value instanceof Boolean) {
            firestoreValue.put(BOOLEAN_VALUE, value);
        }
        else if (value instanceof Double || value instanceof Float) {
            firestoreValue.put(DOUBLE_VALUE, value);
        }
        else if (value instanceof JSONObject) {
            firestoreValue.put(MAP_VALUE, new JSONObject().put("fields", toFirestoreJson((JSONObject) value)));
        }
        else if (value instanceof JSONArray array) {
            final JSONArray firestoreArrayValues = new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                // 'value' here doesn't matter - its temporary so that we can recursively call the function.
                final JSONObject firestoreArrayValue = toFirestoreJson(new JSONObject().put(VALUE, array.get(i)));
                firestoreArrayValues.put(firestoreArrayValue.get(VALUE));
            }
            firestoreValue.put(ARRAY_VALUE, new JSONObject().put("values", firestoreArrayValues));
        }
        else {
            throw new IllegalArgumentException("Unsupported: " + value.getClass().getName());
        }
        return firestoreValue;
    }
}
