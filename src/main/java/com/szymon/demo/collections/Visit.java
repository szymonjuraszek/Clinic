package com.szymon.demo.collections;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Visit")
public class Visit implements Comparable<Visit>{


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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTypeVisit() {
        return typeVisit;
    }

    public void setTypeVisit(String typeVisit) {
        this.typeVisit = typeVisit;
    }

    public Date getTimeVisit() {
        return expireAt;
    }

    public void setTimeVisit(Date timeVisit) {
        this.expireAt = timeVisit;
    }

    public String getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(String idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }


    @Override
    public String toString() {
        return "Visit{" +
                "duration=" + duration +
                ", typeVisit='" + typeVisit + '\'' +
                ", timeVisit=" + expireAt +
                ", idDoctor=" + idDoctor +
                ", idPatient=" + idPatient +
                '}';
    }

    @Override
    public int compareTo(Visit o) {
        return this.expireAt.compareTo(o.expireAt);
    }
}


