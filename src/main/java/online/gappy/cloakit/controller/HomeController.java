package online.gappy.cloakit.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/menu")
    public String menu(@AuthenticationPrincipal OidcUser user, Model model) {
        if (user != null) {
            model.addAttribute("username", user.getPreferredUsername());
        } else {
            return "redirect:/";
        }
        return "menu";
    }
}
