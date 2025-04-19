package com.perkash.employee_shift_manager;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class EmployeeRepository {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public EmployeeRepository() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("employee_db");
        collection = database.getCollection("employees");
    }

    public void save(Employee employee) {
        Document doc = new Document("name", employee.getName())
                            .append("position", employee.getPosition());
        collection.insertOne(doc);
    }
}
