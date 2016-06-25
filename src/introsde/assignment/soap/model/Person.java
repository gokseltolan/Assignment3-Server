package introsde.assignment.soap.model;

import introsde.assignment.soap.dao.LifeCoachDao;
import introsde.assignment.soap.model.LifeStatus;
import introsde.assignment.soap.model.Person;
import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.List;


/**
 * The persistent class for the "Person" database table.
 * 
 */
@Entity
@Table(name="Person")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")


@XmlType(propOrder = { "idPerson", "name", "lastname", "birthdate", "email", "username", "measure"})
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)

//@XmlRootElement
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	
	
	@Id
	// For sqlite in particular, you need to use the following @GeneratedValue annotation
	// This holds also for the other tables
	// SQLITE implements auto increment ids through named sequences that are stored in a 
	// special table named "sqlite_sequence"
	@GeneratedValue(generator="sqlite_person")
	@TableGenerator(name="sqlite_person", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="Person")
		
	@Column(name="idPerson")
	private int idPerson;

	@Column(name="lastname")
	private String lastname;

	@Column(name="name")
	private String name;

	@Column(name="username")
	private String username;
	
	//@Temporal(TemporalType.DATE)
	@Column(name="birthdate")
	private String birthdate;
	
	@Column(name="email")
	private String email;

	// mappedBy must be equal to the name of the attribute in LifeStatus that maps this relation
	@XmlElementWrapper(name="currentHealth")
	@OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<LifeStatus> measure;
		
	@XmlTransient
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<HealthMeasureHistory> measures;
	
	public List<HealthMeasureHistory> getMeasures() {
        return measures;
    }
		
	public Person() {
	}
	
	public String getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdPerson() {
		return this.idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// the XmlElementWrapper defines the name of node in which the list of LifeStatus elements
	// will be inserted
	//@XmlElementWrapper(name = "Measure")
	public List<LifeStatus> getMeasure() {
	    return measure;
	}

	public void setMeasure(List<LifeStatus> measure) {
	    this.measure = measure;
	}
	
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	public static Person getPersonById(int personId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Person p = em.find(Person.class, personId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<Person> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Person savePerson(Person p) {
		Person.addHealthProfile(p);
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Person addHealthProfile(Person p){
		List<LifeStatus> lifeStatusList = p.getMeasure();
        if(lifeStatusList != null){
           for (int i = 0; i<lifeStatusList.size(); i++) {
               LifeStatus lifeStatus = lifeStatusList.get(i);
               lifeStatus.setPerson(p);
           }
       }
        return p;
	}
	
	public static Person updatePerson(Person p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return p;	
	}
		
	public static void removePerson(Person p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
