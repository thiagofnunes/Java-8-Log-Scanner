package auxiliar;

import pojo.Report;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOps {


    /**
     *
     * Reads the desired log file into a String object.
     *
     * @param path to the desired file
     * @return The desired file as a String object
     * @throws IOException
     */
    public static String readLogFileToString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    /**
     *
     * Simple method that checks the file type, if its not a directory and if it ends with the .log extension.
     *
     * @param path to the desired file
     * @return true if it matches the desired filters, otherwise false
     */
    public static boolean checkIfFileExistsAndIsNotADirectoryAndIsALogFile(String path) {
        File f = new File(path);
        return f.exists() && !f.isDirectory() && path.toLowerCase().endsWith(".log");
    }

    /**
     *
     * Gets the line count from the desired file by splitting based on a newline ("\n")
     *
     * @param fileContent from the {@link #readLogFileToString(String)}
     * @return the number of lines separated by \n from the desired file.
     */
    public static int getLineCounts(String fileContent)
    {   if(fileContent == null)
            return 0;
        return fileContent.split("\\n").length;
    }


    /**
     *
     * Receives the desired report object and converts it to a .xml file
     *
     * @param report that you want to write into the file
     * @throws JAXBException if cannot convert the desired report to .xml format
     */
    public void writeReportToXml(Report report) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(Report.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        if(report==null)
            report = new Report();
        mar.marshal(report, new File("./report.xml"));
        System.out.println("Report file written successfully at ./report.xml with the .xml extension");
    }
}
