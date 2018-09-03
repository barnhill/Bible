package com.pnuema.bible.data.firefly;

import com.pnuema.bible.data.IVersion;
import com.pnuema.bible.data.IVersionProvider;

import java.util.List;

public class Versions implements IVersionProvider {
    private List<IVersion> versions;

    public Versions(final List<IVersion> versions) {
        this.versions = versions;
    }

    public List<IVersion> getVersions() {
        return versions;
    }
}
