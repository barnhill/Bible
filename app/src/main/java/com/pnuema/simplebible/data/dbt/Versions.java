package com.pnuema.simplebible.data.dbt;

import com.pnuema.simplebible.data.IVersion;
import com.pnuema.simplebible.data.IVersionProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Versions implements IVersionProvider {
    private List<Version> versions;

    public Versions(List<Volume> volumes) {
        if (volumes == null) {
            return;
        }

        versions = new ArrayList<>();
        HashSet<String> dupeSet = new HashSet<>();
        for (Volume volume : volumes) {
            if ("text".equalsIgnoreCase(volume.getMedia()) && dupeSet.add(volume.getVersionCode())) {
                versions.add(new Version(volume.getDamId(), volume.getVolumeName(), volume.getLanguageFamilyIso(), volume.getVersionCode()));
            }
        }
    }

    @Override
    public List<IVersion> getVersions() {
        return new ArrayList<>(versions);
    }

    public class Version implements IVersion {
        private String id;
        private String display;
        private String language;
        private String abbreviation;

        public Version(String id, String display, String language, String abbreviation) {
            this.id = id;
            this.display = display;
            this.language = language;
            this.abbreviation = abbreviation;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getDisplayText() {
            return display;
        }

        @Override
        public String getLanguage() {
            return language;
        }

        @Override
        public String getAbbreviation() {
            return abbreviation;
        }
    }
}
