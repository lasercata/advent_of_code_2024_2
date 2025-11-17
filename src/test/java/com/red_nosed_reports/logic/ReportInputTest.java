package com.red_nosed_reports.logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.stream.Stream;
import java.io.IOException;

public class ReportInputTest {
    @Test
    @DisplayName("Test throw on wrong file name")
    void testThrowWrongFileName() {
        assertThrows(IllegalArgumentException.class, () -> new ReportInput("name of a file that do not exists"));
    }

    @ParameterizedTest
    @DisplayName("Test convertion from file to Report list")
    @MethodSource("generateInputToReportArguments")
    void testInputToReport(String content, ArrayList<Report> expectedReports, @TempDir Path tempDir) throws IOException {
        String fileName = "test_input_file.txt";

        Path tempFile = tempDir.resolve(fileName);
        Files.write(tempFile, content.getBytes());

        assertTrue(Files.exists(tempFile));
        assertEquals(content, Files.readString(tempFile));

        ReportInput reportInput = new ReportInput(tempFile);
        assertEquals(expectedReports, reportInput.toReportList());
    }

    private static Stream<Arguments> generateInputToReportArguments() {
        List<Arguments> listOfArguments = new LinkedList<>();

        Report r0 = new Report();
        r0.addLevel(0);

        Report r01 = new Report();
        r01.addLevel(0);
        r01.addLevel(1);

        Report r012345 = new Report();
        for (int k = 0; k < 6; ++k)
            r012345.addLevel(k);

        listOfArguments.add(Arguments.of(
            "0",
            new ArrayList<>(Arrays.asList(r0))
        ));

        listOfArguments.add(Arguments.of(
            "0 1",
            new ArrayList<>(Arrays.asList(r01))
        ));

        listOfArguments.add(Arguments.of(
            "0 1 2 3 4 5",
            new ArrayList<>(Arrays.asList(r012345))
        ));

        listOfArguments.add(Arguments.of(
            "0\n0",
            new ArrayList<>(Arrays.asList(r0, r0))
        ));

        listOfArguments.add(Arguments.of(
            "0 1\n0 1",
            new ArrayList<>(Arrays.asList(r01, r01))
        ));

        listOfArguments.add(Arguments.of(
            "0 1 2 3 4 5\n0\n0 1",
            new ArrayList<>(Arrays.asList(r012345, r0, r01))
        ));

        return listOfArguments.stream();
    }
}

