package com.java102.cesar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ReadXML 
{
	public static ArrayList<Parcel> readFromXML(String file)
	{
		try {
			File xmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			ArrayList<Parcel> parcelList = new ArrayList<Parcel>();
			
			NodeList nList = doc.getElementsByTagName("parcels");
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);
				
				String firstName = "";
				String lastName = "";
				String address = "";
				String city = "";
				String state = "";
				String zip = "";
				String date = "";
				int ID = 0000;
				String time = "";
				NodeList childs = nNode.getChildNodes();
				ID = Integer.parseInt(getAttribute("ID", nNode));
				for(int tempLoop = 0; tempLoop< childs.getLength(); tempLoop++)
				{
					if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("firstname"))
					{
						firstName = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("lastname"))
					{
						lastName = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("address"))
					{
						address = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("city"))
					{
						city = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("state"))
					{
						state = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("zip"))
					{
						zip = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("date"))
					{
						date = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("time"))
					{
						time = childs.item(tempLoop).getTextContent();
					}
				}
				
				Address newAddress = new Address(address, city, state, zip);
				Person newPerson = new Person(firstName, lastName, date, newAddress);
				Parcel newParcel = new Parcel(ID,time,newPerson);
				parcelList.add(newParcel);
			}
			
			return parcelList;
					
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return null;
	}
	
	private static String getAttribute(String name, Node node) 
	{
		NamedNodeMap nmap = node.getAttributes();
		try {
			return nmap.getNamedItem(name).getTextContent();
		} catch (Exception e) {
			return "";
		}
	}
	public static boolean commitToFile(ArrayList<Parcel> parcel, String filePath) 
	{

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			Document parcelsDoc;
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			parcelsDoc = docBuilder.newDocument();

			Element rootElement = parcelsDoc.createElement("People");
			
			final int count = parcel.size();
			for (int i = 0; i < count; ++i) {
				final Parcel parcels = parcel.get(i);
				
				Element parcelsElement = parcelsDoc.createElement("parcels");
				parcelsElement.setAttribute("ID", Integer.toString(parcels.getID()));
				
				Element firstNameElement = parcelsDoc.createElement("FirstName");
                firstNameElement.setTextContent(parcels.getPerson().getFirstName());
                
				Element lastNameElement = parcelsDoc.createElement("LastName");
				lastNameElement.setTextContent(parcels.getPerson().getLastName());
                
				Element addressElement = parcelsDoc.createElement("Address");
				addressElement.setTextContent(parcels.getPerson().getAddress().getAddress());
                
				Element cityElement = parcelsDoc.createElement("City");
				cityElement.setTextContent((parcels.getPerson().getAddress().getCity()));
				
				Element stateElement = parcelsDoc.createElement("State");
				stateElement.setTextContent(parcels.getPerson().getAddress().getState());
				
				Element zipElement = parcelsDoc.createElement("Zip");
				zipElement.setTextContent(parcels.getPerson().getAddress().getZip());
				
				Element dateElement = parcelsDoc.createElement("Date");
				dateElement.setTextContent(parcels.getPerson().getDate());
				
				Element timeElement = parcelsDoc.createElement("Time");
				timeElement.setTextContent(Integer.toString(parcels.getID()));
                
                
				parcelsElement.appendChild(firstNameElement);
				parcelsElement.appendChild(lastNameElement);
				parcelsElement.appendChild(addressElement);
				parcelsElement.appendChild(cityElement);
				parcelsElement.appendChild(stateElement);
				parcelsElement.appendChild(zipElement);
				parcelsElement.appendChild(dateElement);
				parcelsElement.appendChild(timeElement);
				rootElement.appendChild(parcelsElement);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(rootElement);
			
			StreamResult result = new StreamResult(new File(filePath));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);

		} catch (Exception e) {
			return false;
		}

		return true;
	}

}