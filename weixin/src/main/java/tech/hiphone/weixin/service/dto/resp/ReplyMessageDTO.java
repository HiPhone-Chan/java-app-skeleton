package tech.hiphone.weixin.service.dto.resp;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import tech.hiphone.weixin.constants.MsgType;

@JacksonXmlRootElement(localName = "xml")
public class ReplyMessageDTO {

    @JacksonXmlCData
    @JsonProperty("ToUserName")
    private String toUserName;
    @JacksonXmlCData
    @JsonProperty("FromUserName")
    private String fromUserName;
    @JacksonXmlCData
    @JsonProperty("CreateTime")
    private Integer createTime = (int) System.currentTimeMillis();
    @JacksonXmlCData
    @JsonProperty("MsgType")
    private String msgType;
    @JacksonXmlCData
    @JsonProperty("Content")
    private String content;

    @JacksonXmlCData
    @JsonProperty("Image")
    private Media image;

    @JsonProperty("ArticleCount")
    private Integer articleCount;

    @JsonProperty("item")
    @JacksonXmlElementWrapper(localName = "Articles")
    private List<ArticleItem> articles;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Media getImage() {
        return image;
    }

    public void setImage(Media image) {
        this.image = image;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public List<ArticleItem> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleItem> articles) {
        this.articles = articles;
        this.articleCount = articles == null ? null : articles.size();
    }

    public void addArticle(ArticleItem articleItem) {
        if (this.articles == null) {
            this.articles = new ArrayList<>();
        }
        this.articles.add(articleItem);
        this.articleCount = this.articles.size();
    }

    public void setMedia(String msgType, String mediaId) {
        switch (msgType) {
        case MsgType.IMAGE:
            this.image = new Media(mediaId);
            break;
        default:
            break;
        }
        this.msgType = msgType;
    }

    public class Media {
        @JacksonXmlCData
        @JsonProperty("MediaId")
        private String mediaId;

        public Media() {
        }

        public Media(String mediaId) {
            this.mediaId = mediaId;
        }

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }
    }

    public static class ArticleItem {
        @JacksonXmlCData
        @JsonProperty("Title")
        private String title;
        @JacksonXmlCData
        @JsonProperty("Description")
        private String description;
        @JacksonXmlCData
        @JsonProperty("PicUrl")
        private String picUrl;
        @JacksonXmlCData
        @JsonProperty("Url")
        private String url;

        public ArticleItem() {
        }

        public ArticleItem(String title, String description, String picUrl, String url) {
            super();
            this.title = title;
            this.description = description;
            this.picUrl = picUrl;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }
}
