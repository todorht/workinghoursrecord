package todorht.blikmak.workinghoursrecords.service.impl;

import org.springframework.stereotype.Service;
import todorht.blikmak.workinghoursrecords.dto.ActivityInformationDto;
import todorht.blikmak.workinghoursrecords.models.ActivityInformation;
import todorht.blikmak.workinghoursrecords.models.EmployeeCard;
import todorht.blikmak.workinghoursrecords.repository.ActivityInformationRepository;
import todorht.blikmak.workinghoursrecords.repository.EmployeeCardRepository;
import todorht.blikmak.workinghoursrecords.service.ActivityInformationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityInformationServiceImpl implements ActivityInformationService {

    private static final String FORMAT2 = "dd-MM-yyyy HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT2);

    private final ActivityInformationRepository activityRepository;
    private final EmployeeCardRepository employeeCardRepository;

    public ActivityInformationServiceImpl(ActivityInformationRepository activityRepository, EmployeeCardRepository employeeCardRepository) {
        this.activityRepository = activityRepository;
        this.employeeCardRepository = employeeCardRepository;
    }


    @Override
    public List<ActivityInformationDto> findAll() {
        return this.activityRepository.findAll().stream()
                .sorted(Comparator.comparing(ActivityInformation::getDateTime).reversed())
                .map(activityInformation -> new ActivityInformationDto(activityInformation.getDateTime().format(formatter),
                        activityInformation.getEmployeeCard().getName(),
                        activityInformation.getActivityType()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityInformationDto> findAllByCardNumber(String cardNumber) {
        return this.activityRepository.findAll().parallelStream()
                .filter(activity -> activity.getEmployeeCard().getID().equals(cardNumber))
                .map(activityInformation -> new ActivityInformationDto(activityInformation.getDateTime().toString(),
                        activityInformation.getEmployeeCard().getName(),
                        activityInformation.getActivityType()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(ActivityInformationDto activityInformationDto) {
        EmployeeCard employeeCard = this.employeeCardRepository.findById(activityInformationDto.getEmployeeName()).orElseThrow();
        String[] parts = activityInformationDto.getDateTime().split(" ");
        String[] data = parts[0].split("-");
        String[] time = parts[1].split(":");
        this.activityRepository.save(new ActivityInformation(LocalDateTime.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(time[0]), Integer.parseInt(time[1]), 0),
                employeeCard,
                activityInformationDto.getActivityType().ordinal()));
    }

    @Override
    public List<ActivityInformationDto> getActivitiesForSelectedDate(int day, int month) {
        return this.activityRepository.findAll().stream().parallel()
                .filter(activityInformation -> activityInformation.getDateTime().getMonthValue()==month
                        && activityInformation.getDateTime().getDayOfMonth()==day)
                .map(activityInformation -> new ActivityInformationDto(activityInformation.getDateTime().toString(),
                activityInformation.getEmployeeCard().getName(),
                activityInformation.getActivityType()))
                .collect(Collectors.toList());
    }


}
