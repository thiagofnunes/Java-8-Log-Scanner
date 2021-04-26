package thread;

import auxiliar.RegexOps;
import pojo.NewRenderingInstance;
import repository.RenderingRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/***
 *
 * The Start rendering analyser is called after all the Get Rendering lines have been analysed.
 * It finds all the new start Rendering commands, as well as all their respective UIDs for each.
 * Similar to the Get Rendering, it uses the Communication Interface to communicate with the Log Analyser after
 * it finishes its work.
 */

public class StartRenderingAnalyser{
    private final List<String> logLines;
    private final CommunicationInterface communicationInterface;
    RegexOps regexOps;

    public StartRenderingAnalyser(List<String> logLines, CommunicationInterface communicationInterface) {
        this.logLines = logLines;
        this.communicationInterface = communicationInterface;
    }

    public void startRenderingAnalyser(){
        System.out.println("Start Rendering started...\nAnalysing all the Start Rendering occurrences...");

        regexOps = new RegexOps();
        long numberOfTotalStartRenderingWithPageAndBookInformation = logLines.stream().filter(regexOps::isStartRenderingCommand).count();

        RenderingRepository.setNewRenderingCounter(numberOfTotalStartRenderingWithPageAndBookInformation);

        IntStream.range(0, logLines.size())
                .filter(position -> regexOps.isStartRenderingCommand(logLines.get(position)))
                .forEach(occurrence -> {
                    List<String> subList = logLines;
                    subList = subList.subList(occurrence, logLines.size());
                    renderObject(subList);
                });

    }
        private void renderObject(List<String> subList) {

            if (subList == null || subList.isEmpty()) {
                return;
            }

            String initialLine = subList.get(0);

            try {
                RegexOps regexOps = new RegexOps();

                NewRenderingInstance newRenderingInstance = new NewRenderingInstance(initialLine);
                String workingThreadId = newRenderingInstance.getWorkingThreadId();

                Optional<String> firstOccurrence = subList.stream().filter(line -> line.contains(workingThreadId)).filter(regexOps::hasStartRenderingServiceUid).findFirst();

                if (firstOccurrence.isPresent()) {

                    String uid = regexOps.getStartRenderingUid(firstOccurrence.get());
                    if (!uid.isEmpty()) {
                        newRenderingInstance.setStartRenderingUID(uid);
                        RenderingRepository.addNewRenderingItem(newRenderingInstance);
                        if (RenderingRepository.hasAllNewRenderingLinesBeenAnalysed()) {
                            communicationInterface.combineObjects();
                        }
                    }
                }


            } catch (IllegalArgumentException exception) {
                exception.printStackTrace();


            }
        }



}

