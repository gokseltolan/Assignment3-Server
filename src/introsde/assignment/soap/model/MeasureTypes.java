package introsde.assignment.soap.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import introsde.assignment.soap.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="MeasureTypes")
@NamedQuery(name="MeasureTypes.findAll", query="SELECT m FROM MeasureTypes m")

@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)

//@XmlRootElement
public class MeasureTypes implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	// For sqlite in particular, you need to use the following @GeneratedValue annotation
	// This holds also for the other tables
	// SQLITE implements auto increment ids through named sequences that are stored in a 
	// special table named "sqlite_sequence"
	@GeneratedValue(generator="sqlite_measuretypes")
	@TableGenerator(name="sqlite_measuretypes", table="sqlite_sequence",
    pkColumnName="name", valueColumnName="seq",
    pkColumnValue="MeasureTypes")
	
	@Column(name="measureTypes")
	private String measureTypes;

    public MeasureTypes() {
    }

    public String getMeasureType() {
        return measureTypes;
    }

    public void setMeasureType(String measureType) {
        this.measureTypes = measureType;
    }
    
    public static List<MeasureTypes> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<MeasureTypes> list = em.createNamedQuery("MeasureTypes.findAll", MeasureTypes.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
    
}