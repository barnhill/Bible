package com.pnuema.android.bible.data.firefly;

import com.pnuema.android.bible.data.IVersion;

import androidx.annotation.NonNull;

public class Version implements IVersion {
    private int id;
    private String abbreviation;
    private String language;
    private String version;
    private String url;
    private String publisher;
    private String copyright;
    private String copyright_info;

    @Override
    public int getId() {
        return id;
    }

    @NonNull
    @Override
    public String getDisplayText() {
        return getVersion();
    }

    public void setId(final int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @NonNull
    @Override
    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(final String publisher) {
        this.publisher = publisher;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(final String copyright) {
        this.copyright = copyright;
    }

    public String getCopyright_info() {
        return copyright_info;
    }

    public void setCopyright_info(final String copyright_info) {
        this.copyright_info = copyright_info;
    }
}
