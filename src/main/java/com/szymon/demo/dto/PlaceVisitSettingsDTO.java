package com.szymon.demo.dto;

import com.szymon.demo.collections.Address;
import com.szymon.demo.collections.TimeOfVisit;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PlaceVisitSettingsDTO {

    @NotBlank
    @NotNull(message = "Field: 'address' can't be null")
    private Address address;

    @NotBlank
    @NotNull(message = "Field: 'visitDurationInMin' can't be null")
    private int visitDurationInMin;

    private List<TimeOfVisit> generalTimetableForVisits;
}
