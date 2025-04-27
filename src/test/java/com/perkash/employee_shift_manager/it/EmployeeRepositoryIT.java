// src/test/java/com/perkash/employee_shift_manager/it/EmployeeRepositoryIT.java
package com.perkash.employee_shift_manager.it;

import com.perkash.employee_shift_manager.Employee;
import com.perkash.employee_shift_manager.EmployeeRepository;
import com.perkash.employee_shift_manager.Shift;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.tuple;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeRepositoryIT {

    // starts a real MongoDB 6.x in Docker
    static final MongoDBContainer mongo = new MongoDBContainer("mongo:6.0.6");

    private EmployeeRepository repository;

    @BeforeAll
    static void startContainer() {
        mongo.start();
    }

    @AfterAll
    static void stopContainer() {
        mongo.stop();
    }

    @BeforeEach
    void setUp() {
        // connect Testcontainer → MongoClient
        MongoClient client = MongoClients.create(mongo.getReplicaSetUrl());
        repository = new EmployeeRepository(client);
        repository.deleteAll();  // wipe out any leftover data
    }

    @Test
    void saveAndFindAll_shouldPersistAndRetrieve() {
        // given
        Employee alice = new Employee("Alice", "A1", "Dev");
        alice.getShifts().add(new Shift(
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(8)
        ));

        // when
        repository.save(alice);

        // then
        List<Employee> all = repository.findAll();
        assertThat(all)
            .hasSize(1)
            .first()
            .satisfies(e -> {
                assertThat(e.getName()).isEqualTo("Alice");
                assertThat(e.getEmployeeId()).isEqualTo("A1");
                assertThat(e.getRole()).isEqualTo("Dev");
                assertThat(e.getShifts())
                   .hasSize(1)
                   .element(0)
                   .satisfies(s -> {
                       assertThat(s.getStartDateTime())
                         .isBeforeOrEqualTo(s.getEndDateTime());
                   });
            });
    }

    @Test
    void updateEmployeeById_shouldModifyExisting() {
        // given
        Employee bob = new Employee("Bob", "B2", "QA");
        repository.save(bob);

        // when
        bob.setName("Robert");
        bob.setRole("Lead QA");
        boolean modified = repository.updateEmployeeById("B2", bob);

        // then
        assertThat(modified).isTrue();
        assertThat(repository.findAll())
            .extracting(Employee::getName, Employee::getRole)
            .containsExactly(tuple("Robert", "Lead QA"));
    }

    @Test
    void deleteEmployeeById_shouldRemoveExisting() {
        // given
        Employee carol = new Employee("Carol", "C3", "Ops");
        repository.save(carol);

        // when
        boolean deleted = repository.deleteEmployeeById("C3");

        // then
        assertThat(deleted).isTrue();
        assertThat(repository.findAll()).isEmpty();
    }
}
