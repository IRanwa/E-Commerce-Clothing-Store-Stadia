package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.OrderProducts;
import ModelClasses.OrderProductsIdentity;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts, OrderProductsIdentity>{

}
