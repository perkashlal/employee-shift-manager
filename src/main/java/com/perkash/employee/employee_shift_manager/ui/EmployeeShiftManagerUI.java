package com.perkash.employee.employee_shift_manager.ui;

import javax.swing.*;
import java.awt.*;
import com.perkash.employee_shift_manager.*;

public class EmployeeShiftManagerUI {
    public static void main(String[] args) {
        // ✅ Create Employee Repository
        EmployeeRepository repository = new EmployeeRepository();

        // ✅ Setup Main Frame
        JFrame frame = new JFrame("Employee Shift Manager");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // ✅ Create Tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add Employee Form Panel
        EmployeeFormPanel employeeFormPanel = new EmployeeFormPanel(repository);
        tabbedPane.addTab("Add Employee", employeeFormPanel);

        // Assign Shift Form Panel
        ShiftFormPanel shiftFormPanel = new ShiftFormPanel(repository);
        tabbedPane.addTab("Assign Shift", shiftFormPanel);

        // View/Delete Employees Panel
        ShiftSummaryPanel shiftSummaryPanel = new ShiftSummaryPanel(repository);
        tabbedPane.addTab("View/Delete Employees", shiftSummaryPanel);

        // Add Tabs to Frame
        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
