package com.perkash.employee_shift_manager2.ui;

import com.perkash.employee_shift_manager.EmployeeRepository;
import com.perkash.employee_shift_manager.ui.EmployeeFormPanel;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
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

    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        EmployeeRepository repository = EmployeeRepository.createWithDefaultMongo();

        frame = GuiActionRunner.execute(() -> {
            JFrame f = new JFrame("Test Employee Form");
            f.setName("employeeFormFrame");

            EmployeeFormPanel panel = new EmployeeFormPanel(repository);
            panel.setShowPopups(false); // ✅ disable modal dialogs during test
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
        if (frame != null) frame.dispose();
    }

    @Test
    @Order(1)
    public void testShouldAddEmployeeSuccessfully() {
        window.textBox("nameField").enterText("Jane");
        window.textBox("idField").enterText("E999");
        window.textBox("roleField").enterText("Developer");

        window.button("saveButton").click();
        robot.waitForIdle();

        assertThat(window.label("statusLabel").text()).isEqualTo("Employee added successfully!");
        assertThat(window.textBox("nameField").text()).isEmpty();
        assertThat(window.textBox("idField").text()).isEmpty();
        assertThat(window.textBox("roleField").text()).isEmpty();
    }
}
