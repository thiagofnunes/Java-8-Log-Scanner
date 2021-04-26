package auxiliar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexOpsTest {

    private RegexOps regexOps;

    @BeforeEach
    void setup() {
        regexOps = new RegexOps();
    }

    @Test
    void assertThatItWillFindTheCorrectTimestamp() {

        String logLine = "2010-10-06 09:02:10,498 [RenderingQueue] INFO  [RenderingQueue]: Waiting for next command...";

        String expectedTimestamp = "2010-10-06 09:02:10,498";

        assertEquals(expectedTimestamp, regexOps.getTimeStampFromString(logLine));

    }

    @Test
    void assertThatItWillNotFindTheCorrectTimestamp() {

        String logLine = "2010-10-06 09/02/10/498 [RenderingQueue] INFO  [RenderingQueue]: Waiting for next command...";

        assertEquals("", regexOps.getTimeStampFromString(logLine));

    }

    @Test
    void assertThatItIsNotAStartRenderingCommand() {

        String logLine = "2010-10-06 09:02:11,550 [WorkerThread-4] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373729338-5317] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";

        assertFalse(regexOps.isStartRenderingCommand(logLine));

    }

    @Test
    void assertThatItIsAStartRenderingCommand() {

        String logLine = "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";

        assertTrue(regexOps.isStartRenderingCommand(logLine));

    }

    @Test
    void assertThatItIsAGetRenderingCommand() {

        String logLine = "2010-10-06 09:02:11,550 [WorkerThread-4] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373729338-5317] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
        String uid = "1286373729338-5317";

        assertTrue(regexOps.isGetRenderingCommand(logLine));

    }

    @Test
    void assertThatItIsNotAGetRenderingCommand() {

        String logLine = "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
        String uid = "1286373729338-5317";

        assertFalse(regexOps.isGetRenderingCommand(logLine));

    }

    @Test
    void assertThatItIsNotAGetRenderingCommandWhenOneOrBothAreTrue() {

        assertFalse(regexOps.isGetRenderingCommand(null));

    }

    @Test
    void assertThatItShouldReturnTheCorrectBookIdAndPage() {

        String logLine = "2010-10-06 09:02:13,631 [WorkerThread-2] INFO  [ServiceProvider]: Executing request startRendering with arguments [114466, 0] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
        String[] bookIdAndPage = regexOps.getBookAndPageFromStartRendering(logLine);

        String bookId = "114466";
        String page = " 0";

        String[] expectedValues = new String[2];
        expectedValues[0] = bookId;
        expectedValues[1] = page;

        assertEquals(expectedValues.length, bookIdAndPage.length);
        assertEquals(expectedValues[0], bookIdAndPage[0]);
        assertEquals(expectedValues[1], bookIdAndPage[1]);


    }

    @Test
    void assertThatItShouldReturnANullBookIdAndPage() {

        String logLine = "2010-10-06 09:02:13,634 [WorkerThread-2] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5423\n";
        String[] bookIdAndPage = regexOps.getBookAndPageFromStartRendering(logLine);

        assertNull(bookIdAndPage);


    }

    @Test
    void assertThatItIsAsStartRenderingServiceIdAndWillReturnTheExpectedId() {

        String logLine = "2010-10-06 09:02:13,634 [WorkerThread-2] INFO  [ServiceProvider]: Service startRendering returned 1286373733634-5423";
        String expectedId = "1286373733634-5423";

        assertTrue(regexOps.hasStartRenderingServiceUid(logLine));

        assertEquals(expectedId, regexOps.getStartRenderingUid(logLine));


    }

    @Test
    void assertThatItWillNotReturnARenderingUidFromInvalidGetLogLine() {

        String logLine = "2010-10-06 09:02:10,498 [RenderingQueue] INFO  [RenderingQueue]: Waiting for next command...";

        assertEquals("", regexOps.getUIDfromGetRenderingLogLine(logLine));


    }

    @Test
    void assertThatItIsNotAsStartRenderingServiceIdAndWillNotReturnTheExpectedId() {

        String logLine = "2010-10-06 09:02:13,634 [WorkerThread-2] DEBUG [RenderingQueue]: Adding command to queue: { RenderingCommand - uid: 1286373733634-5423 }\n";
        String expectedId = "1286373733634-5423";

        assertFalse(regexOps.hasStartRenderingServiceUid(logLine));

        assertNotEquals(expectedId, regexOps.getStartRenderingUid(logLine));


    }

    @Test
    void assertThatItReturnsTheCorrectUIDFromLogLine() {

        String logLine = "2010-10-06 09:02:11,550 [WorkerThread-4] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373729338-5317] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
        String expectedId = "1286373729338-5317";

        assertEquals(expectedId, regexOps.getUIDfromGetRenderingLogLine(logLine));


    }

    @Test
    void assertThatItWillGetTheCorrectWorkingThread() {

        String logLine = "2010-10-06 09:02:11,550 [WorkerThread-4] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373729338-5317] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";

        assertEquals("WorkerThread-4", regexOps.getWorkingThreadId(logLine));


    }

    @Test
    void assertThatItWillGetAnEmptyWorkingThread() {

        String logLine = "2010-10-06 09:02:10,498 [RenderingQueue] INFO  [RenderingQueue]: Waiting for next command...";

        assertEquals("", regexOps.getWorkingThreadId(logLine));


    }

}