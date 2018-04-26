package com.journaldev.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.journaldev.spring.model.Person;
import com.journaldev.spring.service.PersonService;

@Controller
public class PersonController {
	
	private PersonService personService;
	
	@Autowired(required=true)
	@Qualifier(value="personService")
	public void setPersonService(PersonService ps){
		this.personService = ps;
	}
	
	/***
	 *  @RequestMapping("/persons"): in browser if we call to persons then automatically this method will invoke'
	 * return: string- its redirecting to persons page.
	 * call to person service class to get the list of persons.
	 */
	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public String listPersons(Model model) {
		model.addAttribute("person", new Person());
		model.addAttribute("listPersons", this.personService.listPersons());
		return "person";
	}
	
	/***
	 *  @RequestMapping("/person/add"): when we click on remove button, it will call to /person/add'
	 * @ModelAttribute("person"): take the person object from jsp
	 * return: string- its redirecting to persons page.
	 * get person object from jsp and call to person service class[addPerson or UpdatePerson methods].
	 */
	@RequestMapping(value= "/person/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") Person p){
		
		if(p.getId() == 0){
			//new person, add it
			this.personService.addPerson(p);
		}else{
			//existing person, call update
			this.personService.updatePerson(p);
		}
		
		return "redirect:/persons";
		
	}
	
	/***
	 *  @RequestMapping("/remove/{id}"): when we click on remove button, it will call to /remove/{id}, jsp page: /remove/${person.id}'
	 * @PathVariable("id"): take the variable from jsp
	 * id: input varible
	 * return: string- its redirecting to persons page.
	 * get id and call to person service class.
	 */
	@RequestMapping("/remove/{id}")
    public String removePerson(@PathVariable("id") int id){
		
        this.personService.removePerson(id);
        return "redirect:/persons";
    }
 
	/***
	 *  @RequestMapping("/edit/{id}"): when we click on edit button, it will call to /edit/{id}, jsp page: /edit/${person.id}'
	 * @PathVariable("id"): take the variable from jsp
	 * model: set the value to Model object
	 * return: string- its redirecting to person page.
	 * after set the values to Model then we are calling to person service class.
	 */
    @RequestMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", this.personService.getPersonById(id));
        model.addAttribute("listPersons", this.personService.listPersons());
        return "person";
    }
	
}
