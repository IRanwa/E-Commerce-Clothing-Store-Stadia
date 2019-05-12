package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
