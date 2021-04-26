package auxiliar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class StringOps {

    /**
     *
     * Converts the desired String object into a list of the different lines split by a newline.
     *
     * @param fileContent string
     * @return a List of the fileContent separated by newline
     */
    public static List<String> getLogsFileSeparatedByNewLine(String fileContent) {
        if(fileContent==null)
            return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(fileContent.split("\\n")));
    }

    public static String getInputStringFromUser(Scanner scanner) {
        System.out.print("=> ");
        return scanner.nextLine();
    }
}
