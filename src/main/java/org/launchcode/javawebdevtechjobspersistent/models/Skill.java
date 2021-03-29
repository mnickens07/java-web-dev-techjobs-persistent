package org.launchcode.javawebdevtechjobspersistent.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {

    @ManyToMany(mappedBy="skills")
    private List<Job> jobs=new ArrayList<>();

    @NotBlank(message="A skill description is required.")
    @Size(max=150)
    private String description;

    public Skill(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    } // org.thymeleaf.exceptions.TemplateInputException: An error happened during template parsing (template: "class path resource [templates/skills/view.html]")
    // I received this error because I did not have a getter/setter for "jobs" in the skill class and it wasnt connecting to my skills/view template.
}