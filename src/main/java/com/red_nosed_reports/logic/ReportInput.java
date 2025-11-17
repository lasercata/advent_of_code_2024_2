package com.red_nosed_reports.logic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents an input (file with a list of reports) and defines methods to convert to Report
 */
public class ReportInput {

    private Path filePath;

    /**
     * Creates a new ReportInput instance from a string filename.
     *
     * @param fileName The name/path of the file containing the input
     * @throws IllegalArgumentException if the file does not exists
     * @return A new ReportInput instance
     */
    public ReportInput(String fileName) {
        this.filePath = Paths.get(fileName);

        // Check if file exists
        if (!Files.exists(this.filePath))
            throw new IllegalArgumentException("The specified file does not exists!");
    }

    /**
     * Creates a new ReportInput instance from a Path.
     *
     * @param fileName The name/path of the file containing the input
     * @throws IllegalArgumentException if the file does not exists
     * @return A new ReportInput instance
     */
    public ReportInput(Path filePath) {
        this.filePath = filePath;

        // Check if file exists
        if (!Files.exists(this.filePath))
            throw new IllegalArgumentException("The specified file does not exists!");
    }

    /**
     * Reads the file and converts its content to a list or `Report`s.
     *
     * @throws IOException if such exception occurs when reading the file
     * @return the list of reports corresponding to the input file
     */
    public ArrayList<Report> toReportList() throws IOException {
        // First, read the file
        List<String> lines = Files.readAllLines(this.filePath);

        // Create the return list
        ArrayList<Report> reports = new ArrayList<Report>();

        // Then convert each line
        for (String line : lines) {
            reports.add(lineToReport(line));
        }

        return reports;
    }

    /**
     * Converts a line into a `Report`.
     *
     * @param line A string composed of integers separated with spaces.
     * @return A `Report` corresponding to the input line.
     */
    private static Report lineToReport(String line) {
        Report report = new Report();
        String[] levels = line.split(" ");

        for (String level : levels) {
            report.addLevel(Integer.parseInt(level));
        }

        return report;
    }
}

