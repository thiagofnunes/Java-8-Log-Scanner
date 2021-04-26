package thread;

import auxiliar.FileOps;
import auxiliar.StringOps;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.GetRenderingInstance;
import pojo.NewRenderingInstance;
import repository.RenderingRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StartRenderingAnalyserTest {

    private List<String> scenario1, scenario2;
    private MockAnalyser mockAnalyser;
    private StartRenderingAnalyser startRenderingAnalyser;

    @BeforeEach
    void setup() throws IOException {
        scenario1 = StringOps.getLogsFileSeparatedByNewLine(FileOps.readLogFileToString("src\\test\\resources\\scenario_1.log"));
        scenario2 = StringOps.getLogsFileSeparatedByNewLine(FileOps.readLogFileToString("src\\test\\resources\\scenario_2.log"));
    }

    @Test
    void testThatItWillReturnTheCorrectSetRenderingAmountFromScenario1() {


        mockAnalyser = new MockAnalyser(2);
        startRenderingAnalyser = new StartRenderingAnalyser(scenario1, mockAnalyser);
        startRenderingAnalyser.startRenderingAnalyser();

    }

    @Test
    void testThatItWillReturnTheCorrectSetRenderingAmountFromScenario2() {


        mockAnalyser = new MockAnalyser(1);
        startRenderingAnalyser = new StartRenderingAnalyser(scenario2, mockAnalyser);
        startRenderingAnalyser.startRenderingAnalyser();

    }

    class MockAnalyser implements CommunicationInterface {
        public long mapSize;

        public MockAnalyser(int expectedMapItems) {
            this.mapSize = expectedMapItems;

        }

        @Override
        public void callStartRenderingAnalyser() {
        //This method is called by the Get Rendering Analyser,
        // so in this case it will not be used.
        }

        @Test
        @Override
        public void combineObjects() {
            Map<String, List<NewRenderingInstance>> newRenderingItems = RenderingRepository.getNewRenderingItems();
            assertTrue(newRenderingItems.size() > 0);
            assertEquals(mapSize,newRenderingItems.size());
            System.out.println(newRenderingItems.toString());
        }
    }

}