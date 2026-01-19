package com.yeah.viewcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.yeah.repositories.Top30PlayerRepository;

@Controller
public class TrainSchedulerViewController {

    @Autowired
    private Top30PlayerRepository top30PlayerRepository;

    @GetMapping("/uploadNames")
    public String showUploadForm(Model model) {
        // Always allow weeks 1 to 104 for uploads (2 years)
        model.addAttribute("weeks", java.util.stream.IntStream.rangeClosed(1, 104).toArray());
        return "uploadNames";
    }
}