package ru.naissur.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naissur.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findByPriceBetween(int startPrice, int endPrice, Sort sort);

}
