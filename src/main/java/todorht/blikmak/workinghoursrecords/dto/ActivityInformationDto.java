package todorht.blikmak.workinghoursrecords.dto;

import lombok.Getter;
import todorht.blikmak.workinghoursrecords.models.enums.ActivityType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ActivityInformationDto {

    private String dateTime;
    private String employeeName;
    private String employeeId;
    private ActivityType activityType;

    public ActivityInformationDto(String date, String employeeName, ActivityType activityType) {
        this.dateTime = date;
        this.employeeName = employeeName;
        this.activityType = activityType;
    }
}

