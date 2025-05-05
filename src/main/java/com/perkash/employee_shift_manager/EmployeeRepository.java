package com.perkash.employee_shift_manager;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for {@link Employee} objects, backed by a MongoDB collection.
 */
public class EmployeeRepository {

    private final MongoCollection<Document> collection;

    public EmployeeRepository(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("employee_db");
        this.collection = database.getCollection("employees");
    }

    /**
     * Production convenience factory (connects to localhost:27017).
     */
    public static EmployeeRepository createWithDefaultMongo() {
        MongoClient mongoClient = 
            com.mongodb.client.MongoClients.create("mongodb://localhost:27017");
        return new EmployeeRepository(mongoClient);
    }

    /**
     * Persist a new employee (including its shifts).
     */
    public void save(Employee employee) {
        List<Document> shiftDocs = new ArrayList<>();
        for (Shift shift : employee.getShifts()) {
            shiftDocs.add(new Document()
                .append("startDateTime", shift.getStartDateTime().toString())
                .append("endDateTime",   shift.getEndDateTime().toString()));
        }

        Document doc = new Document("name",       employee.getName())
            .append("employeeId", employee.getEmployeeId())
            .append("role",       employee.getRole())
            .append("shifts",     shiftDocs);

        collection.insertOne(doc);
    }

    /**
     * Fetch all employees (and reconstruct their shifts).
     */
    @SuppressWarnings("unchecked")
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        FindIterable<Document> docs = collection.find();
        for (Document doc : docs) {
            Employee e = new Employee();
            e.setName(doc.getString("name"));
            e.setEmployeeId(doc.getString("employeeId"));
            e.setRole(doc.getString("role"));

            List<Shift> shifts = new ArrayList<>();
            List<Document> shiftDocs = (List<Document>) doc.get("shifts", List.class);
            if (shiftDocs != null) {
                for (Document sd : shiftDocs) {
                    LocalDateTime start = LocalDateTime.parse(sd.getString("startDateTime"));
                    LocalDateTime end   = LocalDateTime.parse(sd.getString("endDateTime"));
                    shifts.add(new Shift(start, end));
                }
            }
            e.setShifts(shifts);

            employees.add(e);
        }
        return employees;
    }

    /**
     * Delete all documents in the collection.
     * Useful for tests to reset state.
     */
    public void deleteAll() {
        collection.deleteMany(new Document());
    }

    /**
     * Update name+role of the employee with the given ID.
     * @return true if matched & modified
     */
    public boolean updateEmployeeById(String employeeId, Employee updatedEmployee) {
        Document query  = new Document("employeeId", employeeId);
        Document update = new Document("$set", new Document()
            .append("name", updatedEmployee.getName())
            .append("role", updatedEmployee.getRole()));
        return collection.updateOne(query, update).getModifiedCount() > 0;
    }

    /**
     * Remove the employee with the given ID.
     * @return true if something was deleted
     */
    public boolean deleteEmployeeById(String employeeId) {
        Document query = new Document("employeeId", employeeId);
        return collection.deleteOne(query).getDeletedCount() > 0;
    }
}
