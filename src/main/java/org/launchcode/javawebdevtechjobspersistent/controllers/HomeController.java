package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.Job;
import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.JobRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository; //part 1 of updating HomeController

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());//part 2 of updating HomeController
        model.addAttribute("skills", skillRepository.findAll());//part 2 of updating HomeController
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam Integer employerId, @RequestParam List<Integer> skills) {
        if (errors.hasErrors()) {
            model.addAttribute("employers", employerRepository.findById(employerId));//part 4 of updating HomeController
            model.addAttribute("skills", skillRepository.findAllById(skills));//part 4 of updating HomeController
            model.addAttribute("title", "Add Job");
            return "add";
        }
        Employer newEmployer = (Employer) employerRepository.findById(employerId).get();
        newJob.setEmployer(newEmployer);

        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);
        jobRepository.save(newJob);
        return "redirect:";
    }

    @GetMapping("view/{jobId}")//
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> job1 = jobRepository.findById(jobId);
        Job job = (Job) job1.get();
        model.addAttribute("job", job);
        return "view";
    }


}
