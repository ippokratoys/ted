package application.basicControllers;

import application.database.Apartment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by thanasis on 23/8/2017.
 */
@Controller
public class HostController {
    @RequestMapping(value = "/add_apartment",method = RequestMethod.POST)
    String addApartmentController(Model model,
                                  @ModelAttribute Apartment formApartment
    ){
        formApartment.toString();
        System.out.println(formApartment.getName());
        return "redirect:/";
    }
    @RequestMapping(value = "/add_apartment",method = RequestMethod.GET)
    String addApartmentController(Model model
    ){
        System.out.println("att added");
        Apartment apartment = new Apartment();
        apartment.setName("mon kolonaki");
        model.addAttribute("apartment",apartment);
        return "/add_apartment";
    }
}