package introsde.assignment.soap.ws;

import introsde.assignment.soap.model.HealthMeasureHistory;
import introsde.assignment.soap.model.LifeStatus;
import introsde.assignment.soap.model.Person;
import introsde.assignment.soap.model.MeasureTypes;

import java.util.List;

import javax.jws.WebService;

//Service Implementation

@WebService(endpointInterface = "introsde.assignment.soap.ws.People",
	serviceName="PeopleService")
public class PeopleImpl implements People {

	@Override
	public Person readPerson(int id) {
		System.out.println("---> Reading Person by id = "+id);
		Person p = Person.getPersonById(id);
		if (p!=null) {
			System.out.println("---> Found Person by id = "+id+" => "+p.getName());
		} else {
			System.out.println("---> Didn't find any Person with  id = "+id);
		}
		return p;
	}

	@Override
	public List<Person> getPeople() {
		return Person.getAll();
	}
	
	@Override
	public List<HealthMeasureHistory> getHistory() {
		return HealthMeasureHistory.getAll();
	}
	
	@Override
	public List<HealthMeasureHistory> readPersonHistory(int id, String measureType) {
		return HealthMeasureHistory.getAllHistory(id, measureType);
	}
	
	@Override
	public HealthMeasureHistory  readPersonMeasure(int id, String measureType, int mid) {
		return HealthMeasureHistory.getHistoryById(id, measureType, mid);
	}
	
	@Override
	public HealthMeasureHistory saveHistory(int id, HealthMeasureHistory measure) {
		Person per = new Person();
		per = Person.getPersonById(id);
		List<LifeStatus> ls = per.getMeasure();
		HealthMeasureHistory hmh2 = new HealthMeasureHistory();
		LifeStatus life = new LifeStatus();
		for(int i = 0; i<ls.size(); i++){
			if (ls.get(i).getMeasureName().equals( measure.getMeasureName()))
			{
				life = LifeStatus.getLifeStatusById(ls.get(i).getIdMeasure());
				hmh2.setPerson(life.getPerson());
				hmh2.setMeasureName(life.getMeasureName());
				hmh2.setMeasureType(life.getMeasureType());
				hmh2.setTimestamp(life.getTime());
				hmh2.setValue(life.getValue());
				LifeStatus.removeLifeStatus(life);
				LifeStatus life2 = new LifeStatus();
				life2.setPerson(per);
				life2.setMeasureName(measure.getMeasureName());
				life2.setMeasureType(measure.getMeasureType());
				life2.setTime(measure.getTimestamp());
				life2.setValue(measure.getValue());
				LifeStatus.saveLifeStatus(life2);
			}
				}
		return HealthMeasureHistory.saveHealthMeasureHistory(hmh2);
		
		}	
	
	
	@Override
	public List<MeasureTypes> getMeasureType() {
		return MeasureTypes.getAll();
	}
	

	@Override
	public Person addPerson(Person person) {	
		Person.savePerson(person);
		return person;
         }
         
	@Override
	public Person updatePerson(Person person) {
		Person existing = Person.getPersonById(person.getIdPerson());
		
		if (person.getName() != null)
			existing.setName(person.getName());
		if (person.getLastname() != null)
			existing.setLastname(person.getLastname());
		if (person.getBirthdate() != null)
			existing.setBirthdate(person.getBirthdate());	
		if (person.getEmail() != null)
			existing.setEmail(person.getEmail());;			
		if (person.getUsername() != null)
			existing.setUsername(person.getUsername());		
		Person.updatePerson(existing);
		return existing;
	}

	@Override
	public int deletePerson(int id) {
		Person p = Person.getPersonById(id);
		if (p!=null) {
			Person.removePerson(p);
			return 0;
		} else {
			return -1;
		}
	}
	
	@Override
	public int deleteLifeStatus(int id) {
		LifeStatus lf = LifeStatus.getLifeStatusById(id);
		if (lf!=null) {
			LifeStatus.removeLifeStatus(lf);
			return 0;
		} else {
			return -1;
		}
	}

	@Override
	public int createLifeStatus(int personid, LifeStatus hp) {
			hp.setPerson(Person.getPersonById(personid));
			LifeStatus.saveLifeStatus(hp);
			return hp.getIdMeasure();		
			}
	
	
	@Override
	public int updatePersonHP(int id, LifeStatus hp) {
		LifeStatus ls = LifeStatus.getLifeStatusById(hp.getIdMeasure());
		if (ls.getPerson().getIdPerson() == id) {
			LifeStatus newhp = new LifeStatus();
			newhp.setPerson(Person.getPersonById(id));
			newhp.setMeasureName(hp.getMeasureName());
			newhp.setIdMeasure(hp.getIdMeasure());
			newhp.setMeasureType(hp.getMeasureType());
			newhp.setTime(hp.getTime());
			newhp.setValue(hp.getValue());			
			LifeStatus.updateLifeStatus(newhp);
			return newhp.getIdMeasure();
		} else {
			return -1;
		}
	}

}
