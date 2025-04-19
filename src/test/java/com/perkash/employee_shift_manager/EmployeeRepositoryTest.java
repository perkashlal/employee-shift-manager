package com.perkash.employee_shift_manager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.perkash.employee_shift_manager.Employee;
import com.perkash.employee_shift_manager.EmployeeRepository;

public class EmployeeRepositoryTest {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @BeforeEach
    public void setUp() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("employee_db");
        collection = database.getCollection("employees");
        collection.drop(); // Clean before each test
    }

    @AfterEach
    public void tearDown() {
        mongoClient.close();
    }

    @Test
    public void shouldSaveEmployee() {
        // ARRANGE
        EmployeeRepository repository = new EmployeeRepository(); // 🔥 This class does NOT exist yet!

        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setPosition("Software Engineer");

        // ACT
        repository.save(employee);

        // ASSERT
        long count = collection.countDocuments();
        assertThat(count).isEqualTo(1);
    }
}
