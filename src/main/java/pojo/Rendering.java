package pojo;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 *
 * After the Get Rendering and the Set Rendering analysers run,
 * This class is responsible for getting all the objects together for each document,
 * as well as adding their timestamp together and counting the duplicates occurrences.
 *
 */

public class Rendering {

    @XmlElement(name = "document")
    private String documentId;
    @XmlElement(name = "page")
    private String page;
    @XmlElement(name = "uid")
    private String uid;
    private String key;
    private int duplicates;
    private int unnecessary;
    @XmlElement(name = "start")
    private List<String> startRenderingsTimestamps;
    @XmlElement(name = "get")
    private List<String> getRenderingsTimestamps;

    public Rendering(String key, List<NewRenderingInstance> objects) {
        this.key = key;
        startRenderingsTimestamps = new ArrayList<>();
        getRenderingsTimestamps = new ArrayList<>();

        NewRenderingInstance newRenderingInstance = objects.get(0);

        documentId = newRenderingInstance.getDocumentId();
        page = newRenderingInstance.getDocumentPage();
        uid = newRenderingInstance.getStartRenderingUID();

        this.unnecessary = 0;
        this.duplicates = 0;

        objects.forEach(object -> startRenderingsTimestamps.add(object.getStartRenderingTimestamp()));

    }

    public Rendering(Map.Entry<String, List<NewRenderingInstance>> entry) {
        this(entry.getKey(), entry.getValue());
    }


    public int getDuplicates() {
        return duplicates;
    }

    public int getUnnecessary() {
        return unnecessary;
    }

    public String getUid() {
        return uid;
    }

    public void addGetRendering(Map.Entry<String, List<GetRenderingInstance>> entry) {

        entry.getValue().forEach(getRendering -> getRenderingsTimestamps.add(getRendering.getTimestamp()));

        verifyUnnecessaryRenderings();

    }

    private void verifyUnnecessaryRenderings() {

        int getRenderingCounter = getRenderingsTimestamps.size();
        int startRenderingCounter = startRenderingsTimestamps.size();

        if (getRenderingCounter > startRenderingCounter) {
            this.unnecessary = getRenderingCounter - startRenderingCounter;
        }

    }

    public void countDuplicates(long occurrences) {
        this.duplicates = (int) (occurrences - getRenderingsTimestamps.size());
    }
}
