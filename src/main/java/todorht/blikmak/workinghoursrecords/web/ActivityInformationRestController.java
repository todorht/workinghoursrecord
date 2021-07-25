package todorht.blikmak.workinghoursrecords.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todorht.blikmak.workinghoursrecords.dto.ActivityInformationDto;
import todorht.blikmak.workinghoursrecords.dto.EmployeeCardDto;
import todorht.blikmak.workinghoursrecords.models.enums.ActivityType;
import todorht.blikmak.workinghoursrecords.service.ActivityInformationService;
import todorht.blikmak.workinghoursrecords.service.EmployeeCardService;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://192.168.0.43:8081")
public class ActivityInformationRestController {

    private final ActivityInformationService activityInformationService;
    private final EmployeeCardService employeeCardService;

    public ActivityInformationRestController(ActivityInformationService activityInformationService, EmployeeCardService employeeCardService) {
        this.activityInformationService = activityInformationService;
        this.employeeCardService = employeeCardService;
    }

    @GetMapping()
    public ResponseEntity<List<ActivityInformationDto>> getActivities(){
            List<ActivityInformationDto> activityInformationList = activityInformationService.findAll();
            if(activityInformationList == null){
                return ResponseEntity.notFound().build();
            }else return ResponseEntity.ok().body(activityInformationList);
    }

    @PostMapping("/insert")
    public void makeCorrection(@RequestBody ActivityInformationDto activityInformationDto){
        activityInformationService.save(activityInformationDto);
    }

    @GetMapping("/daily")
    private ResponseEntity<List<ActivityInformationDto>> getActivitiesDaily(@RequestParam(name= "day", required = false) Integer day,
                                                                                        @RequestParam(name = "month", required = false) Integer month){
        List<ActivityInformationDto> result = this.activityInformationService.getActivitiesForSelectedDate(day,month);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<List<ActivityInformationDto>> getActivitiesByEmployee(@PathVariable(name = "cardNumber") String cardNum) {
        List<ActivityInformationDto> activities = this.activityInformationService.findAllByCardNumber(cardNum);
        if (activities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(activities);
    }

    @GetMapping("/employee-cards")
    public ResponseEntity<List<EmployeeCardDto>> getEmployeeCards() {
        List<EmployeeCardDto> employees = this.employeeCardService.findAll();
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping("/types")
    public ResponseEntity<List<ActivityType>> getActivityTypes(){
       return ResponseEntity.ok().body(Arrays.asList(ActivityType.values()));
    }
}
