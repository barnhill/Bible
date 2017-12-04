package com.pnuema.simplebible.data.dbt;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Volume {
    @SerializedName("dam_id")
    private String damId;
    @SerializedName("fcbh_id")
    private String fcbhId;
    @SerializedName("volume_name")
    private String volumeName;
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

    public void setDamId(String damId) {
        this.damId = damId;
    }

    public String getFcbhId() {
        return fcbhId;
    }

    public void setFcbhId(String fcbhId) {
        this.fcbhId = fcbhId;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getLangaugeCode() {
        return langaugeCode;
    }

    public void setLangaugeCode(String langaugeCode) {
        this.langaugeCode = langaugeCode;
    }

    public String getLangaugeName() {
        return langaugeName;
    }

    public void setLangaugeName(String langaugeName) {
        this.langaugeName = langaugeName;
    }

    public String getLangaugeEnglish() {
        return langaugeEnglish;
    }

    public void setLangaugeEnglish(String langaugeEnglish) {
        this.langaugeEnglish = langaugeEnglish;
    }

    public String getLanguageIso() {
        return languageIso;
    }

    public void setLanguageIso(String languageIso) {
        this.languageIso = languageIso;
    }

    public String getLanguageIsoTwoB() {
        return languageIsoTwoB;
    }

    public void setLanguageIsoTwoB(String languageIsoTwoB) {
        this.languageIsoTwoB = languageIsoTwoB;
    }

    public String getLanguageIsoTwoT() {
        return languageIsoTwoT;
    }

    public void setLanguageIsoTwoT(String languageIsoTwoT) {
        this.languageIsoTwoT = languageIsoTwoT;
    }

    public String getLanguageIsoOne() {
        return languageIsoOne;
    }

    public void setLanguageIsoOne(String languageIsoOne) {
        this.languageIsoOne = languageIsoOne;
    }

    public String getLanguageIsoName() {
        return languageIsoName;
    }

    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }

    public String getLanguageFamilyCode() {
        return languageFamilyCode;
    }

    public void setLanguageFamilyCode(String languageFamilyCode) {
        this.languageFamilyCode = languageFamilyCode;
    }

    public String getLanguageFamilyName() {
        return languageFamilyName;
    }

    public void setLanguageFamilyName(String languageFamilyName) {
        this.languageFamilyName = languageFamilyName;
    }

    public String getLanguageFamilyEnglish() {
        return languageFamilyEnglish;
    }

    public void setLanguageFamilyEnglish(String languageFamilyEnglish) {
        this.languageFamilyEnglish = languageFamilyEnglish;
    }

    public String getLanguageFamilyIso() {
        return languageFamilyIso;
    }

    public void setLanguageFamilyIso(String languageFamilyIso) {
        this.languageFamilyIso = languageFamilyIso;
    }

    public String getLanguageFamilyIsoTwoB() {
        return languageFamilyIsoTwoB;
    }

    public void setLanguageFamilyIsoTwoB(String languageFamilyIsoTwoB) {
        this.languageFamilyIsoTwoB = languageFamilyIsoTwoB;
    }

    public String getLanguageFamilyIsoTwoT() {
        return languageFamilyIsoTwoT;
    }

    public void setLanguageFamilyIsoTwoT(String languageFamilyIsoTwoT) {
        this.languageFamilyIsoTwoT = languageFamilyIsoTwoT;
    }

    public String getLanguageFamilyOne() {
        return languageFamilyOne;
    }

    public void setLanguageFamilyOne(String languageFamilyOne) {
        this.languageFamilyOne = languageFamilyOne;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Boolean getRightToLeft() {
        return rightToLeft;
    }

    public void setRightToLeft(Boolean rightToLeft) {
        this.rightToLeft = rightToLeft;
    }

    public long getNumberOfArtFiles() {
        return numberOfArtFiles;
    }

    public void setNumberOfArtFiles(long numberOfArtFiles) {
        this.numberOfArtFiles = numberOfArtFiles;
    }

    public long getNumberOfSampleAudio() {
        return numberOfSampleAudio;
    }

    public void setNumberOfSampleAudio(long numberOfSampleAudio) {
        this.numberOfSampleAudio = numberOfSampleAudio;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public List<String> getDeliveryTypes() {
        return deliveryTypes;
    }

    public void setDeliveryTypes(List<String> deliveryTypes) {
        this.deliveryTypes = deliveryTypes;
    }

    public List<String> getResolution() {
        return resolution;
    }

    public void setResolution(List<String> resolution) {
        this.resolution = resolution;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}