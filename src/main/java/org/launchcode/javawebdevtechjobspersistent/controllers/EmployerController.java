package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("employers") //this route sets the location on the local host for this form to be 'localhost:8080/employers'
// THIS ISSUE WAS FIXED BY CHANGING THE GetMapping ROUTING ON displayAddEmployerForm method FROM 'INDEX' TO 'ADD'
public class EmployerController {

    //Something is not properly routed and therefore I am unable
    // to see anything show up from my form on the webpage.
    @Autowired
    private EmployerRepository employerRepository; //gives access to employerRepository so that I can save employers in database.

    @GetMapping("add")//this route and method displays the 'add' employer form. It can be found at localhost:8080/employers/add
    public String displayAddEmployerForm(Model model) {
        model.addAttribute("title", "Add Employer"); //this action? binds the attribute name 'title' with 'Add Employers' on the form
        model.addAttribute(new Employer()); // this action creates a new instance of the Employer class on the form.
        model.addAttribute("employers", employerRepository.findAll());//this action will bind the attribute name 'employers with all employers and print them on the form
        return "employers/add"; //this return statement will place the user at the localhost:8080/employers/add
    }

    @GetMapping//index method that responds at /employers with a list of all employers. I guess we call this an index method since it returns employers/index
    public String displayAllEmployers(Model model){
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/index";

    }

    @PostMapping("add")//this route and method process the information collected from the 'add' employer form. It can be found at localhost:8080/employers/add
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Employer");
            return "employers/add";
        }
        employerRepository.save(newEmployer); //save a new employer(object) in the employerRepository
        return "redirect:/employers";//redirects user to a list of all of the saved employers in the employerRepository/database
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        Optional<Employer> optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            model.addAttribute("location", employer.getLocation());
            model.addAttribute("name", employer.getName());
            return "employers/view";
        } else {
            return "redirect:../";
        }
    }
}
