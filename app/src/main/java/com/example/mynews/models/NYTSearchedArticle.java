package com.example.mynews.models;

import com.example.mynews.utils.INYTArticle;
import com.example.mynews.utils.NYTPageConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NYTSearchedArticle implements INYTArticle {

    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("lead_paragraph")
    @Expose
    private String leadParagraph;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("multimedia")
    @Expose
    private List<NYTSearchedMultimedia> multimedia = null;
    @SerializedName("headline")
    @Expose
    private NYTHeadline headline;
    @SerializedName("keywords")
    @Expose
    private List<NYTKeyword> keywords = null;
    @SerializedName("pub_date")
    @Expose
    private String pubDate;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("news_desk")
    @Expose
    private String newsDesk;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("byline")
    @Expose
    private NYTByline byline;
    @SerializedName("type_of_material")
    @Expose
    private String typeOfMaterial;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("word_count")
    @Expose
    private Integer wordCount;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("subsection_name")
    @Expose
    private String subsectionName;

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getLeadParagraph() {
        return leadParagraph;
    }

    public void setLeadParagraph(String leadParagraph) {
        this.leadParagraph = leadParagraph;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<NYTSearchedMultimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<NYTSearchedMultimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public NYTHeadline getHeadline() {
        return headline;
    }

    public void setHeadline(NYTHeadline headline) {
        this.headline = headline;
    }

    public List<NYTKeyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<NYTKeyword> keywords) {
        this.keywords = keywords;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public NYTByline getByline() {
        return byline;
    }

    public void setByline(NYTByline byline) {
        this.byline = byline;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSubsectionName() {
        return subsectionName;
    }

    public void setSubsectionName(String subsectionName) {
        this.subsectionName = subsectionName;
    }

    @Override
    public String getTitle() {
        return headline.getMain();
    }

    @Override
    public String getPublishedDate() {
        return pubDate;
    }

    @Override
    public String getUrl() {
        return webUrl;
    }

    @Override
    public String getSnapshotUrl() {
        if (!multimedia.isEmpty()) {
            return NYTPageConstants.BASE_IMAGE_URL + multimedia.get(0).getUrl();
        } else {
            return "";
        }
    }

    @Override
    public String getFullSection() {
        String fullSection = sectionName;

        if (subsectionName != null && !subsectionName.equals(""))
            fullSection += " > " + subsectionName;

        return fullSection.toUpperCase();
    }
}
