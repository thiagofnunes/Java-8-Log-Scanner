package auxiliar;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringOpsTest {

    @Test
    void assertStringOpsWillReturnTheExpectedObject() {

        String fileContent = "Hello there!\nGeneral Kenobi.";

        List<String> expectedObject = Arrays.asList("Hello there!", "General Kenobi.");


        List<String> logsFileSeparatedByNewLine = StringOps.getLogsFileSeparatedByNewLine(fileContent);

        assertEquals(expectedObject.size(), logsFileSeparatedByNewLine.size());
        assertEquals(expectedObject, logsFileSeparatedByNewLine);

    }

    @Test
    void assertStringOpsWillReturnAnEmptyObject() {

        List<String> expectedObject = new ArrayList<>();


        List<String> logsFileSeparatedByNewLine = StringOps.getLogsFileSeparatedByNewLine(null);

        assertEquals(expectedObject.size(), logsFileSeparatedByNewLine.size());
        assertEquals(expectedObject, logsFileSeparatedByNewLine);

    }



}