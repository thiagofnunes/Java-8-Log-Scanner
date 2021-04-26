package pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/***
 *
 * The summary class was created simply for the ease of adding the counters to the xml file
 * in an easy way.
 *
 */
public class Summary {

    @XmlElement(name = "count")
    private int totalRenderings;
    @XmlElement(name = "duplicates")
    private int totalDuplicates;
    @XmlElement(name = "unnecessary")
    private int totalUnnecessary;

    public Summary()
    {
        totalDuplicates = 0;
        totalRenderings = 0;
        totalUnnecessary = 0;
    }

    public Summary(List<Rendering> listOfRenderingObjects)
    {
        totalRenderings = listOfRenderingObjects.size();

        listOfRenderingObjects.forEach(object -> {
            totalDuplicates += object.getDuplicates();
            totalUnnecessary += object.getUnnecessary();
        });
    }


}
