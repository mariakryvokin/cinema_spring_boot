package app.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(Throwable.class)
    public RedirectView handle(Throwable t, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error",t.getMessage()==null?t.getClass().getName():t.getMessage());
        return  new RedirectView("/app/error/");
    }
}
