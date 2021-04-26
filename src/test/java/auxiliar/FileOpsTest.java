package auxiliar;

import org.junit.jupiter.api.Test;
import pojo.Rendering;
import pojo.NewRenderingInstance;
import pojo.Report;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class FileOpsTest {

    @Test
    void itShouldThrowExceptionSinceFileDoesNotExist() throws IOException {

        assertThatThrownBy(() -> FileOps.readLogFileToString("test2.txt")).isInstanceOf(NoSuchFileException.class);
    }

    @Test
    void itShouldReadFileAndEqualGivenString() throws IOException {
        String testFileContent = "Hello, world!";
        String testFilePath = "src\\test\\resources\\test.txt";

        assertThatCode(() -> {
            FileOps.readLogFileToString(testFilePath);
        }).doesNotThrowAnyException();

        String realFileContent = FileOps.readLogFileToString(testFilePath);

        assertEquals(testFileContent, realFileContent);

    }

    @Test
    void itShouldCheckIfFileIsALogFileAndReturnFalse() {

        String testFilePath = "src\\test\\resources\\test.txt";


        boolean isLogFile = FileOps.checkIfFileExistsAndIsNotADirectoryAndIsALogFile(testFilePath);

        assertFalse(isLogFile);

    }

    @Test
    void itShouldCheckIfFileIsALogFileAndReturnTrue() {

        String testFilePath = "C:\\Users\\thiag\\Downloads\\server.log";


        boolean isLogFile = FileOps.checkIfFileExistsAndIsNotADirectoryAndIsALogFile(testFilePath);

        assertTrue(isLogFile);

    }

    @Test
    void assertFileContentWillReturnTheCorrectLineCount() {

        String fileContent = "Hello there!\nGeneral Kenobi.";


        int lineCount = FileOps.getLineCounts(fileContent);

        assertEquals(lineCount, 2);

    }

    @Test
    void assertFileContentWillReturnOne() {

        String fileContent = "";


        int lineCount = FileOps.getLineCounts(fileContent);

        assertEquals(lineCount, 1);

    }

    @Test
    void assertFileContentWillReturnZero() {


        int lineCount = FileOps.getLineCounts(null);

        assertEquals(lineCount, 0);

    }

    @Test
    void assertThatItCanWriteAnEmptyReport() {


        FileOps fileOps = new FileOps();
        Report report = new Report();

        assertThatCode(() -> {
            fileOps.writeReportToXml(report);
        }).doesNotThrowAnyException();


        AtomicReference<String> fileContent = new AtomicReference<>("");

        assertThatCode(() -> {
            fileContent.set(FileOps.readLogFileToString("report.xml"));
        }).doesNotThrowAnyException();


        String fileContentShouldBe = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<report>\n" +
                "    <summary>\n" +
                "        <count>0</count>\n" +
                "        <duplicates>0</duplicates>\n" +
                "        <unnecessary>0</unnecessary>\n" +
                "    </summary>\n" +
                "</report>\n";

        assertEquals(fileContent.get(), fileContentShouldBe);
    }

    @Test
    void assertThatItCanWriteReportWithOneRendering() {
/*

        NewRenderingInstance newRenderingInstance = new NewRenderingInstance("1", "2","2010-10-06 09:02:10,498");
        newRenderingInstance.setStartRenderingUID("3");
        newRenderingInstance.addGetRenderingTimestamp("2010-10-06 09:02:11,498");
        List<NewRenderingInstance> list = Arrays.asList(newRenderingInstance);

        Rendering rendering = new Rendering("1", list);
        Report report = new Report(Arrays.asList(rendering));

        FileOps fileOps = new FileOps();
        assertThatCode(() -> {
            fileOps.writeReportToXml(report);
        }).doesNotThrowAnyException();


        AtomicReference<String> fileContent = new AtomicReference<>("");

        assertThatCode(() -> {
            fileContent.set(FileOps.readLogFileToString("report.xml"));
        }).doesNotThrowAnyException();


        String fileContentShouldBe = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<report>\n" +
                "    <rendering>\n" +
                "        <document>1</document>\n" +
                "        <page>2</page>\n" +
                "        <uid>3</uid>\n" +
                "        <start>2010-10-06 09:02:10,498</start>\n" +
                "        <get>2010-10-06 09:02:11,498</get>\n" +
                "    </rendering>\n" +
                "    <summary>\n" +
                "        <count>1</count>\n" +
                "        <duplicates>0</duplicates>\n" +
                "        <unnecessary>0</unnecessary>\n" +
                "    </summary>\n" +
                "</report>\n";

        assertEquals(fileContent.get(), fileContentShouldBe);*/
    }


}