package com.laioffer.meetdeals.Service;

import com.laioffer.meetdeals.Model.Item;
import com.laioffer.meetdeals.Model.User;
import com.laioffer.meetdeals.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ItemService {
    private ItemRepository itemRepository;
    private UserService userService;
    private ItemService itemService;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void uploadItem(Item item) {
        //logic
        itemRepository.save(item);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editItem(int itemId, int price) throws ItemNotExitException {
        Item item = itemRepository.findItemById(itemId);
        if (item == null) {
            throw new ItemNotExitException("Item is invalid.");
        }
        itemRepository.editItem(itemId, price);

    }
}