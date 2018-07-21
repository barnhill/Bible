package com.pnuema.bible.data.dbt;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Volume {
    @SerializedName("dam_id")
    private String damId;
    @SerializedName("fcbh_id")
    private String fcbhId;
    @SerializedName("volume_name")
    private String volumeName;
    @SerializedName("version_name")
    private String versionName;
    private String status;
    private String expiration;
    @SerializedName("language_code")
    private String langaugeCode;
    @SerializedName("language_name")
    private String langaugeName;
    @SerializedName("language_english")
    private String langaugeEnglish;
    @SerializedName("language_iso")
    private String languageIso;
    @SerializedName("language_iso_2B")
    private String languageIsoTwoB;
    @SerializedName("language_iso_2T")
    private String languageIsoTwoT;
    @SerializedName("language_iso_1")
    private String languageIsoOne;
    @SerializedName("language_iso_name")
    private String languageIsoName;
    @SerializedName("language_family_code")
    private String languageFamilyCode;
    @SerializedName("language_family_name")
    private String languageFamilyName;
    @SerializedName("language_family_english")
    private String languageFamilyEnglish;
    @SerializedName("language_family_iso")
    private String languageFamilyIso;
    @SerializedName("language_family_iso_2B")
    private String languageFamilyIsoTwoB;
    @SerializedName("language_family_iso_2T")
    private String languageFamilyIsoTwoT;
    @SerializedName("language_family_iso_1")
    private String languageFamilyOne;
    @SerializedName("version_code")
    private String versionCode;
    @SerializedName("updated_on")
    private String updatedOn;
    @SerializedName("right_to_left")
    private Boolean rightToLeft;
    @SerializedName("num_art")
    private long numberOfArtFiles;
    @SerializedName("num_sample_audio")
    private long numberOfSampleAudio;
    private String sku;
    private String media;
    @SerializedName("media_type")
    private String mediaType;
    @SerializedName("delivery")
    private List<String> deliveryTypes;
    private List<String> resolution;
    @SerializedName("collection_name")
    private String collectionName;

    public String getDamId() {
        return damId;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public String getLanguageFamilyIso() {
        return languageFamilyIso;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getMedia() {
        return media;
    }
}