package interface_adapter.firestore;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FirestoreJsonAdapterTest {

    @Test
    public void testToFirestoreJson_PrimitiveValues() {
        JSONObject inputJson = new JSONObject()
                .put("name", "John Doe")
                .put("age", 30)
                .put("isActive", true)
                .put("pi", 3.1415926535)
                .put("gravity", 9.81f);

        JSONObject expectedJson = new JSONObject()
                .put("name", new JSONObject().put("stringValue", "John Doe"))
                .put("age", new JSONObject().put("integerValue", 30))
                .put("isActive", new JSONObject().put("booleanValue", true))
                .put("pi", new JSONObject().put("doubleValue", 3.1415926535))
                .put("gravity", new JSONObject().put("doubleValue", 9.81f));

        JSONObject actualJson = FirestoreJsonAdapter.toFirestoreJson(inputJson);

        assertTrue(expectedJson.similar(actualJson), "The JSON structures are not similar");
    }

    @Test
    public void testToFirestoreJson_NestedAndArrayValues() {
        JSONObject nestedJson = new JSONObject().put("city", "New York");
        JSONArray jsonArray = new JSONArray().put("apple").put("banana");
        JSONObject inputJson = new JSONObject()
                .put("address", nestedJson)
                .put("fruits", jsonArray);

        JSONObject expectedJson = new JSONObject()
                .put("address", new JSONObject().put("mapValue",
                        new JSONObject().put("fields", new JSONObject()
                                .put("city", new JSONObject().put("stringValue", "New York")))))
                .put("fruits", new JSONObject().put("arrayValue",
                        new JSONObject().put("values", new JSONArray()
                                .put(new JSONObject().put("stringValue", "apple"))
                                .put(new JSONObject().put("stringValue", "banana")))));

        JSONObject actualJson = FirestoreJsonAdapter.toFirestoreJson(inputJson);

        assertTrue(expectedJson.similar(actualJson), "The JSON structures are not similar");
    }

    @Test
    public void testToFirestoreJson_UnsupportedValue() {
        JSONObject inputJson = new JSONObject().put("unsupported", new Object());
        assertThrows(IllegalArgumentException.class,
                () -> FirestoreJsonAdapter.toFirestoreJson(inputJson));
    }

    @Test
    public void testFromFirestoreJson_StringValue() {
        JSONObject firestoreJson = new JSONObject().put("name",
                new JSONObject().put("stringValue", "John Doe"));
        JSONObject expectedJson = new JSONObject().put("name", "John Doe");
        assertEquals(expectedJson.toString(),
                FirestoreJsonAdapter.fromFirestoreJson(firestoreJson).toString());
    }

    @Test
    public void testFromFirestoreJson_IntegerValue() {
        JSONObject firestoreJson = new JSONObject().put("age",
                new JSONObject().put("integerValue", 30));
        JSONObject expectedJson = new JSONObject().put("age", 30);
        assertEquals(expectedJson.toString(),
                FirestoreJsonAdapter.fromFirestoreJson(firestoreJson).toString());
    }

    @Test
    public void testFromFirestoreJson_BooleanValue() {
        JSONObject firestoreJson = new JSONObject().put("isActive",
                new JSONObject().put("booleanValue", true));
        JSONObject expectedJson = new JSONObject().put("isActive", true);
        assertEquals(expectedJson.toString(),
                FirestoreJsonAdapter.fromFirestoreJson(firestoreJson).toString());
    }

    @Test
    public void testFromFirestoreJson_DoubleValue() {
        JSONObject firestoreJson = new JSONObject().put("score",
                new JSONObject().put("doubleValue", 99.5));
        JSONObject expectedJson = new JSONObject().put("score", 99.5);
        assertEquals(expectedJson.toString(),
                FirestoreJsonAdapter.fromFirestoreJson(firestoreJson).toString());
    }

    @Test
    public void testFromFirestoreJson_NestedAndArrayValues() {
        JSONObject firestoreJson = new JSONObject()
                .put("address", new JSONObject().put("mapValue",
                        new JSONObject().put("fields", new JSONObject()
                                .put("city", new JSONObject().put("stringValue", "New York")))))
                .put("fruits", new JSONObject().put("arrayValue",
                        new JSONObject().put("values", new JSONArray()
                                .put(new JSONObject().put("stringValue", "apple"))
                                .put(new JSONObject().put("stringValue", "banana")))));

        JSONObject expectedJson = new JSONObject()
                .put("address", new JSONObject().put("city", "New York"))
                .put("fruits", new JSONArray().put("apple").put("banana"));

        JSONObject actualJson = FirestoreJsonAdapter.fromFirestoreJson(firestoreJson);

        assertTrue(expectedJson.similar(actualJson), "The JSON structures are not similar");
    }

    @Test
    public void testFromFirestoreJson_NullArrayValue() {
        JSONObject firestoreJson = new JSONObject().put("emptyArray",
                new JSONObject().put("arrayValue", new JSONObject()));


        JSONObject expectedJson = new JSONObject().put("emptyArray", new JSONArray());

        JSONObject actualJson = FirestoreJsonAdapter.fromFirestoreJson(firestoreJson);


        assertTrue(expectedJson.similar(actualJson), "The JSON structures are not similar");
    }

    @Test
    public void testFromFirestoreJson_UnsupportedValue() {
        JSONObject firestoreJson = new JSONObject()
                .put("unsupported", new JSONObject().put("unsupportedValue", "value"));
        assertThrows(IllegalArgumentException.class,
                () -> FirestoreJsonAdapter.fromFirestoreJson(firestoreJson));
    }
}
