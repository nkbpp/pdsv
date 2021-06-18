package ru.pfr.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.Application;
import ru.pfr.model.pdsvvrr.Logi;
import ru.pfr.model.pdsvvrr.User;
import ru.pfr.service.pdsvvrr.LogiService;

import java.util.Date;

@Controller
@RequestMapping(value = {"/", "/pdsv"})
public class MainController {
    @Autowired
    LogiService logiService;

    private static final Logger logger = LogManager.getLogger(Application.class);

    @RequestMapping
    public String mains(
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(),user.getLogin(),"Авторизация прошла успешно MainController mains()"));
        logger.info("User = " + user.getLogin() + " Авторизация прошла успешно MainController mains()");
        if (user.getRayon().getKod().equals("000"))
            return "redirect:/pdsv/opfr";
        else if (user.getRayon().getKod().equals("999"))
            return "redirect:/pdsv/admin";
        return "/pdsv";
    }
}
