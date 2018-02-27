package com.hnshilin.ddwallet.mod;

/**
 * Created by zhuxi on 2017/8/22.
 */
public class HotFunctionBean {
    private String title;
    private String content;
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "HotFunctionBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
