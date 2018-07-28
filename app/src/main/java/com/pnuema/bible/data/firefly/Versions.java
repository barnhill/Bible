package com.pnuema.bible.data.firefly;

import java.util.List;

public class Versions {
    private List<Version> versions;

    public Versions(final List<Version> versions) {
        this.versions = versions;
    }

    public List<Version> getVersions() {
        return versions;
    }
}
