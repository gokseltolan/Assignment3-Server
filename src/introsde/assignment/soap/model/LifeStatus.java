package introsde.assignment.soap.model;

import introsde.assignment.soap.dao.LifeCoachDao;
import introsde.assignment.soap.endpoint.PeoplePublisher;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.*;
import javax.persistence.OneToOne;

/**
 * The persistent class for the "LifeStatus" database table.
 * 
 */
@Entity
@Table(name = "LifeStatus")
@NamedQuery(name = "LifeStatus.findAll", query = "SELECT l FROM LifeStatus l")

@XmlType(propOrder = {"idMeasure", "measureName", "measureType", "time", "value"})
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)
public class LifeStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(generator="sqlite_lifestatus")
	@TableGenerator(name="sqlite_lifestatus", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="LifeStatus")
	
	@Column(name = "idMeasure")
	private int idMeasure;

	@Column(name = "value")
	private String value;
	
	@Column(name = "time")
	private String time;
	
	@Column(name = "measureType")
	private String measureType;
	
	@Column(name = "measureName")
	private String measureName;

	@XmlTransient
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;

	public LifeStatus() {
	}

	public int getIdMeasure() {
		return this.idMeasure;
	}

	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getMeasureName() {
		return this.measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}
	
	public String getMeasureType() {
		return this.measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}
	

	// we make this transient for JAXB to avoid and infinite loop on serialization
	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	public static LifeStatus getLifeStatusById(int lifestatusId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		LifeStatus p = em.find(LifeStatus.class, lifestatusId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<LifeStatus> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<LifeStatus> list = em.createNamedQuery("LifeStatus.findAll", LifeStatus.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static LifeStatus saveLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static LifeStatus updateLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
