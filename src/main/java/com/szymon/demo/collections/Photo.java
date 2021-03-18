package com.szymon.demo.collections;

import lombok.Data;
import org.bson.types.Binary;

@Data
public class Photo {

    private String title;

    Long timestamp;

    private String format;

    private Binary image;

    public Photo(String title, Binary image, String format, Long timestamp) {
        this.title = title;
        this.format = format;
        this.image = image;
        this.timestamp = timestamp;
    }
}
