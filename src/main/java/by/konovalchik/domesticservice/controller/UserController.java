package by.konovalchik.domesticservice.controller;

import by.konovalchik.domesticservice.dto.cardDTO.UserInfoDTO;
import by.konovalchik.domesticservice.dto.telephoneDTO.NumberTelDTO;
import by.konovalchik.domesticservice.dto.userDTO.*;
import by.konovalchik.domesticservice.entity.*;
;
import by.konovalchik.domesticservice.service.UserService;
import by.konovalchik.domesticservice.utils.ControllerMessageManager;
import by.konovalchik.domesticservice.utils.ConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.Optional;


@Controller
@RequestMapping("/userInfo")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/userProfile")
    public ModelAndView geCurrentUser(ModelAndView modelAndView) {
        modelAndView.setViewName("userProfile");
        User user = userService.getCurrentUser();
        Optional<User> userInfoOpt = userService.getUserById(user.getId());
        if(userInfoOpt.isPresent()){
            UserInfoDTO userInfoDTO = ConverterDTO.getUserInfoCard(userInfoOpt.get());
            modelAndView.addObject("userProfileDTO", userInfoDTO);
        }else{
            modelAndView.addObject("messageUserProfile", ControllerMessageManager.USER_NOT_FOUND);
        }
        return modelAndView;
    }



    @GetMapping("/getUserById/{id}")
    public ModelAndView getUserById(@PathVariable @Min(value=1) long id, ModelAndView modelAndView) {
        modelAndView.setViewName("userInfo");
        Optional<User> userInfoOpt = userService.getUserById(id);
        if(userInfoOpt.isPresent()){
            UserInfoDTO userInfoDTO = ConverterDTO.getUserInfoCard(userInfoOpt.get());
            modelAndView.addObject("userInfoDTO", userInfoDTO);
        }
        return modelAndView;
    }



    @GetMapping("/updPassword")
    public ModelAndView updatePassword(ModelAndView modelAndView) {
        modelAndView.setViewName("updatePassword");
        modelAndView.addObject("userPasswordDTO", new PasswordUserDTO());
        return modelAndView;
    }



    @PostMapping("/updPassword")
    public ModelAndView updatePassword(@Valid @ModelAttribute("userPasswordDTO") PasswordUserDTO passwordUserDTO,
                                        BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updatePassword");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }else{
            User user = userService.getCurrentUser();
            if (userService.updateUserPassword(passwordUserDTO.getOldPassword(), passwordUserDTO.getConfirmPassword(), passwordUserDTO.getNewPassword(), user)) {
                modelAndView.addObject("messagePassword1", ControllerMessageManager.UPDATE_PASSWORD_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messagePassword2", ControllerMessageManager.UPDATE_USERNAME_FAIL);
            }
        }
        return modelAndView;
    }



    @GetMapping("/updFirstName")
    public ModelAndView updateFirstName(ModelAndView modelAndView) {
        modelAndView.setViewName("updateFirstName");
        modelAndView.addObject("userFirstNameDTO", new FirstNameUserDTO());
        return modelAndView;
    }



    @PostMapping("/updFirstName")
    public ModelAndView updateFirstName(@Valid @ModelAttribute("userFirstNameDTO") FirstNameUserDTO firstNameDTO,
                                        BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updateFirstName");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }else{
            User user = userService.getCurrentUser();
            if (userService.updateUserFirstName(user, firstNameDTO.getFirstName())) {
                modelAndView.addObject("messageFirstName1", ControllerMessageManager.UPDATE_NAME_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageFirstName2", ControllerMessageManager.UPDATE_NAME_FAIL);
            }
        }
        return modelAndView;
    }



    @GetMapping("/updLastName")
    public ModelAndView updateLastName(ModelAndView modelAndView) {
        modelAndView.setViewName("updateLastName");
        modelAndView.addObject("userLastNameDTO", new LastNameUserDTO());
        return modelAndView;
    }



    @PostMapping("/updLastName")
    public ModelAndView updateLastName(@Valid @ModelAttribute("userLastNameDTO") LastNameUserDTO lastNameDTO,
                                       BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updateLastName");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }else{
            User user = userService.getCurrentUser();
            if (userService.updateUserLastName(user, lastNameDTO.getLastName())) {
                modelAndView.addObject("messageLastName1", ControllerMessageManager.UPDATE_LAST_NAME_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageLastName2", ControllerMessageManager.UPDATE_LAST_NAME_FAIL);
            }
        }
        return modelAndView;
    }



    @GetMapping("/updPicture")
    public ModelAndView updatePicture(ModelAndView modelAndView) {
        modelAndView.setViewName("updatePicture");
        modelAndView.addObject("userPictureDTO", new PictureUserDTO());
        return modelAndView;
    }



    @PostMapping("/updPicture")
    public ModelAndView updatePicture(@Valid @ModelAttribute("userPictureDTO") PictureUserDTO pictureDTO,
                                      BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updatePicture");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }else{
            User user = userService.getCurrentUser();
            if (userService.updateUserPicture(user, pictureDTO.getPicture())) {
                modelAndView.addObject("messagePicture1", ControllerMessageManager.UPDATE_PICTURE_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messagePicture2", ControllerMessageManager.UPDATE_PICTURE_FAIL);
            }
        }
        return modelAndView;
    }



    @GetMapping("/updTelephone")
    public ModelAndView updateTelephone(ModelAndView modelAndView) {
        modelAndView.setViewName("updateTelephone");
        modelAndView.addObject("userTelephoneDTO", new NumberTelDTO());
        return modelAndView;
    }



    @PostMapping("/updTelephone")
    public ModelAndView updatePicture(@Valid @ModelAttribute("userTelephoneDTO") NumberTelDTO numberTelDTO,
                                      BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updateTelephone");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }else{
            User user = userService.getCurrentUser();
            if (userService.updateUserTelephone(user, numberTelDTO.getTelephone())) {
                modelAndView.addObject("messageTelephone1", ControllerMessageManager.UPDATE_NUMBER_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageTelephone2", ControllerMessageManager.UPDATE_NUMBER_FAIL);
            }
        }
        return modelAndView;
    }



    @GetMapping("/updEmail")
    public ModelAndView updateEmail(ModelAndView modelAndView) {
        modelAndView.setViewName("updateEmail");
        modelAndView.addObject("userEmailDTO", new EmailUserDTO());
        return modelAndView;
    }



    @PostMapping("/updEmail")
    public ModelAndView updateEmail(@Valid @ModelAttribute("userEmailDTO") EmailUserDTO emailDTO,
                                      BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updateEmail");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }else{
            User user = userService.getCurrentUser();
            if (userService.updateUserEmail(user, emailDTO.getEmail())) {
                modelAndView.addObject("messageEmail1", ControllerMessageManager.UPDATE_EMAIL_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageEmail2", ControllerMessageManager.UPDATE_EMAIL_FAIL);
            }
        }
        return modelAndView;
    }


}
