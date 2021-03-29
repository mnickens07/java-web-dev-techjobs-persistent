package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute("title", "Add Skill");
        model.addAttribute(new Skill());
        model.addAttribute("skills", skillRepository.findAll());
        return "skills/add";
    }

    @GetMapping
    public String displayAllSkills(Model model){
        model.addAttribute("skills", skillRepository.findAll());
        return "skills/index";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                         Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Skill");
            return "skills/add";
        }
        skillRepository.save(newSkill);
        return "redirect:/skills";
    }

    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) {

        Optional<Skill> optSkill = skillRepository.findById(skillId);
        if (optSkill.isPresent()) {
            Skill skill = (Skill) optSkill.get();
            model.addAttribute("skill", skill);
            model.addAttribute("description", skill.getDescription());
            model.addAttribute("name", skill.getName());
            return "skills/view";
        } else {
            return "redirect:../";
        }
    }
}
