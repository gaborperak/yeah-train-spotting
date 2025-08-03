package com.yeah.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TrainSchedulerViewController {

    @GetMapping("/uploadNames")
    public String showUploadForm(Model model) {
        model.addAttribute("weeks", java.util.stream.IntStream.rangeClosed(1, 52).toArray());
        return "uploadNames";
    }
}