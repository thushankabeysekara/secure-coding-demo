package com.nfinance.security.securecodingdemo.api;

import com.nfinance.security.securecodingdemo.entity.User;
import com.nfinance.security.securecodingdemo.model.WebLoginRequestModel;
import com.nfinance.security.securecodingdemo.service.UserService;
import com.nfinance.security.securecodingdemo.validator.InputValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class WebLoginController {

    private final UserService userService;
    private final InputValidator inputValidator;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("loginRequestModel", WebLoginRequestModel.builder().build());
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/user_info")
    public String userInfo(@RequestParam String userId, Model model) {
        inputValidator.isValidUserId(userId);
        Optional<User> user = userService.getUserInfo(userId);
        if(user.isPresent()) {
            log.info("Returning user information for user {}", user);
            model.addAttribute("user", user.get());
        } else {
            log.error("Couldn't find user with id: {}", userId);
        }
        return "user_info";
    }
}
