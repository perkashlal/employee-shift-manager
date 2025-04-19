package com.perkash.employee_shift_manager.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

public class MongoConnectionIT {

    private MongoClient mongoClient;

    @BeforeEach
    public void setUp() {
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            System.out.println("✅ MongoDB connection established");
        } catch (Exception e) {
            e.printStackTrace();
            fail("❌ Failed to connect to MongoDB: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("✅ MongoDB connection closed");
        }
    }

    @Test
    public void shouldConnectToMongoDB_andListDatabases() {
        List<String> databaseNames = mongoClient.listDatabaseNames()
                                                 .into(new ArrayList<>());
        assertThat(databaseNames).isNotNull();
        System.out.println("✔ Database names: " + databaseNames);
    }

    @Test
    public void shouldInsertDataIntoMongoDB() {
        MongoDatabase db = mongoClient.getDatabase("testdb");
        MongoCollection<Document> collection = db.getCollection("sampleCollection");

        collection.drop(); // clean old data before inserting fresh

        Document document = new Document("name", "Perkash")
                                .append("city", "New York");
        collection.insertOne(document);

        long count = collection.countDocuments();
        assertThat(count).isEqualTo(1);
        System.out.println("✔ Inserted document count into MongoDB: " + count);
    }
}
