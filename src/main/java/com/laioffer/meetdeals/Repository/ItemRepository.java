package com.laioffer.meetdeals.Repository;

import com.laioffer.meetdeals.Model.Item;
import com.laioffer.meetdeals.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Modifying
    @Query(value = "UPDATE Item it SET it.price = ?2 WHERE it.id = ?1")
    void editItem(int itemId, int price);

    @Query(value = "SELECT i FROM Item i WHERE i.id = ?1") //i.id,i.name,i.description,i.status,i.price,i.zipcode,i.category,i.quality,i.image
    Item findItemById(int id);
}
