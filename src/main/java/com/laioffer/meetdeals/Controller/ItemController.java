package com.laioffer.meetdeals.Controller;

import com.amazonaws.services.s3.AmazonS3;
import com.laioffer.meetdeals.Config.AmazonConfig;
import com.laioffer.meetdeals.Model.Item;
import com.laioffer.meetdeals.Model.User;
import com.laioffer.meetdeals.Service.ItemService;
import com.laioffer.meetdeals.Service.S3BucketStorageService;
import com.laioffer.meetdeals.Service.UserNotFoundException;
import com.laioffer.meetdeals.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@Controller
public class ItemController {
    private ItemService itemService;
    private UserService userService;

    @Autowired
    public ItemController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @RequestMapping(value = "/item/upload", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void uploadItem(@RequestParam("seller_email") String sellerEmail,
                           @RequestParam("item_name") String name,// not sure if front end has this field
                           @RequestParam("zipcode") String zipcode,
                           @RequestParam("category") int category,
                           @RequestParam("price") int price,
                           @RequestParam("condition") int condition,
                           @RequestParam("description") String description,
                           @RequestParam("image") MultipartFile image) throws UserNotFoundException {
        //logic
        User seller = userService.findUserByEmail(sellerEmail); // completed by cara
        if (seller == null) {
            throw new UserNotFoundException("Invalid user.");
        }
        AmazonConfig amazonConfig = new AmazonConfig();
        S3BucketStorageService s3 = new S3BucketStorageService();
        s3.amazonS3Client = amazonConfig.s3();
        String url = s3.uploadFile(name, image); // get the url of item image that will store in amazon s3
        Item.Builder builder = new Item.Builder();
        builder.setSeller(seller)
                .setName(name)
                .setZipcode(zipcode)
                .setCategory(category)
                .setPrice(price)
                .setQuality(condition)
                .setDescription(description)
                .setImage(url); // because item repo will store this url in string format
        Item item = builder.build();

        itemService.uploadItem(item);
    }


    @RequestMapping(value = "/item/edit", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void editItem(@RequestParam("item_id") int itemId, @RequestParam("price") int price) {
        //logic
        itemService.editItem(itemId, price);
    }
}