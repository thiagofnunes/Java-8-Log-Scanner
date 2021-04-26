package repository;

import pojo.GetRenderingInstance;
import pojo.NewRenderingInstance;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/***
 *
 * The rendering repository is responsible for storing all the objects that are analysed
 * and the combined together for writing on the xml file.
 * I could have used something else like a file storage or database, but because this was a timed
 * project and the persistance is only for running the program once then static objects
 * where enough.
 *
 */

public class RenderingRepository {

    //These objects store the new Rendering and the Get Rendering objects that are
    //taken during the log file analysis and then are used to write on the xml file.
    private static final Map<String, List<NewRenderingInstance>> newRenderingsMap;
    private static final Map<String, List<GetRenderingInstance>> getRenderingsMap;
    //These atomic variables are set initially to how many get Renderings and how many start Renderings
    //are there on the log lines and are decreased every time one of those lines is successful analysed
    //and stored on its respective map
    private static final AtomicLong newRenderingCounter;
    private static final AtomicLong getRenderingCounter;

    static {
        newRenderingsMap = new ConcurrentHashMap<>();
        getRenderingsMap = new ConcurrentHashMap<>();
        newRenderingCounter = new AtomicLong();
        getRenderingCounter = new AtomicLong();
    }

    public static void addNewRenderingItem(NewRenderingInstance object) {
        String key = object.getNewRenderingId();
        List<NewRenderingInstance> list;
        if (newRenderingsMap.containsKey(key)) {
            list = newRenderingsMap.get(key);
        } else {
            list = new LinkedList<>();
        }
        list.add(object);
        newRenderingsMap.put(key, list);
        newRenderingCounter.decrementAndGet();
    }

    public static Map<String, List<NewRenderingInstance>> getNewRenderingItems() {
        return newRenderingsMap;
    }

    public static void addGetRenderingItem(GetRenderingInstance object) {
        String key = object.getUid();
        List<GetRenderingInstance> list;
        if (getRenderingsMap.containsKey(key)) {
            list = getRenderingsMap.get(key);
        } else {
            list = new LinkedList<>();
        }
        list.add(object);
        getRenderingsMap.put(key, list);
        getRenderingCounter.decrementAndGet();
    }

    public static Map<String, List<GetRenderingInstance>> getGetRenderingItems() {
        return getRenderingsMap;
    }

    public static void setNewRenderingCounter(Long value) {
        newRenderingCounter.set(value);
    }

    public static void setGetRenderingCounter(Long value) {
        getRenderingCounter.set(value);
    }

    public static boolean hasAllGetRenderingLinesBeenAnalysed() {
        return getRenderingCounter.get() == 0;
    }

    public static boolean hasAllNewRenderingLinesBeenAnalysed() {
        return newRenderingCounter.get() == 0;
    }
}
