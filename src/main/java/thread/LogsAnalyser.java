package thread;

import auxiliar.FileOps;
import auxiliar.RegexOps;
import pojo.GetRenderingInstance;
import pojo.NewRenderingInstance;
import pojo.Rendering;
import pojo.Report;
import repository.RenderingRepository;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/***
 *
 * The Log Analyser class is responsible for controlling the other analysers, the Get and Set
 * Rendering Analysers, as well as writing the content to an xml file after they are done.
 *
 */

public class LogsAnalyser implements CommunicationInterface {


    private final List<String> logsList;

    public LogsAnalyser(List<String> logsList) {
        this.logsList = logsList;

    }

    public void startGetRenderingAnalyser() {
        GetRenderingAnalyser getRenderingAnalyser = new GetRenderingAnalyser(logsList, this);
        getRenderingAnalyser.start();
    }

    @Override
    public void callStartRenderingAnalyser() {

        StartRenderingAnalyser startRenderingAnalyser= new StartRenderingAnalyser(logsList, this);
        startRenderingAnalyser.startRenderingAnalyser();
    }

    @Override
    public void combineObjects() {

        System.out.println("Start Rendering finished...\nCombining all the objects together...");

        Map<String, List<GetRenderingInstance>> getRenderingItems = RenderingRepository.getGetRenderingItems();
        Map<String, List<NewRenderingInstance>> newRenderingItems = RenderingRepository.getNewRenderingItems();
        List<Rendering> listOfRendering = addRenderingsTogether(newRenderingItems, getRenderingItems);
        countDuplicatesRenderingWithTheSameUid(listOfRendering);

        System.out.println("About to write the report to the .xml file...");
        Report report = new Report(listOfRendering);
        try {
            new FileOps().writeReportToXml(report);

        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Error writing the report.xml file. Exception caught:"+e.getLocalizedMessage());
        }


    }

    private void countDuplicatesRenderingWithTheSameUid(List<Rendering> listOfRendering) {

        System.out.println("Counting the duplicates for each UID...");
        listOfRendering.forEach(rendering -> {
            long occurrences = logsList.stream().parallel().filter(line -> line.contains(rendering.getUid())).filter(line -> new RegexOps().hasStartRenderingServiceUid(line)).count();
            rendering.countDuplicates(occurrences);
        });


    }

    private List<Rendering> addRenderingsTogether(Map<String, List<NewRenderingInstance>> newRenderingItems, Map<String, List<GetRenderingInstance>> getRenderingItems) {

        List<Rendering> listOfRendering = newRenderingItems.entrySet().stream().map(Rendering::new).collect(Collectors.toList());
        getRenderingItems.entrySet().forEach(entry -> addGetRenderingToRenderingList(entry, listOfRendering));


        return listOfRendering;


    }

    private void addGetRenderingToRenderingList(Map.Entry<String, List<GetRenderingInstance>> entry, List<Rendering> listOfRendering) {

        listOfRendering.stream().filter(rendering -> rendering.getUid().equals(entry.getKey())).forEach(rendering -> rendering.addGetRendering(entry));

    }
}
