import auxiliar.*;
import pojo.Rendering;
import pojo.NewRenderingInstance;
import pojo.Report;
import repository.RenderingRepository;
import thread.LogsAnalyser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWelcome, please indicate the file's absolute path for analysis: ");
        String path = StringOps.getInputStringFromUser(scanner);

        while (!FileOps.checkIfFileExistsAndIsNotADirectoryAndIsALogFile(path)) {

            if (path.equals("q")) {
                System.out.println("Program is closing.");
                System.exit(0);
            }

            System.out.println("It is not a *.log file, please indicate a correct file absolute path or enter q to exit.");
            path = StringOps.getInputStringFromUser(scanner);

        }

        System.out.println("\nFound selected file with path: {" + path + "}");
        String file = FileOps.readLogFileToString(path);
        System.out.println("Line count from file: {" + FileOps.getLineCounts(file) + "} line(s).");


        List<String> lines = StringOps.getLogsFileSeparatedByNewLine(file);

        if(!lines.isEmpty()) {
            LogsAnalyser logsAnalyser = new LogsAnalyser(lines);
            logsAnalyser.startGetRenderingAnalyser();
        }


    }
}
