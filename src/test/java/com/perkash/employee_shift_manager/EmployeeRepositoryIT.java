package com.perkash.employee_shift_manager;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
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
    private EmployeeRepository repository; // ✅ Class variable

    @BeforeEach
    public void setUp() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("employee_db");
        collection = database.getCollection("employees");
        collection.drop(); // Clean database before each test

        repository = new EmployeeRepository(); // ✅ Initialize repository here
    }

    @AfterEach
    public void tearDown() {
        mongoClient.close();
    }

    @Test
    public void shouldSaveEmployee() {
        // ARRANGE
        Employee employee = new Employee("John Doe", "E123", "Software Engineer");

        // ACT
        repository.save(employee);

        // ASSERT
        long count = collection.countDocuments();
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void shouldFindAllEmployees() {
        // ARRANGE
        Document doc1 = new Document("name", "Alice")
                            .append("employeeId", "E001")
                            .append("role", "Manager");
        Document doc2 = new Document("name", "Bob")
                            .append("employeeId", "E002")
                            .append("role", "Cashier");

        collection.insertOne(doc1);
        collection.insertOne(doc2);

        // ACT
        List<Employee> employees = repository.findAll();

        // ASSERT
        assertThat(employees)
            .hasSize(2)
            .extracting(Employee::getName)
            .containsExactlyInAnyOrder("Alice", "Bob");

        assertThat(employees)
            .extracting(Employee::getRole)
            .containsExactlyInAnyOrder("Manager", "Cashier");
    }
}
