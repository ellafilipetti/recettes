package recettes;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="etudiants")
public class GateauListeWrapper 
{
	private List<Gateau> gateaux;
	 public List<Gateau> getGateaux()
	 {
		 return gateaux;
	 }
	public void setGateaux(List<Gateau> gateaux)
	{
		this.gateaux=gateaux;
	}
	
	
}