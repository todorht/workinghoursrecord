package todorht.blikmak.workinghoursrecords.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todorht.blikmak.workinghoursrecords.models.EmployeeCard;

@Repository
public interface EmployeeCardRepository extends JpaRepository<EmployeeCard, String> {

}
