package de.schulung.spring.accounts.persistence;

import de.schulung.spring.accounts.domain.CustomerState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerEntityRepository
  extends JpaRepository<CustomerEntity, UUID> {

  // find customers by state

  //@Query("select c from Customer c where c.state = :state")
  //Collection<CustomerEntity> myQuery(@Param("state") CustomerState state);

  List<CustomerEntity> findAllByState(CustomerState state);

}
