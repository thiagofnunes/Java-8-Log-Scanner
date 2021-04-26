package thread;

import auxiliar.RegexOps;
import pojo.GetRenderingInstance;
import repository.RenderingRepository;

import java.util.List;
import java.util.stream.IntStream;

/***
 *
 * The Get Rendering Analyser is responsible for analysing all the Get Rendering commands
 * found on the log file, as well as storing each occurrence to the Rendering Repository.
 * After all lines have been analysed it informs the Log Analyser so it can call the other analyser,
 * the Start Rendering analyser which analyses all the Get Rendering lines.
 *
 */

public class GetRenderingAnalyser extends Thread {

    private List<String> listOfLines;
    private final CommunicationInterface communicationInterface;

    public GetRenderingAnalyser(List<String> fileContent, CommunicationInterface communicationInterface) {
        listOfLines = fileContent;
        this.communicationInterface = communicationInterface;
    }

    @Override
    public void run() {
        System.out.println("Get Rendering started...\nAnalysing all the Get Rendering occurrences...");
        RegexOps regexOps = new RegexOps();

        long numberOfTotalGetRenderingOccurrences = listOfLines.stream().parallel().filter(regexOps::isGetRenderingCommand).count();
        RenderingRepository.setGetRenderingCounter(numberOfTotalGetRenderingOccurrences);

        listOfLines.stream().parallel().filter(regexOps::isGetRenderingCommand)
                .forEach(this::createNewGetRenderingObjectAndAddToMap);

    }

    private void createNewGetRenderingObjectAndAddToMap(String getRenderingLine) {
        GetRenderingInstance instance = new GetRenderingInstance(getRenderingLine);
        RenderingRepository.addGetRenderingItem(instance);
        if(RenderingRepository.hasAllGetRenderingLinesBeenAnalysed())
        {

            communicationInterface.callStartRenderingAnalyser();


        }
    }
}
