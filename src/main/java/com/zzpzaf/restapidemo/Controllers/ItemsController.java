package com.zzpzaf.restapidemo.Controllers;

import java.util.List;

import com.zzpzaf.restapidemo.Repositories.ItemsRepo;
import com.zzpzaf.restapidemo.dataObjects.Item;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api")
public class ItemsController {

    @Autowired
    private ItemsRepo repo;

   // private final Log logger = LogFactory.getLog(getClass());

    @GetMapping(value = "/items")
    public ResponseEntity<List<Item>> getAllItems() {

        //logger.info("===== Items Controller ======");

        try {
        
            return new ResponseEntity<>(repo.getItems(), HttpStatus.OK);    

        } catch (Exception e) {
            
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        
        
    }
    
}
