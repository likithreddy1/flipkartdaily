package com.flipkartdaily.repository;

import com.flipkartdaily.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

    Optional<Item> findByBrandAndCategoryAndPrice(String Brand, String  Category,int price);
}
