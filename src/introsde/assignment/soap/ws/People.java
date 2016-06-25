package introsde.assignment.soap.ws;
import introsde.assignment.soap.model.HealthMeasureHistory;
import introsde.assignment.soap.model.LifeStatus;
import introsde.assignment.soap.model.Person;
import introsde.assignment.soap.model.MeasureTypes;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional

public interface People {
    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") int id);
    
    @WebMethod(operationName="getMeasureList")
    @WebResult(name="measureTypes") 
    public List<MeasureTypes> getMeasureType();
 
    @WebMethod(operationName="getPeopleList")
    @WebResult(name="people") 
    public List<Person> getPeople();
    
    @WebMethod(operationName="getHistoryList")
    @WebResult(name="healthMeasureHistory") 
    public List<HealthMeasureHistory> getHistory();
    
    @WebMethod(operationName="savePersonMeasure")
    @WebResult(name="healthMeasure") 
    public HealthMeasureHistory saveHistory(@WebParam(name="personId") int id, @WebParam(name="measure") HealthMeasureHistory measure);
 
    @WebMethod(operationName="readPersonHistory")
    @WebResult(name="healthMeasure") 
    public List<HealthMeasureHistory> readPersonHistory(@WebParam(name="personId") int id, @WebParam(name="measureType") String measureType);
    
    @WebMethod(operationName="readPersonMeasure")
    @WebResult(name="healthMeasure") 
    public HealthMeasureHistory readPersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measureType") String measureType, @WebParam(name="measureId") int mid);
       
    @WebMethod(operationName="createPerson")
    @WebResult(name="person") 
    public Person addPerson(@WebParam(name="person") Person person);
 
    @WebMethod(operationName="updatePerson")
    @WebResult(name="person") 
    public Person updatePerson(@WebParam(name="person") Person person);
    
    @WebMethod(operationName="deletePerson")
    @WebResult(name="personId") 
    public int deletePerson(@WebParam(name="personId") int id);
    
    @WebMethod(operationName="deleteLifeStatus")
    @WebResult(name="personId") 
    public int deleteLifeStatus(@WebParam(name="lsId") int id);
    
    @WebMethod(operationName="createLifeStatus")
    @WebResult(name="personId") 
    public int createLifeStatus(@WebParam(name="personId") int id, @WebParam(name="lifeStatus") LifeStatus ls);
    
    
    @WebMethod(operationName="updatePersonHealthProfile")
    @WebResult(name="hpId") 
    public int updatePersonHP(@WebParam(name="personId") int id, @WebParam(name="healthProfile") LifeStatus hp);
}