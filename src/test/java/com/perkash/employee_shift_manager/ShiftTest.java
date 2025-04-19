package com.perkash.employee_shift_manager;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import com.perkash.employee_shift_manager.Shift;

public class ShiftTest {

    @Test
    public void testShiftCreation() {
        // Create example start and end date-times
        LocalDateTime start = LocalDateTime.of(2025, 4, 15, 9, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 15, 17, 0);

        // Create Shift object with start and end times
        Shift shift = new Shift(start, end);

        // Validate that start and end times are correctly set
        assertEquals(start, shift.getStartDateTime());
        assertEquals(end, shift.getEndDateTime());
    }
}
