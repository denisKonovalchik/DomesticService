package by.konovalchik.domesticservice.controller;

import by.konovalchik.domesticservice.dto.telephoneDTO.NumberTelDTO;
import by.konovalchik.domesticservice.dto.userDTO.*;
import by.konovalchik.domesticservice.entity.Role;
import by.konovalchik.domesticservice.entity.Telephone;
import by.konovalchik.domesticservice.service.TelephoneService;
import by.konovalchik.domesticservice.service.UserService;
import by.konovalchik.domesticservice.entity.User;
import by.konovalchik.domesticservice.utils.ControllerMessageManager;
import by.konovalchik.domesticservice.utils.ConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Arrays;

@Controller
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public ModelAndView registration(ModelAndView modelAndView){
        modelAndView.setViewName("registration");
        modelAndView.addObject("roles", Arrays.asList(Role.values()));
        modelAndView.addObject("newUser", new AllArgUserDTO());
        return modelAndView;
    }



    @PostMapping("/registration")
    public ModelAndView registration(@Valid @ModelAttribute("newUser") AllArgUserDTO userDTO,
                                     BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.setViewName("registration");
        if (!bindingResult.hasErrors()) {
            User user = ConverterDTO.getAllArgUsersDTO(userDTO);
            if (userService.save(user)) {
                modelAndView.addObject("messageReg1", ControllerMessageManager.REG_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageReg2", ControllerMessageManager.REG_FAIL);
            }
        }
        return modelAndView;
    }



    @GetMapping("/authorization")
    public ModelAndView authorization(ModelAndView modelAndView){
        modelAndView.setViewName("authorization");
        return modelAndView;
    }






}
