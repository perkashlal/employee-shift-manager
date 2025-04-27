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
        repository.deleteAll();

        frame = GuiActionRunner.execute(() -> {
            JFrame f = new JFrame("Test Employee Form");
            EmployeeFormPanel panel = new EmployeeFormPanel(repository);
            panel.setShowPopups(false);
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

    @Test @Order(4)
    public void testShouldDeleteEmployeeSuccessfully() {
        // add one employee
        window.textBox("nameField").enterText("Mark");
        window.textBox("idField").enterText("E555");
        window.textBox("roleField").enterText("Tester");
        window.button("saveButton").click();
        robot.waitForIdle();

        int before = window.table("employeeTable").rowCount();
        assertThat(before).isGreaterThan(0);

        window.table("employeeTable").selectRows(before - 1);
        window.button("deleteButton").click();
        robot.waitForIdle();

        int after = window.table("employeeTable").rowCount();
        assertThat(after).isEqualTo(before - 1);
        assertThat(window.label("statusLabel").text())
            .isEqualTo("Employee deleted successfully!");
    }

    @Test @Order(5)
    public void testShouldShowErrorWhenDeletingWithoutSelection() {
        // capture row count
        int before = window.table("employeeTable").rowCount();

        // click Delete with no selection
        window.button("deleteButton").click();
        robot.waitForIdle();

        // table must remain the same
        assertThat(window.table("employeeTable").rowCount())
            .isEqualTo(before);

        // and status message must be exactly this
        assertThat(window.label("statusLabel").text())
            .isEqualTo("No employee selected!");
    }
}
