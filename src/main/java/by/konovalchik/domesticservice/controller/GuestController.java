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





    @GetMapping("/updFirstName")
    public ModelAndView updateFirstName(ModelAndView modelAndView) {
        modelAndView.setViewName("updFirstName");
        modelAndView.addObject("userNameDTO", new FirstNameUserDTO());
        return modelAndView;
    }


    @PostMapping("/updFirstName")
    public ModelAndView updateFirstName(@Valid @ModelAttribute("userNameDTO") FirstNameUserDTO firstNameDTO,
                                        BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updFirstName");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }else{
            User user = userService.getCurrentUser();
            if (userService.updateUserFirstName(user, firstNameDTO.getFirstName())) {
                user.setFirstName(firstNameDTO.getFirstName());
                modelAndView.addObject("messageFirstName", ControllerMessageManager.UPDATE_NAME_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageFirstName", ControllerMessageManager.UPDATE_NAME_FAIL);
            }
        }
        return modelAndView;
    }





    @GetMapping("/updLastName")
    public ModelAndView updateLastName(ModelAndView modelAndView) {
        modelAndView.setViewName("updLastName");
        modelAndView.addObject("userLastNameDTO", new LastNameUserDTO());
        return modelAndView;
    }


    @PostMapping("/updLastName")
    public ModelAndView updateLastName(@Valid @ModelAttribute("userLastNameDTO") LastNameUserDTO lastNameDTO,
                                       BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updLastName");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }else{
            User user = userService.getCurrentUser();
            if (userService.updateUserLastName(user, lastNameDTO.getLastName())) {
                user.setLastName(lastNameDTO.getLastName());
                modelAndView.addObject("messageLastName", ControllerMessageManager.UPDATE_LAST_NAME_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageLastName", ControllerMessageManager.UPDATE_LAST_NAME_FAIL);
            }
        }
        return modelAndView;
    }


    @GetMapping("/updPicture")
    public ModelAndView updatePicture(ModelAndView modelAndView) {
        modelAndView.setViewName("updPicture");
        modelAndView.addObject("userPictureDTO", new EmailUserDTO());
        return modelAndView;
    }


    @PostMapping("/updPicture")
    public ModelAndView updatePicture(@Valid @ModelAttribute("userPictureDTO") PictureUserDTO pictureDTO,
                                      BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updPicture");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }else{
            User user = userService.getCurrentUser();
            if (userService.updateUserPicture(user, pictureDTO.getPicture())) {
                user.setPicture(pictureDTO.getPicture());
                modelAndView.addObject("messagePicture", ControllerMessageManager.UPDATE_PICTURE_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messagePicture", ControllerMessageManager.UPDATE_PICTURE_FAIL);
            }
        }
        return modelAndView;
    }



    @GetMapping("/updTelephone")
    public ModelAndView updateTelephone(ModelAndView modelAndView) {
        modelAndView.setViewName("updTelephone");
        modelAndView.addObject("userTelephoneDTO", new NumberTelDTO());
        return modelAndView;
    }


    @PostMapping("/updTelephone")
    public ModelAndView updatePicture(@Valid @ModelAttribute("userPictureDTO") NumberTelDTO numberTelDTO,
                                      BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updTelephone");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }else{
            User user = userService.getCurrentUser();
            if (userService.updateUserTelephone(user, numberTelDTO.getTelephone())) {
                user.getTelephone().setNumber(numberTelDTO.getTelephone());
                modelAndView.addObject("messageTelephone", ControllerMessageManager.UPDATE_NUMBER_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageTelephone", ControllerMessageManager.UPDATE_NUMBER_FAIL);
            }
        }
        return modelAndView;
    }



}
