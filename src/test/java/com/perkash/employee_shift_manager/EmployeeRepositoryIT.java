package com.perkash.employee_shift_manager;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Arrays;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class EmployeeRepositoryIT {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private EmployeeRepository repository;

    @BeforeEach
    public void setUp() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("employee_db");
        collection = database.getCollection("employees");
        collection.drop(); // Clean the database before each test

        repository = new EmployeeRepository();
    }

    @AfterEach
    public void tearDown() {
        mongoClient.close();
    }

    @Test
    public void shouldSaveEmployee() {
        // Arrange
        Employee employee = new Employee("John Doe", "E123", "Software Engineer");

        // Act
        repository.save(employee);

        // Assert
        long count = collection.countDocuments();
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void shouldFindAllEmployees() {
        // Arrange
        Document doc1 = new Document("name", "Alice")
                .append("employeeId", "E001")
                .append("role", "Manager")
                .append("shifts", Arrays.asList()); // Empty shift list
        Document doc2 = new Document("name", "Bob")
                .append("employeeId", "E002")
                .append("role", "Cashier")
                .append("shifts", Arrays.asList());

        collection.insertOne(doc1);
        collection.insertOne(doc2);

        // Act
        List<Employee> employees = repository.findAll();

        // Assert
        assertThat(employees)
            .hasSize(2)
            .extracting(Employee::getName)
            .containsExactlyInAnyOrder("Alice", "Bob");

        assertThat(employees)
            .extracting(Employee::getRole)
            .containsExactlyInAnyOrder("Manager", "Cashier");
    }

    @Test
    public void shouldDeleteEmployeeById() {
        // Arrange
        Document doc = new Document("name", "Delete Test")
                .append("employeeId", "DEL001")
                .append("role", "Intern")
                .append("shifts", Arrays.asList());

        collection.insertOne(doc);

        // Act
        boolean deleted = repository.deleteEmployeeById("DEL001");

        // Assert
        assertThat(deleted).isTrue();
        assertThat(collection.countDocuments(new Document("employeeId", "DEL001"))).isEqualTo(0);
    }

    @Test
    public void shouldSaveEmployeeWithShifts() {
        // Arrange
        Employee employee = new Employee("Shift Tester", "SHIFT001", "Supervisor");

        Shift shift1 = new Shift(
                java.time.LocalDateTime.of(2025, 5, 10, 9, 0),
                java.time.LocalDateTime.of(2025, 5, 10, 17, 0)
        );
        Shift shift2 = new Shift(
                java.time.LocalDateTime.of(2025, 5, 11, 9, 0),
                java.time.LocalDateTime.of(2025, 5, 11, 17, 0)
        );

        employee.addShift(shift1);
        employee.addShift(shift2);

        // Act
        repository.save(employee);

        // Assert
        Document storedEmployee = collection.find(new Document("employeeId", "SHIFT001")).first();
        assertThat(storedEmployee).isNotNull();
        List<Document> shifts = (List<Document>) storedEmployee.get("shifts");
        assertThat(shifts).hasSize(2);
    }
}