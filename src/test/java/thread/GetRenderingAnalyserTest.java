package thread;

import auxiliar.FileOps;
import auxiliar.RegexOps;
import auxiliar.StringOps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.GetRenderingInstance;
import repository.RenderingRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GetRenderingAnalyserTest {

    private List<String> scenario1, scenario2;
    private MockAnalyser mockAnalyser;
    private GetRenderingAnalyser getRenderingAnalyser;

    @BeforeEach
    void setup() throws IOException {
        scenario1 = StringOps.getLogsFileSeparatedByNewLine(FileOps.readLogFileToString("src\\test\\resources\\scenario_1.log"));
        scenario2 = StringOps.getLogsFileSeparatedByNewLine(FileOps.readLogFileToString("src\\test\\resources\\scenario_2.log"));

    }

    //The tests below should be ran separately since there is no semaphore
    // controlling which scenario will finish first.

    @Test
    void testThatItWillReturnTheCorrectGetRenderingAmountFromScenario1() {
        mockAnalyser = new MockAnalyser(2);
        getRenderingAnalyser = new GetRenderingAnalyser(scenario1, mockAnalyser);
        getRenderingAnalyser.start();
    }

    @Test
    void testThatItWillReturnTheCorrectGetRenderingAmountFromScenario2() {
        mockAnalyser = new MockAnalyser(1);
        getRenderingAnalyser = new GetRenderingAnalyser(scenario2, mockAnalyser);
        getRenderingAnalyser.start();
    }


    class MockAnalyser implements CommunicationInterface {
        private long mapSize;

        public MockAnalyser(int expectedMapItems) {
            this.mapSize = expectedMapItems;

        }

        @Override
        @Test
        public void callStartRenderingAnalyser() {
            //In this case we dont want to call the other thread analyser,
            // we want to make sure we got the right getRenders from the analyser.

            Map<String, List<GetRenderingInstance>> getRenderingItems = RenderingRepository.getGetRenderingItems();
            assertEquals(mapSize, getRenderingItems.size());

            System.out.println(getRenderingItems.size());
            System.out.println("Map :" + getRenderingItems.toString());


        }

        @Override
        public void combineObjects() {
            //This is used for the StartRenderingAnalyser to communicate with the Logs Analyser, so in this case it will not be utilized.
        }
    }

}