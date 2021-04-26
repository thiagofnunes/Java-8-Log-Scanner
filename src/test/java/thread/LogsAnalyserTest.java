package thread;

import auxiliar.FileOps;
import auxiliar.StringOps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogsAnalyserTest {

    private List<String> scenario1, scenario2,scenario3;

    @BeforeEach
    void setUp() throws IOException {

        scenario1 = StringOps.getLogsFileSeparatedByNewLine(FileOps.readLogFileToString("src\\test\\resources\\scenario_1.log"));
        scenario2 = StringOps.getLogsFileSeparatedByNewLine(FileOps.readLogFileToString("src\\test\\resources\\scenario_2.log"));
        scenario3 = StringOps.getLogsFileSeparatedByNewLine(FileOps.readLogFileToString("src\\test\\resources\\scenario_3.log"));

    }

    @Test
    void assertThatItWillCallAllTheCorrectClassesAndReturnTheExpectedReportFileForScenario1() throws InterruptedException, IOException {

        Runnable runnable = () -> {
            LogsAnalyser logsAnalyser = new LogsAnalyser(scenario1);
            logsAnalyser.startGetRenderingAnalyser();
        };

        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(1000);

        assertTrue(Files.exists(Paths.get("report.xml")));

        String expectedReportFile ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<report>\n" +
                "    <rendering>\n" +
                "        <document>114466</document>\n" +
                "        <page>0</page>\n" +
                "        <uid>1286373785873-3536</uid>\n" +
                "        <start>2010-10-06 09:03:05,869</start>\n" +
                "        <get>2010-10-06 09:03:06,547</get>\n" +
                "    </rendering>\n" +
                "    <rendering>\n" +
                "        <document>114466</document>\n" +
                "        <page>0</page>\n" +
                "        <uid>1286373733634-5423</uid>\n" +
                "        <start>2010-10-06 09:02:13,631</start>\n" +
                "        <get>2010-10-06 09:02:14,825</get>\n" +
                "    </rendering>\n" +
                "    <summary>\n" +
                "        <count>2</count>\n" +
                "        <duplicates>0</duplicates>\n" +
                "        <unnecessary>0</unnecessary>\n" +
                "    </summary>\n" +
                "</report>\n";

        assertEquals(expectedReportFile,FileOps.readLogFileToString("report.xml"));



    }

    @Test
    void assertThatItWillCallAllTheCorrectClassesAndReturnTheExpectedReportFileForScenario2() throws InterruptedException, IOException {

        Runnable runnable = () -> {
            LogsAnalyser logsAnalyser = new LogsAnalyser(scenario2);
            logsAnalyser.startGetRenderingAnalyser();
        };

        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(1000);

        assertTrue(Files.exists(Paths.get("report.xml")));

        String expectedReportFile ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<report>\n" +
                "    <rendering>\n" +
                "        <document>114466</document>\n" +
                "        <page>0</page>\n" +
                "        <uid>1286373733634-5423</uid>\n" +
                "        <start>2010-10-06 09:02:13,631</start>\n" +
                "        <get>2010-10-06 09:02:14,825</get>\n" +
                "        <get>2010-10-06 09:02:14,828</get>\n" +
                "    </rendering>\n" +
                "    <summary>\n" +
                "        <count>1</count>\n" +
                "        <duplicates>0</duplicates>\n" +
                "        <unnecessary>1</unnecessary>\n" +
                "    </summary>\n" +
                "</report>\n";

        assertEquals(expectedReportFile,FileOps.readLogFileToString("report.xml"));



    }

    @Test
    void assertThatItWillCallAllTheCorrectClassesAndReturnTheExpectedReportFileForScenario3() throws InterruptedException, IOException {

        Runnable runnable = () -> {
            LogsAnalyser logsAnalyser = new LogsAnalyser(scenario3);
            logsAnalyser.startGetRenderingAnalyser();
        };

        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(1000);

        assertTrue(Files.exists(Paths.get("report.xml")));

        String expectedReportFile ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<report>\n" +
                "    <rendering>\n" +
                "        <document>115771</document>\n" +
                "        <page>0</page>\n" +
                "        <uid>1286395408854-2742</uid>\n" +
                "        <start>2010-10-06 15:03:28,834</start>\n" +
                "        <get>2010-10-06 15:03:29,326</get>\n" +
                "    </rendering>\n" +
                "    <summary>\n" +
                "        <count>1</count>\n" +
                "        <duplicates>1</duplicates>\n" +
                "        <unnecessary>0</unnecessary>\n" +
                "    </summary>\n" +
                "</report>\n";

        assertEquals(expectedReportFile,FileOps.readLogFileToString("report.xml"));



    }
}