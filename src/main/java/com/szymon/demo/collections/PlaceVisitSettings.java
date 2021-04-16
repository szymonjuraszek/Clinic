package com.szymon.demo.collections;

import lombok.Data;

import java.util.List;

@Data
public class PlaceVisitSettings {

    private Address address;

    private int visitDurationInMin;

    private List<TimeOfVisit> generalTimetableForVisits;
}
