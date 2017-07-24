package com.pnuema.simplebible.data;

import java.util.List;

public final class Versions {
    public VersionsResponse response;

    public class VersionsResponse {
        List<Version> versions;
        Meta meta;
    }

    public class Version {
        public String id;
        public String name;
        public String lang;
        public String lang_code;
        public String contact_url;
        public String audio;
        public String copyright;
        public String info;
        public String lang_name;
        public String lang_name_eng;
        public String abbreviation;
        public String updated_at;
    }
}
