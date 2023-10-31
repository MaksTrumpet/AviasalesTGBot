package MTCompany.dao;

import MTCompany.entity.UserRequestData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRequestDataDao extends JpaRepository<UserRequestData, Long> {
}
