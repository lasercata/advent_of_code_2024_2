package com.red_nosed_reports.logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.AbstractList;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

import java.util.stream.Stream;

public class ReportTest {
    private Report report;

    @BeforeEach
    void setUp() {
        this.report = new Report();
    }

    @Test
    void testAddLevel() {
        report.addLevel(0);
        assertEquals(Arrays.asList(0), report.getLevels());

        report.addLevel(100);
        assertEquals(Arrays.asList(0, 100), report.getLevels());
    }

    @ParameterizedTest
    @DisplayName("Test the safe level checks")
    @MethodSource("generateIsSafeArguments")
    void testIsSafe(AbstractList<Integer> levels, boolean shouldBeAccepted) {
        // Add levels to the report
        for (int level : levels) {
            report.addLevel(level);
        }

        assertEquals(shouldBeAccepted, report.isSafe());
    }

    private static Stream<Arguments> generateIsSafeArguments() {
        List<Arguments> listOfArguments = new LinkedList<>();

        listOfArguments.add(Arguments.of(Arrays.asList(0), true));
        listOfArguments.add(Arguments.of(Arrays.asList(7, 6, 4, 2, 1), true));
        listOfArguments.add(Arguments.of(Arrays.asList(1, 2, 7, 8, 9), false));
        listOfArguments.add(Arguments.of(Arrays.asList(9, 7, 6, 2, 1), false));
        listOfArguments.add(Arguments.of(Arrays.asList(1, 3, 2, 4, 5), false));
        listOfArguments.add(Arguments.of(Arrays.asList(8, 6, 4, 4, 1), false));
        listOfArguments.add(Arguments.of(Arrays.asList(1, 3, 6, 7, 9), true));

        return listOfArguments.stream();
    }
}
