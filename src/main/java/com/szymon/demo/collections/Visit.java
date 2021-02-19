package com.szymon.demo.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "Visit")
public class Visit implements Comparable<Visit> {

    private Date expireAt;

    private int expireAfterSeconds = 60;

    private int duration;

    private String typeVisit;

    private String idDoctor;

    private String idPatient;

    public Visit() {
    }

    public Visit(int duration, String typeVisit, Date timeVisit, String idDoctor, String idPatient) {
        this.duration = duration;
        this.typeVisit = typeVisit;
        this.expireAt = timeVisit;
        this.idDoctor = idDoctor;
        this.idPatient = idPatient;
    }

    public Date getTimeVisit() {
        return expireAt;
    }

    public void setTimeVisit(Date timeVisit) {
        this.expireAt = timeVisit;
    }

    @Override
    public int compareTo(Visit o) {
        return this.expireAt.compareTo(o.expireAt);
    }
}


