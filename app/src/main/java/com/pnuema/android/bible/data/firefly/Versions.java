package com.pnuema.android.bible.data.firefly;

import com.pnuema.android.bible.data.IVersion;
import com.pnuema.android.bible.data.IVersionProvider;

import java.util.List;

import androidx.annotation.NonNull;

public class Versions implements IVersionProvider {
    private List<IVersion> versions;

    public Versions(final List<IVersion> versions) {
        this.versions = versions;
    }

    @NonNull
    public List<IVersion> getVersions() {
        return versions;
    }
}
