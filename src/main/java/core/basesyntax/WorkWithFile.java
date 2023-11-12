package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final String SEPARATOR = ",";
    private static final String NEW_LINE = "\n";
    private static final String LABEL_SUPPLY = "supply";
    private static final String LABEL_BUY = "buy";
    private static final String LABEL_RESULT = "result";

    public void getStatistic(String fromFileName, String toFileName) {
        try {
            String fileContent = readFile(fromFileName);

            String report = generateReport(fileContent);

            writeToFile(report, toFileName);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from the file ", e);
        }
    }

    private String readFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append(NEW_LINE);
            }
        }
        return content.toString();
    }

    private String generateReport(String fileContent) {
        int supplyTotal = 0;
        int buyTotal = 0;

        StringBuilder reportBuilder = new StringBuilder();

        String[] lines = fileContent.split(NEW_LINE);
        for (String line : lines) {
            String[] parts = line.split(SEPARATOR);
            if (parts.length == 2) {
                String label = parts[0];
                int value = Integer.parseInt(parts[1]);
                if (LABEL_SUPPLY.equals(label)) {
                    supplyTotal += value;
                } else if (LABEL_BUY.equals(label)) {
                    buyTotal += value;
                }
            }
        }

        int result = supplyTotal - buyTotal;

        reportBuilder.append(LABEL_SUPPLY).append(SEPARATOR).append(supplyTotal)
                .append(System.lineSeparator())
                .append(LABEL_BUY).append(SEPARATOR).append(buyTotal)
                .append(System.lineSeparator())
                .append(LABEL_RESULT).append(SEPARATOR).append(result);

        return reportBuilder.toString();
    }

    private void writeToFile(String report, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(report);
        }
    }
}
