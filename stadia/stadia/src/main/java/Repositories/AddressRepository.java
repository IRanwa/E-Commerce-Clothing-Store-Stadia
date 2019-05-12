package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
