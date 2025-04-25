package com.perkash.employee_shift_manager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class EmployeeRepository {

    private final MongoCollection<Document> collection;

    // 🔁 Constructor that accepts a MongoClient (used in tests)
    public EmployeeRepository(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("employee_db");
        this.collection = database.getCollection("employees");
    }

    // 🔁 Optional: for production use (if needed)
    public static EmployeeRepository createWithDefaultMongo() {
        MongoClient mongoClient = com.mongodb.client.MongoClients.create("mongodb://localhost:27017");
        return new EmployeeRepository(mongoClient);
    }

    public void save(Employee employee) {
        List<Document> shiftDocs = new ArrayList<>();
        for (Shift shift : employee.getShifts()) {
            Document shiftDoc = new Document()
                    .append("startDateTime", shift.getStartDateTime().toString())
                    .append("endDateTime", shift.getEndDateTime().toString());
            shiftDocs.add(shiftDoc);
        }

        Document doc = new Document("name", employee.getName())
                .append("employeeId", employee.getEmployeeId())
                .append("role", employee.getRole())
                .append("shifts", shiftDocs);
        collection.insertOne(doc);
    }

    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        FindIterable<Document> documents = collection.find();

        for (Document doc : documents) {
            Employee employee = new Employee();
            employee.setName(doc.getString("name"));
            employee.setEmployeeId(doc.getString("employeeId"));
            employee.setRole(doc.getString("role"));

            List<Shift> shifts = new ArrayList<>();
            List<Document> shiftDocs = (List<Document>) doc.get("shifts", List.class);
            if (shiftDocs != null) {
                for (Document shiftDoc : shiftDocs) {
                    LocalDateTime start = LocalDateTime.parse((String) shiftDoc.get("startDateTime"));
                    LocalDateTime end = LocalDateTime.parse((String) shiftDoc.get("endDateTime"));
                    shifts.add(new Shift(start, end));
                }
                employee.setShifts(shifts);
            }

            employees.add(employee);
        }
        return employees;
    }

    public boolean updateEmployeeById(String employeeId, Employee updatedEmployee) {
        Document update = new Document("$set", new Document()
            .append("name", updatedEmployee.getName())
            .append("role", updatedEmployee.getRole()));
        Document query = new Document("employeeId", employeeId);

        return collection.updateOne(query, update).getModifiedCount() > 0;
    }

    public boolean deleteEmployeeById(String employeeId) {
        Document query = new Document("employeeId", employeeId);
        return collection.deleteOne(query).getDeletedCount() > 0;
    }
}
