package com.perkash.employee_shift_manager.it;

import com.perkash.employee_shift_manager.EmployeeRepository;
import com.perkash.employee_shift_manager.ui.EmployeeFormPanel;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.data.TableCell;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.*;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeFormPanelE2ETest {

    private FrameFixture window;
    private Robot robot;
    private JFrame frame;
    private EmployeeRepository repository;

    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();
        repository = EmployeeRepository.createWithDefaultMongo();
        repository.deleteAll();  // start clean

        frame = GuiActionRunner.execute(() -> {
            JFrame f = new JFrame("Test Employee Form");
            EmployeeFormPanel panel = new EmployeeFormPanel(repository);
            panel.setShowPopups(false);  // disable real popups
            f.add(panel);
            f.pack();
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            return f;
        });

        window = new FrameFixture(robot, frame);
        window.show();
        robot.waitForIdle();
    }

    @AfterEach
    public void tearDown() {
        if (window != null) window.cleanUp();
        if (frame  != null) frame.dispose();
    }

    @Test @Order(1)
    public void testShouldAddEmployeeSuccessfully() {
        window.textBox("nameField").enterText("Jane");
        window.textBox("idField").enterText("E999");
        window.textBox("roleField").enterText("Developer");
        window.button("saveButton").click();
        robot.waitForIdle();

        assertThat(window.label("statusLabel").text())
            .isEqualTo("Employee added successfully!");
        assertThat(window.textBox("nameField").text()).isEmpty();
        assertThat(window.textBox("idField").text()).isEmpty();
        assertThat(window.textBox("roleField").text()).isEmpty();
    }

    @Test @Order(2)
    public void testShouldShowErrorForEmptyFields() {
        window.textBox("nameField").enterText("");
        window.textBox("idField").enterText("");
        window.textBox("roleField").enterText("");
        window.button("saveButton").click();
        robot.waitForIdle();

        assertThat(window.label("statusLabel").text())
            .isEqualTo("Failed to add employee!");
    }

    @Test @Order(3)
    public void testShouldViewEmployeeSuccessfully() {
        window.textBox("nameField").enterText("John Doe");
        window.textBox("idField").enterText("E123");
        window.textBox("roleField").enterText("Manager");
        window.button("saveButton").click();
        robot.waitForIdle();

        int rowCount = window.table("employeeTable").rowCount();
        assertThat(rowCount).isGreaterThan(0);

        String employeeName = window.table("employeeTable")
            .cell(TableCell.row(rowCount - 1).column(0))
            .value();
        assertThat(employeeName).isEqualTo("John Doe");
    }

    @Test @Order(4)
    public void testShouldDeleteEmployeeSuccessfully() {
        // Arrange: add one employee
        window.textBox("nameField").enterText("Mark");
        window.textBox("idField").enterText("E555");
        window.textBox("roleField").enterText("Tester");
        window.button("saveButton").click();
        robot.waitForIdle();

        int before = window.table("employeeTable").rowCount();
        assertThat(before).isGreaterThan(0);

        // Act: select and delete
        window.table("employeeTable").selectRows(before - 1);
        window.button("deleteButton").click();
        robot.waitForIdle();

        // Assert: one less row and success status
        int after = window.table("employeeTable").rowCount();
        assertThat(after).isEqualTo(before - 1);
        assertThat(window.label("statusLabel").text())
            .isEqualTo("Employee deleted successfully!");
    }

    @Test @Order(5)
    public void testShouldShowErrorWhenDeletingWithoutSelection() {
        // No rows selected: clicking delete should not remove anything
        int before = window.table("employeeTable").rowCount();

        window.button("deleteButton").click();
        robot.waitForIdle();

        // Table unchanged
        assertThat(window.table("employeeTable").rowCount())
            .isEqualTo(before);

        // Status label shows original behavior
        assertThat(window.label("statusLabel").text())
            .isEqualTo("No employee selected!");
    }
}
