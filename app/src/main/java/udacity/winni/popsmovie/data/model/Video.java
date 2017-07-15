package udacity.winni.popsmovie.data.model;

/**
 * Created by winniseptiani on 7/6/17.
 */

public class Video {

    public static final String TRAILER = "Trailer";

    public static final String TEASER = "Teaser";

    public static final String CLIP = "Clip";

    public static final String FEATURETTE = "Featurette";

    private String id;

    private String iso_639_1;

    private String iso_3166_1;

    private String key;

    private String name;

    private String site;

    private int size;

    private String type;

    public String getId() {
        return id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
