package com.accenture.fl.controller;

import com.accenture.bl.entity.Flower;
import com.accenture.fl.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class FlowerController {

    private final FlowerService flowerService;

    @Autowired
    public FlowerController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @RequestMapping(value = "/flowerbyname", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Flower findByName(@RequestParam("name") String name) {
        return flowerService.findByName(name);
    }

    @RequestMapping(value = "/flowerbyprice", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<Flower> findByPrice(@RequestParam(required = false, value = "from", defaultValue = "0") double from,
                                    @RequestParam(required = false, value = "to", defaultValue = "0") double to) {
        return flowerService.findbyPrice(from, to);
    }

    @RequestMapping(value = "/flower/view", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<Flower> get(@RequestParam(required = false, value = "name") String name,
                            @RequestParam(required = false, value = "from", defaultValue = "0") double from,
                            @RequestParam(required = false, value = "to", defaultValue = "0") double to) {
        return flowerService.get(name, from, to);
    }

    @RequestMapping(value = "/flower/updateQuantity", method = RequestMethod.PUT, produces = {"application/json"})
    @ResponseBody
    public Flower updateQuantity(@RequestParam(required = false, value = "name") String name,
                                 @RequestParam(required = false, value = "quantity", defaultValue = "0") int quantity) {
        return flowerService.updateQuantity(name, quantity);
    }

}
