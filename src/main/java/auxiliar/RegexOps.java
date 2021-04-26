package auxiliar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/***
 *
 * The Regex class helps analysing each log line and using sstreams and filters to filter desired lines
 * or occurrences.
 *
 * The class has methods that help me find the getRendering, startRendering commands
 * As well as finding book and page ID, as well as timestamp from a specific log line.
 */

public final class RegexOps {


    public RegexOps() {
    }


    public String getTimeStampFromString(String string) {
        String TIMESTAMP_REGEX = "^(([2-9][0-9][0-9][0-9])-([0-1][0-9])-(0?[1-9]|[12][0-9]|3[01]) ([0-2][0-9]):([0-9]|[0-5][0-9]):([0-9]|[0-5][0-9]),([0-9][0-9][0-9]))";
        Pattern p = Pattern.compile(TIMESTAMP_REGEX);
        Matcher m = p.matcher(string);

        if (m.find()) {
            return m.group(1);
        }
        return "";
    }


    public boolean isStartRenderingCommand(String line) {
        String EXECUTING_REQUEST_REGEX = "\\[(ServiceProvider)\\]: (Executing request startRendering with arguments) \\[\\d+, \\d+\\] (on service object)";
        Pattern p = Pattern.compile(EXECUTING_REQUEST_REGEX);
        Matcher m = p.matcher(line);

        return m.find();
    }

    public boolean isGetRenderingCommand(String line) {
        if (line == null)
            return false;

        String executing_get_rendering = "Executing request getRendering with arguments";
        String on_service_object = "on service object";

        return line.contains(executing_get_rendering) && line.contains(on_service_object);
    }


    public String[] getBookAndPageFromStartRendering(String command) {
        String BOOK_ID_AND_PAGE_NUMBER_REGEX = "(\\[(\\d+,\\s+\\d+)\\]|\\[(\\d+,\\d+)\\])";
        Pattern p = Pattern.compile(BOOK_ID_AND_PAGE_NUMBER_REGEX);
        Matcher m = p.matcher(command);

        if (m.find()) {
            String aux = m.group(0).replaceAll("\\[", "").replaceAll("\\]", "").trim();
            return aux.split(",");
        }

        return null;

    }


    public boolean hasStartRenderingServiceUid(String line) {
        String STARTED_RENDERING_REGEX = "\\[ServiceProvider\\]: Service startRendering returned\\s\\d+-\\d+";
        Pattern p = Pattern.compile(STARTED_RENDERING_REGEX);
        Matcher m = p.matcher(line);

        return m.find();
    }


    public String getStartRenderingUid(String command) {
        String COMMAND_UID_REGEX = "(returned\\s+\\d+-\\d+)";
        Pattern p = Pattern.compile(COMMAND_UID_REGEX);
        Matcher m = p.matcher(command);

        if (m.find()) {
            return m.group(0).replace("returned", "").trim();
        }

        return "";

    }

    public String getWorkingThreadId(String command) {
        String COMMAND_UID_REGEX = "(WorkerThread-\\d+)";
        Pattern p = Pattern.compile(COMMAND_UID_REGEX);
        Matcher m = p.matcher(command);

        if (m.find()) {
            return m.group(0).trim();
        }

        return "";

    }

    public String getUIDfromGetRenderingLogLine(String logLine) {

        String pattern = "(\\[\\d+-\\d+\\])";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(logLine);

        if (m.find()) {
            return m.group(0).trim().replace("[","").replace("]","");
        }

        return "";

    }
}
