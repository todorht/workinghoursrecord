package todorht.blikmak.workinghoursrecords.service.impl;

import org.springframework.stereotype.Service;
import todorht.blikmak.workinghoursrecords.dto.EmployeeCardDto;
import todorht.blikmak.workinghoursrecords.models.EmployeeCard;
import todorht.blikmak.workinghoursrecords.repository.EmployeeCardRepository;
import todorht.blikmak.workinghoursrecords.service.EmployeeCardService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {

    private final EmployeeCardRepository repository;

    public EmployeeCardServiceImpl(EmployeeCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EmployeeCardDto> findAll() {
        return this.repository.findAll().stream()
                .map(employeeCard -> new EmployeeCardDto(employeeCard.getID(),employeeCard.getName(), employeeCard.getSurname()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeCard> save(EmployeeCardDto employeeCardDto) {
        return Optional.of(repository.save(new EmployeeCard(employeeCardDto.getCardNumber(),
                employeeCardDto.getName(),
                employeeCardDto.getSurname())));
    }

    @Override
    public Optional<EmployeeCard> findByCardNumber(String cardNumber) {
        return this.repository.findById(cardNumber);
    }
}
