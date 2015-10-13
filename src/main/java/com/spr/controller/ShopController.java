package com.spr.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spr.exception.ShopNotFound;
import com.spr.model.Shop;
import com.spr.model.enums.Status;
import com.spr.service.ShopService;
import com.spr.validation.ShopValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping(value = "/shop")
public class ShopController {
	
	Logger logger= LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopValidator shopValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(shopValidator);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView newShopPage() {
        ModelAndView mav = new ModelAndView("shop-new", "shop", new Shop());
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createNewShop(@ModelAttribute @Valid Shop shop,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return new ModelAndView("shop-new");
        }

        ModelAndView mav = new ModelAndView();
        String message = "New shop " + shop.getName() + " was successfully created.";
        shop.setStatus(Status.UNASSIGNED);
        shopService.create(shop);
        mav.setViewName("redirect:/index.html");

        redirectAttributes.addFlashAttribute("message", message);
        return mav;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView shopListPage() {
    	logger.info("list {}","shopListPage");
        ModelAndView mav = new ModelAndView("shop-list");
//		List<Shop> shopList = shopService.findAll();
        Pageable pageable = new PageRequest(0, 25);
        // Page<Shop> page = shopService.findByName("r", pageable);
      //  Page<Shop> page = shopService.findByStatus(Status.UNASSIGNED, pageable);
        Page<Shop> page = shopService.findByName("%f%", pageable);
        List<Shop> shopList = page.getContent();

        mav.addObject("shopList", shopList);
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editShopPage(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("shop-edit");
        Shop shop = shopService.findById(id);
        mav.addObject("shop", shop);
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editShop(@ModelAttribute @Valid Shop shop,
            BindingResult result,
            @PathVariable Integer id,
            final RedirectAttributes redirectAttributes) throws ShopNotFound {

        if (result.hasErrors()) {
            return new ModelAndView("shop-edit");
        }

        ModelAndView mav = new ModelAndView("redirect:/index.html");
        String message = "Shop was successfully updated.";

        shopService.update(shop);

        redirectAttributes.addFlashAttribute("message", message);
        return mav;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteShop(@PathVariable Integer id,
            final RedirectAttributes redirectAttributes) throws ShopNotFound {

        ModelAndView mav = new ModelAndView("redirect:/index.html");

        Shop shop = shopService.delete(id);
        String message = "The shop " + shop.getName() + " was successfully deleted.";

        redirectAttributes.addFlashAttribute("message", message);
        return mav;
    }

}
