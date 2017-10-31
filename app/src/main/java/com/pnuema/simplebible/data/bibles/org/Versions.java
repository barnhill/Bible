package com.pnuema.simplebible.data.bibles.org;

import com.pnuema.simplebible.data.IVersion;
import com.pnuema.simplebible.data.IVersionProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Versions implements Serializable, IVersionProvider {
    public VersionsResponse response;

    @Override
    public List<IVersion> getVersions() {
        if (response == null) {
            return null;
        }

        List<IVersion> listIVersion = new ArrayList<>();
        listIVersion.addAll(response.versions);

        return listIVersion;
    }

    private class VersionsResponse implements Serializable {
        private List<Version> versions;
        private Meta meta;
    }

    public class Version implements Serializable, IVersion {
        private String id;
        private String name;
        private String lang;
        private String lang_code;
        private String contact_url;
        private String audio;
        private String copyright;
        private String info;
        private String lang_name;
        private String lang_name_eng;
        private String abbreviation;
        private String updated_at;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getDisplayText() {
            return name;
        }

        @Override
        public String getLanguage() {
            return lang;
        }

        @Override
        public String getAbbreviation() {
            return abbreviation;
        }
    }
}
