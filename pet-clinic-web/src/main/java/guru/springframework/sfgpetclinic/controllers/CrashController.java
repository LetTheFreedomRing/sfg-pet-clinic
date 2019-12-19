package guru.springframework.sfgpetclinic.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CrashController implements ErrorController {

    @GetMapping("/oups")
    public String triggerException() {
        return "errors/error";
    }

    @Override
    public String getErrorPath() {
        return "errors/error";
    }
}
