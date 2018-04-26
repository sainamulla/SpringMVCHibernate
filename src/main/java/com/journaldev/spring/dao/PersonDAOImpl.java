package com.journaldev.spring.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.journaldev.spring.model.Person;

@Repository
public class PersonDAOImpl implements PersonDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonDAOImpl.class);
	static List<Person> personsList = new ArrayList<Person>();
	//private SessionFactory sessionFactory;
	
	/*public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}*/
	@PostConstruct
	public void init() {
		personsList.clear();
		for(int i=0; i<=10; i++) {
			Person p= new Person();
			p.setCountry("India");
			p.setId(i);
			p.setName("Nandhu"+i);
			personsList.add(p);
		}
	}

	@Override
	public void addPerson(Person p) {
		//Session session = this.sessionFactory.getCurrentSession();
		//session.persist(p);
		p.setId(Integer.parseInt(UUID.randomUUID().toString()));
		personsList.add(p);
		logger.info("Person saved successfully, Person Details="+p);
	}

	@Override 
	public void updatePerson(Person p) {
		//Session session = this.sessionFactory.getCurrentSession();
		//session.update(p);
		//Person person=new Person();
		for(int i=0; i<=personsList.size(); i++) {
			if(personsList.get(i).getId() == p.getId()) {
				personsList.get(i).setCountry(p.getCountry());
				personsList.get(i).setId(p.getId());
				personsList.get(i).setName(p.getName());
				break;
			}
		}
		logger.info("Person updated successfully, Person Details="+p);
	}

	@Override
	public List<Person> listPersons() {
		//Session session = this.sessionFactory.getCurrentSession();
		//List<Person> personsList=session.createQuery("from Person").list();
		
		
		return personsList;
	}

	@Override
	public Person getPersonById(int id) {
		//Session session = this.sessionFactory.getCurrentSession();		
		//Person p = (Person) session.load(Person.class, new Integer(id));
		Person p=new Person();
		for(Person person: personsList) {
			if(person.getId()==id) {
				p=person;
				break;
			}
		}
		logger.info("Person loaded successfully, Person details="+p);
		return p;
	}

	@Override
	public void removePerson(int id) {
		for(Person person: personsList) {
			if(person.getId()==id) {
				personsList.remove(person);
				break;
			}
		}
		logger.info("Person deleted successfully, person details=");
	}

}
