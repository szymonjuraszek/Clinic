package com.szymon.demo.collections;


import org.springframework.data.annotation.Id;

import java.util.Date;

public class Visit {

    //@Column(name = "duration")
    private int duration;
    //@Column(name = "type_visit")
    private String typeVisit;

    @Id
    private Date timeVisit;

    /*@ManyToOne
    @JoinColumn(name = "doctor_id")*/
    private Doctor doctor;
    /*@ManyToOne
    @JoinColumn(name = "patient_id")*/
    private Patient patient;

    public Visit() {
    }

    public Visit(int duration, String typeVisit, Date timeVisit, Doctor doctor, Patient patient) {
        this.duration = duration;
        this.typeVisit = typeVisit;
        this.timeVisit = timeVisit;
        this.doctor = doctor;
        this.patient = patient;
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
        return timeVisit;
    }

    public void setTimeVisit(Date timeVisit) {
        this.timeVisit = timeVisit;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    @Override
    public String toString() {
        return "Visit{" +
                "duration=" + duration +
                ", typeVisit='" + typeVisit + '\'' +
                ", timeVisit=" + timeVisit +
                ", doctor=" + doctor +
                ", patient=" + patient +
                '}';
    }
}


