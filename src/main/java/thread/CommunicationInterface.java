package thread;

import java.util.List;

/***
 *
 * The Communication Interface is a interface that allows different threads,
 * the GetRendering and StartRendering analysers to communicate with the Logs Analyser,
 * which is the main class invoking them, knowing that they have finished their respective jobs.
 * This interface was designed with the Observer behavioral Design Pattern in mind.
 */
public interface CommunicationInterface {
    void callStartRenderingAnalyser();
    void combineObjects();
}
