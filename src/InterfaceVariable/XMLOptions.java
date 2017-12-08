package InterfaceVariable;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Commands.Commands;

class XMLOptions{
	String XMLFileName = "options.xml";
	
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = null;	
    
    Document document;
    Element rootElement;
    /**
     * initialize all fields
     * @param cmdBase - command space
     */
    public XMLOptions(){ 
    }
    
    void saveFile(){
		try {
			builder = dbf.newDocumentBuilder();
			document = builder.newDocument(); 
			rootElement = document.createElement("options");
			document.appendChild(rootElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	for(int i=0; i < InterfaceVariables.commandsList.size(); ++i){   		
    		add(i, InterfaceVariables.commandsList.get(i));
    	}   	
    	close();
    }
   
    void add(int index, Commands cmd){   	
		try { 
			Element el = document.createElement("option");
			// int indexCommandName;			
			// int INDEX_MIND;
			// float POWER_MIND;			
			// int INDEX_EXPRESSIV;
			// float POWER_EXPRESSIV;	
			el.setAttribute("index_expressiv", Integer.toString(cmd.getIndexExpression()));
			el.setAttribute("index_mind", Integer.toString(cmd.getIndexMind()));
			
			el.setAttribute("power_expressiv", Float.toString(cmd.getPowerExpression()));
			el.setAttribute("power_mind", Float.toString(cmd.getPowerMind()));	
			
			el.setAttribute("index_commandName", Integer.toString(cmd.getIndexCommandName()));
			rootElement.appendChild(el);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }   
    
    /**
     * was pushed button in Draw
     */
    public void close(){
        Transformer t = null;
		try {
			t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.METHOD, "xml");
			t.setOutputProperty(OutputKeys.INDENT, "yes");       
			t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(XMLFileName)));    	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void loadFile(){
    	// clear old data
    	InterfaceVariables.commandsList.clear();
    	Interface.listModel.clear();
    	
    	// read and write new data
		int indexCommandName;			
		int INDEX_MIND;
		float POWER_MIND;			
		int INDEX_EXPRESSIV;
		float POWER_EXPRESSIV;
		
		try{
			DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf1.newDocumentBuilder();
			Document doc = db.parse(XMLFileName);
			doc.getDocumentElement().normalize();

			NodeList nodeLst = doc.getElementsByTagName("option");
			for(int je = 0;je < nodeLst.getLength(); je++){
				Node fstNode = nodeLst.item(je);
				if(fstNode.getNodeType() == Node.ELEMENT_NODE){				
					Element elj = (Element)fstNode;
					indexCommandName = Integer.parseInt(elj.getAttribute("index_commandName"));
					INDEX_EXPRESSIV = Integer.parseInt(elj.getAttribute("index_expressiv"));
					INDEX_MIND = Integer.parseInt(elj.getAttribute("index_mind"));
					POWER_EXPRESSIV = Float.parseFloat(elj.getAttribute("power_expressiv")) * 100f;
					POWER_MIND = Float.parseFloat(elj.getAttribute("power_mind")) * 100f;
					
					InterfaceVariables.commandsList.add(new Commands(indexCommandName, INDEX_MIND, INDEX_EXPRESSIV, (int)POWER_MIND, (int)POWER_EXPRESSIV));
					Interface.listModel.addElement(Interface.getLastCommandsData());
				}               
			}	            
		}catch(Exception ei){System.out.println("Error");}

		
    }
}