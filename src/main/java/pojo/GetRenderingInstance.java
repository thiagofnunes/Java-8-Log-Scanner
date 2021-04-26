package pojo;


import auxiliar.RegexOps;

/***
 *
 * Get Rendering class, that retrieves its timestamp and uid from a getRendering log line.
 *
 */

public class GetRenderingInstance {

    private String timestamp;
    private String uid;

    public GetRenderingInstance(String logLine)
    {   RegexOps regexOps = new RegexOps();
        timestamp = regexOps.getTimeStampFromString(logLine);
        uid = regexOps.getUIDfromGetRenderingLogLine(logLine);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public String toString() {
        return "GetRenderingInstance{" +
                "timestamp='" + timestamp + '\'' +
                ", uid='" + uid + '\'' +
                "}\n";
    }
}
