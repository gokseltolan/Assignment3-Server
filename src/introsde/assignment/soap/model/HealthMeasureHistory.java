package introsde.assignment.soap.model;

import introsde.assignment.soap.dao.LifeCoachDao;
import introsde.assignment.soap.model.Person;
import introsde.assignment.soap.model.HealthMeasureHistory;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.*;


/**
 * The persistent class for the "HealthMeasureHistory" database table.
 * 
 */
@Entity
@Table(name="HealthMeasureHistory")

@NamedQueries({
	
	@NamedQuery(name = "HealthMeasureHistory.findAll", query = "SELECT h FROM HealthMeasureHistory h"),
	@NamedQuery(name = "HealthMeasureHistory.getHealthMeasureHistoryByIdAndType", query = "SELECT h FROM HealthMeasureHistory h WHERE h.person.idPerson = :idP AND h.measureName = :measureT"),
	@NamedQuery(name = "HealthMeasureHistory.getByMid", query = "SELECT h FROM HealthMeasureHistory h WHERE h.person.idPerson = :idP AND h.measureName = :measureT AND h.idMeasureHistory = :idM") 
})

//@XmlRootElement
@XmlType(propOrder = {"idMeasureHistory", "timestamp", "measureName", "value", "measureType" })
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)
public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_healthmeasurehistory")
	@TableGenerator(name="sqlite_healthmeasurehistory", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="HealthMeasureHistory")
	
	@Column(name="idMeasureHistory")
	private int idMeasureHistory;

	@Column(name="measureName")
	private String measureName;

	@Column(name="measureType")
	private String measureType;
	
	//@Temporal(TemporalType.DATE)
	@Column(name="timestamp")
	private String timestamp;

	@Column(name="value")
	private String value;
		
	// notice that we haven't included a reference to the history in Person
	// this means that we don't have to make this attribute XmlTransient
	@XmlTransient
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;
	
	public HealthMeasureHistory() {
	}

	public int getIdMeasureHistory() {
		return this.idMeasureHistory;
	}

	public void setIdMeasureHistory(int idMeasureHistory) {
		this.idMeasureHistory = idMeasureHistory;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
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

	@XmlTransient
	public Person getPerson() {
	    return person;
	}

	public void setPerson(Person param) {
	    this.person = param;
	}

	// database operations
	public static HealthMeasureHistory getHealthMeasureHistoryById(int id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthMeasureHistory p = em.find(HealthMeasureHistory.class, id);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<HealthMeasureHistory> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<HealthMeasureHistory> list = em.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static List<HealthMeasureHistory> getAllHistory(int id, String measure) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<HealthMeasureHistory> list = em
				.createNamedQuery("HealthMeasureHistory.getHealthMeasureHistoryByIdAndType", HealthMeasureHistory.class)
				.setParameter("idP", id)
				.setParameter("measureT", measure)
				.getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}
	
	
	public static HealthMeasureHistory getHistoryById(int id, String measure, int idM) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthMeasureHistory p = em.createNamedQuery("HealthMeasureHistory.getByMid", HealthMeasureHistory.class)
				.setParameter("idP", id)
				.setParameter("measureT", measure)
				.setParameter("idM", idM)
				.getSingleResult();
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
