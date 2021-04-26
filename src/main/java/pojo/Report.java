package pojo;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * The report class is the class used to combine everything
 * and writing to an xml file.
 *
 */
@XmlRootElement(name = "report")
public class Report {

    @XmlElement(name = "rendering")
    private List<Rendering> listOfRenderingObjects;
    @XmlElement(name = "summary")
    private Summary summary;

    public Report() {
        listOfRenderingObjects = new ArrayList<>();
        summary = new Summary();

    }

    public Report(List<Rendering> listOfRenderingObjects) {
        this();
        if (listOfRenderingObjects != null) {
            this.listOfRenderingObjects = listOfRenderingObjects;
            summary = new Summary(listOfRenderingObjects);
        }

    }
}
