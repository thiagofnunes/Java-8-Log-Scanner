package pojo;

import auxiliar.RegexOps;

/***
 *
 * Class responsible for analysing the Start Rendering commands, as well as finding its respective uid.
 *
 */

public final class NewRenderingInstance {

    private String newRenderingId;
    private String documentId;
    private String documentPage;
    private String workingThreadId;
    private String startRenderingUID;
    private String startRenderingTimestamp;


    public NewRenderingInstance(String logLine) {

        RegexOps regexOps = new RegexOps();

        String[] bookIdAndPage = regexOps.getBookAndPageFromStartRendering(logLine);

        if (bookIdAndPage == null || bookIdAndPage.length != 2) {
            throw new IllegalArgumentException();
        }

        this.documentId = bookIdAndPage[0];
        this.documentPage = bookIdAndPage[1].trim();
        this.newRenderingId = this.documentId + "-" + this.documentPage;
        this.startRenderingTimestamp = regexOps.getTimeStampFromString(logLine);
        this.workingThreadId = regexOps.getWorkingThreadId(logLine);


    }

    public String getDocumentId() {
        return documentId;
    }

    public String getDocumentPage() {
        return documentPage;
    }

    public String getStartRenderingUID() {
        return startRenderingUID;
    }


    public void setStartRenderingUID(String startRenderingUID) {
        this.startRenderingUID = startRenderingUID;
        this.newRenderingId = this.newRenderingId + "-" + startRenderingUID;
    }

    public String getStartRenderingTimestamp() {
        return startRenderingTimestamp;
    }


    public String getNewRenderingId() {
        return newRenderingId;
    }

    public String getWorkingThreadId() {
        return workingThreadId;
    }

    @Override
    public String toString() {
        return "NewRenderingInstance{" +
                "newRenderingId='" + newRenderingId + '\'' +
                ", documentId='" + documentId + '\'' +
                ", documentPage='" + documentPage + '\'' +
                ", workingThreadId='" + workingThreadId + '\'' +
                ", startRenderingUID='" + startRenderingUID + '\'' +
                ", startRenderingTimestamp='" + startRenderingTimestamp + '\'' +
                "}\n";
    }
}
