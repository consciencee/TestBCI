package InterfaceVariable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;




import ExampleAPIFiles.Edk;
import ExampleAPIFiles.EmoLogger;
import ExampleAPIFiles.EmoProfile;
import ExampleAPIFiles.EmoState;
import ExampleAPIFiles.Variable;
import Commands.Commands;
import InterfaceVariable.InterfaceVariables;

public class Interface extends JFrame{
	public static XMLOptions xmlOptions = new XMLOptions();

	public static JPanel panel;
	/* train */
	public static JButton trainBtt,saveBtt,loadBtt;	
	public static JComboBox comboBoxMind;
	public static JComboBox comboBoxExpressions;
	
	public static JLabel currentActionsLbl;	
	
	public static final DefaultListModel lMNames = new DefaultListModel();
	public static JList lNames;
	public static JPanel pNames;
	
	public static final DefaultListModel listModelCurrentActions = new DefaultListModel();
	public static JList listCurrentActions;
	public static JPanel panelCurrentActions;
	
	/* settings */
	public static JLabel commandsLbl, mindTrainedLbl, avaliableExpressionsLbl;
	public static JComboBox comboBoxCommands, comboBoxMindTrained, comboBoxAllAvaliableExpressions;
	public static JButton saveExpressionBtt;
	
	//public static JButton addCommandBtt;
	public static JButton trainExpBtt;
	
	public static JLabel powerMindLb, powerExpressionLb;
	public static JSlider powerMind, powerExpression;

	public static final DefaultListModel listModel = new DefaultListModel();
	public static JList listOptions;
	public static JPanel listPanel;
	public static JButton deleteFromListBtt;
	
	public static JButton saveOptionsBtt, loadOptionsBtt;
	public static JButton startSendBtt, stopSendBtt;
	
	/* execute */
	public static JLabel commandNamesLbl;
	public static JPanel commandNamesPanel, executePanel;
	public static JList commandNamesList, executeList;
	public static final DefaultListModel listModelCommandNames = new DefaultListModel();
	public static final DefaultListModel listModelExecuteCommands = new DefaultListModel();
	
	/* logging */
	public static JButton startLogBtt, stopLogBtt;

	public static int[] cognitivActionList ={EmoState.IEE_MentalCommandAction_t.MC_NEUTRAL.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_PUSH.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_PULL.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_LIFT.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_DROP.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_LEFT.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_RIGHT.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_ROTATE_LEFT.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_ROTATE_RIGHT.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_ROTATE_CLOCKWISE.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_ROTATE_COUNTER_CLOCKWISE.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_ROTATE_FORWARDS.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_ROTATE_REVERSE.ToInt(),
		   EmoState.IEE_MentalCommandAction_t.MC_DISAPPEAR.ToInt()
			};
	public static Boolean[] cognitivActionsEnabled = new Boolean[cognitivActionList.length];

	public static int[] expressivActionList ={EmoState.IEE_FacialExpressionAlgo_t.FE_NEUTRAL.ToInt(),
		   EmoState.IEE_FacialExpressionAlgo_t.FE_SURPRISE.ToInt(),
		   EmoState.IEE_FacialExpressionAlgo_t.FE_FROWN.ToInt(),
		   EmoState.IEE_FacialExpressionAlgo_t.FE_SMILE.ToInt(),
		   EmoState.IEE_FacialExpressionAlgo_t.FE_CLENCH.ToInt(),
		   EmoState.IEE_FacialExpressionAlgo_t.FE_LAUGH.ToInt(),
		   EmoState.IEE_FacialExpressionAlgo_t.FE_SMIRK_LEFT.ToInt(),
		   EmoState.IEE_FacialExpressionAlgo_t.FE_SMIRK_RIGHT.ToInt()
			};
	/*public static int[] expressivActionList ={EmoState.EE_ExpressivAlgo_t.EXP_NEUTRAL.ToInt(),
	   EmoState.EE_ExpressivAlgo_t.EXP_CLENCH.ToInt()
		};*/
	public static Boolean[] expressivActionsEnabled = new Boolean[expressivActionList.length];

	
	public Interface() {
		cognitivActionsEnabled[0] = true;
        for (int i = 1; i < cognitivActionList.length; i++)
        {
            cognitivActionsEnabled[i] = false;
        }

        expressivActionsEnabled[0] = true;
        for (int i = 1; i < expressivActionList.length; i++)
        {
        	expressivActionsEnabled[i] = false;
        }
        
       // jtp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        
        panel = new JPanel();
        panel.setLayout(null);    
        panel.setBackground(Color.white);
	//	Container content = getContentPane();
	 //   content.setBackground(Color.white);
	//    content.setLayout(null);
	    
	    comboBoxMind = new JComboBox(InterfaceVariables.optionsMind);
	    comboBoxMind.setLocation(InterfaceVariables.X_BUTTON_LOCATION, InterfaceVariables.Y_BUTTON_LOCATION);
	    comboBoxMind.setSize(InterfaceVariables.WIDTH_BUTTON, InterfaceVariables.HEIGHT_BUTTON);
	   // trainMindPanel.add(comboBoxMind);
	    panel.add(comboBoxMind);
	        
	    
		trainBtt = new JButton("Train MIND");
		InterfaceVariables.X_BUTTON_LOCATION += InterfaceVariables.DELTA_X_BUTTON_LOCATION;
		trainBtt.setLocation(InterfaceVariables.X_BUTTON_LOCATION, InterfaceVariables.Y_BUTTON_LOCATION);
		trainBtt.setSize(InterfaceVariables.WIDTH_BUTTON, InterfaceVariables.HEIGHT_BUTTON);
		trainBtt.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {	
				System.out.println("START");
				int index = comboBoxMind.getSelectedIndex();
			    EmoState.IEE_MentalCommandAction_t cognitivAction = null;
			    switch(index){
			    case InterfaceVariables.Index_NEUTRAL:
			    	System.out.println("START2");
					int i = Edk.INSTANCE.IEE_MentalCommandSetTrainingAction(Variable.userID.getValue(),EmoState.IEE_MentalCommandAction_t.MC_NEUTRAL.ToInt());
					System.out.println("i " + i);
					i = Edk.INSTANCE.IEE_MentalCommandSetTrainingControl(Variable.userID.getValue(), Edk.IEE_MentalCommandTrainingControl_t.MC_START.getType());
					System.out.println("i " + i);
					break;
			    case InterfaceVariables.Index_MIND_CMD_1:
			    	cognitivAction = EmoState.IEE_MentalCommandAction_t.MC_PUSH;
			    	break;
			    case InterfaceVariables.Index_MIND_CMD_2:
			    	cognitivAction = EmoState.IEE_MentalCommandAction_t.MC_PULL;
			    	break;
			    case InterfaceVariables.Index_MIND_CMD_3:
			    	cognitivAction = EmoState.IEE_MentalCommandAction_t.MC_ROTATE_LEFT;
			    	break;
			    case InterfaceVariables.Index_MIND_CMD_4:
			    	cognitivAction = EmoState.IEE_MentalCommandAction_t.MC_ROTATE_RIGHT;
			    	break;
			    }
			    if(index != InterfaceVariables.Index_NEUTRAL){
					try{
						EnableCognitivAction(cognitivAction, true);
						EnableCognitivActionsList();
						StartTrainingCognitiv(cognitivAction);
					}catch(Exception ex){
						ex.printStackTrace();
					}
			    }
			}
		});
		
		panel.add(trainBtt);
		//trainMindPanel.add(trainBtt);
		
		/// Save Profile handle
		saveBtt = new JButton("Save .emo");
		InterfaceVariables.X_BUTTON_LOCATION += InterfaceVariables.DELTA_X_BUTTON_LOCATION;
		saveBtt.setLocation(InterfaceVariables.X_BUTTON_LOCATION, InterfaceVariables.Y_BUTTON_LOCATION);
		saveBtt.setSize(InterfaceVariables.WIDTH_BUTTON, InterfaceVariables.HEIGHT_BUTTON);		
		saveBtt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				EmoProfile.SaveProfile();
				
				JOptionPane.showMessageDialog(new JFrame(), "File was saved", "Dialog",
				        JOptionPane.INFORMATION_MESSAGE);
				/*if (EmoProfileManagement.SaveCurrentProfile() == true){
					EmoProfileManagement.SaveProfilesToFile();
					JOptionPane.showMessageDialog(new JFrame(), "File was saved", "Dialog",
					        JOptionPane.INFORMATION_MESSAGE);
				}*/
			}
		});
		panel.add(saveBtt);
		//SaveLoadProfilePanel.add(saveBtt);
		
		/// Load Profile handle
		loadBtt = new JButton("Load .emo");
		InterfaceVariables.X_BUTTON_LOCATION += InterfaceVariables.DELTA_X_BUTTON_LOCATION;
		loadBtt.setLocation(InterfaceVariables.X_BUTTON_LOCATION, InterfaceVariables.Y_BUTTON_LOCATION);
		loadBtt.setSize(InterfaceVariables.WIDTH_BUTTON, InterfaceVariables.HEIGHT_BUTTON);	
		
		loadBtt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				
				EmoProfile.LoadProfile();
				
				JOptionPane.showMessageDialog(new JFrame(), "File was loaded", "Dialog",
				        JOptionPane.INFORMATION_MESSAGE);
				
			/*	EmoProfileManagement.LoadProfilesFromFile();
				EmoProfileManagement.SetUserProfile("1");
				String actionList = EmoProfileManagement.CheckCurrentProfile();
				long cognitivActions = Long.valueOf(actionList);
				Edk.INSTANCE.IEE_MentalCommandSetActiveActions(Variable.userID.getValue(), cognitivActions);
				JOptionPane.showMessageDialog(new JFrame(), "File was loaded", "Dialog",
				        JOptionPane.INFORMATION_MESSAGE);*/
			}
		});
		panel.add(loadBtt);
		//SaveLoadProfilePanel.add(loadBtt);
		
	    
		
	    comboBoxExpressions = new JComboBox(InterfaceVariables.optionsExp);
	    InterfaceVariables.Y_BUTTON_LOCATION += InterfaceVariables.DELTA_Y_BUTTON_LOCATION/2;
	    comboBoxExpressions.setLocation(InterfaceVariables.START_X_BUTTON_LOCATION, InterfaceVariables.Y_BUTTON_LOCATION);
	    comboBoxExpressions.setSize(InterfaceVariables.WIDTH_BUTTON, InterfaceVariables.HEIGHT_BUTTON);
	    panel.add(comboBoxExpressions);
	   // trainExprPanel.add(comboBoxExpressions);
	    
	    trainExpBtt = new JButton("Train EXP");
		//InterfaceVariables.Y_BUTTON_LOCATION += InterfaceVariables.DELTA_Y_BUTTON_LOCATION/2;
	    InterfaceVariables.X_BUTTON_LOCATION = InterfaceVariables.START_X_BUTTON_LOCATION + InterfaceVariables.DELTA_X_BUTTON_LOCATION;
		trainExpBtt.setLocation(InterfaceVariables.X_BUTTON_LOCATION, InterfaceVariables.Y_BUTTON_LOCATION);
		trainExpBtt.setSize(InterfaceVariables.WIDTH_BUTTON, InterfaceVariables.HEIGHT_BUTTON);
		trainExpBtt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		    	System.out.println("Pushed button");
					int index = comboBoxExpressions.getSelectedIndex();
				    EmoState.IEE_FacialExpressionAlgo_t expressivAction = null;
				    switch(index){
				    case 0:		
				    	System.out.println("Train Exp Neutral");
				    	Edk.INSTANCE.IEE_FacialExpressionSetSignatureType(Variable.userID.getValue(), 1);	// Edk.EE_ExpressivSignature_t.EXP_SIG_TRAINED
				    	
            	    	Edk.INSTANCE.IEE_FacialExpressionSetTrainingAction(Variable.userID.getValue(), EmoState.IEE_FacialExpressionAlgo_t.FE_NEUTRAL.ToInt());
            	    	Edk.INSTANCE.IEE_FacialExpressionSetTrainingControl(Variable.userID.getValue(), Edk.IEE_FacialExpressionTrainingControl_t.FE_START.toInt());
				    	break;
				    	//{"Neutral",
				    	//"EYEBROW", "FURROW", "SMILE", "CLENCH", "LAUGH","SMIRK_LEFT", "SMIRK_RIGHT"};
				    case 1:
				    	expressivAction = EmoState.IEE_FacialExpressionAlgo_t.FE_SURPRISE;
				    	break;
				    case 2:
				    	expressivAction = EmoState.IEE_FacialExpressionAlgo_t.FE_FROWN;
				    	break;
				    case 3:
				    	expressivAction = EmoState.IEE_FacialExpressionAlgo_t.FE_SMILE;
				    	break;
				    case 4:
				    	expressivAction = EmoState.IEE_FacialExpressionAlgo_t.FE_CLENCH;
				    	break;
				    case 5:
				    	expressivAction = EmoState.IEE_FacialExpressionAlgo_t.FE_LAUGH;
				    	break;
				    case 6:
				    	expressivAction = EmoState.IEE_FacialExpressionAlgo_t.FE_SMIRK_LEFT;
				    	break;
				    case 7:
				    	expressivAction = EmoState.IEE_FacialExpressionAlgo_t.FE_SMIRK_RIGHT;
				    	break;
				    }			   
				    if(index != 0){
						try{
							System.out.println("Call");
							EnableExpressivAction(expressivAction, true);
							EnableExpressivActionsList();
							StartTrainingExpressiv(expressivAction);
						}catch(Exception ex){
							ex.printStackTrace();
						}
				    }
			}
		});
		panel.add(trainExpBtt);
		//trainExprPanel.add(trainExpBtt);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		startLogBtt = new JButton("Start logging");
	    InterfaceVariables.X_BUTTON_LOCATION += InterfaceVariables.DELTA_X_BUTTON_LOCATION;
	    startLogBtt.setLocation(InterfaceVariables.X_BUTTON_LOCATION, InterfaceVariables.Y_BUTTON_LOCATION);
	    startLogBtt.setSize(InterfaceVariables.WIDTH_BUTTON, InterfaceVariables.HEIGHT_BUTTON);
	    startLogBtt.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {	
				if(EmoLogger.enabled){
					EmoLogger.enabled = false;
					startLogBtt.setText("Start logging");
					EmoLogger.closeLogFile();
				}
				else{
					EmoLogger.enabled = true;
					startLogBtt.setText("Stop logging");
					try {
						EmoLogger.openLogFile();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	    panel.add(startLogBtt);
	    
		currentActionsLbl = new JLabel("Current Actions");
		InterfaceVariables.Y_BUTTON_LOCATION += InterfaceVariables.DELTA_Y_BUTTON_LOCATION/2;
		currentActionsLbl.setLocation(InterfaceVariables.START_X_BUTTON_LOCATION, InterfaceVariables.Y_BUTTON_LOCATION);
		currentActionsLbl.setSize(InterfaceVariables.WIDTH_BUTTON, InterfaceVariables.HEIGHT_BUTTON);	
		panel.add(currentActionsLbl);
		//currentActionsPanel.add(currentActionsLbl);
		
		pNames = new JPanel();
		InterfaceVariables.Y_BUTTON_LOCATION += InterfaceVariables.DELTA_Y_BUTTON_LOCATION/4;
		pNames.setLayout(new BorderLayout(1, 1));
		pNames.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		pNames.setBorder(new TitledBorder("Current actions"));
		pNames.setLocation(InterfaceVariables.START_X_BUTTON_LOCATION, InterfaceVariables.Y_BUTTON_LOCATION);
		pNames.setSize(InterfaceVariables.WIDTH_BUTTON, 13*InterfaceVariables.HEIGHT_BUTTON);	    
		firstInitListNames();
		lNames = new JList(lMNames);
		pNames.add(new JScrollPane(lNames), BorderLayout.CENTER);		
		panel.add(pNames);	
		//currentActionsPanel.add(pNames);	
		
		panelCurrentActions = new JPanel();
		InterfaceVariables.X_BUTTON_LOCATION = InterfaceVariables.START_X_BUTTON_LOCATION + InterfaceVariables.WIDTH_BUTTON;
		panelCurrentActions.setLayout(new BorderLayout(1, 1));
		panelCurrentActions.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		panelCurrentActions.setBorder(new TitledBorder("power"));
		panelCurrentActions.setLocation(InterfaceVariables.X_BUTTON_LOCATION - 2, InterfaceVariables.Y_BUTTON_LOCATION);	//InterfaceVariables.X_BUTTON_LOCATION
		panelCurrentActions.setSize(3*InterfaceVariables.WIDTH_BUTTON/2, 13*InterfaceVariables.HEIGHT_BUTTON);	    
		firstInitListCurrentActions();
		listCurrentActions = new JList(listModelCurrentActions);
		panelCurrentActions.add(new JScrollPane(listCurrentActions), BorderLayout.CENTER);		
		panel.add(panelCurrentActions);	
		//currentActionsPanel.add(panelCurrentActions);	
		
	    add(panel);	  
	    
	    WindowListener wndCloser = new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
                	InterfaceVariables.flagEndCycle = 1;
    	    		Edk.INSTANCE.IEE_EngineDisconnect();   	    		
                    e.getWindow().setVisible(false);
                    System.exit(0);
	        }
	    };
	    addWindowListener(wndCloser);
	    
		this.setSize(600, 600);
		this.setVisible(true);   
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			

	}
	
	public boolean findElement(String str){
		System.out.println("comboBoxCommands.getItemCount() " + comboBoxCommands.getItemCount());
		for(int i=0; i<comboBoxCommands.getItemCount();++i){
			if(str.equalsIgnoreCase((String) comboBoxCommands.getItemAt(i))){
				return true;
			}
		}
		return false;
	}
	
	public static String getLastCommandsData(){
		return InterfaceVariables.commandsList.get(InterfaceVariables.commandsList.size()-1).getData();
	}
	
	public static void repaintListCurrentActions(){
		int index = 0;
		
		for(int j = 1; j < InterfaceVariables.currentMindCommands.length; ++j){				
			listModelCurrentActions.set(index , getCurrentMindData(j));
			index++;
		}		

		for(int i = 1; i < InterfaceVariables.optionsExpressions.length; ++i){				
			listModelCurrentActions.set(index, getCurrentExpressionData(i));	
			index++;
		}
	}
	
	public static void firstInitListNames(){
		for(int j = 1; j < InterfaceVariables.currentMindCommands.length; ++j){				
			lMNames.addElement(InterfaceVariables.optionsMind[j] + " =");	// " ,"
		}
		
		for(int i = 1; i < InterfaceVariables.currentExpressionsCommands.length; ++i){				
			lMNames.addElement(InterfaceVariables.optionsExpressions[i] + " =");	// " ,"
		}	
	}
	
	public static void firstInitListCurrentActions(){
		for(int j = 1; j < InterfaceVariables.currentMindCommands.length; ++j){				
			listModelCurrentActions.addElement(getCurrentMindData(j));
		}
		
		for(int i = 1; i < InterfaceVariables.currentExpressionsCommands.length; ++i){				
			listModelCurrentActions.addElement(getCurrentExpressionData(i));
		}		
	}
	
	public static void firstInitListCommandNames(){
		for(int j = 0; j < InterfaceVariables.optionsCommand.length; ++j){				
			listModelCommandNames.addElement(InterfaceVariables.optionsCommand[j]);
		}
	}
	
	public static void firstInitListExecuteCommands(){
		for(int j = 0; j < listModelCommandNames.size(); ++j){				
			listModelExecuteCommands.addElement(0);
		}
	}
	
	public static String getCurrentMindData(int index){
		return "" + InterfaceVariables.currentMindCommands[index]; //" = "
	}
	
	public static String getCurrentExpressionData(int index){
		return "" + InterfaceVariables.currentExpressionsCommands[index]; // " = "
	}
	
    public static void StartTrainingCognitiv(EmoState.IEE_MentalCommandAction_t cognitivAction){
        if (cognitivAction == EmoState.IEE_MentalCommandAction_t.MC_NEUTRAL){
        	Edk.INSTANCE.IEE_MentalCommandSetTrainingAction(Variable.userID.getValue(),EmoState.IEE_MentalCommandAction_t.MC_NEUTRAL.ToInt());
			Edk.INSTANCE.IEE_MentalCommandSetTrainingControl(Variable.userID.getValue(), Edk.IEE_MentalCommandTrainingControl_t.MC_START.getType());
        }
        else{
        	for (int i = 1; i < cognitivActionList.length; i++){
                if (cognitivAction.ToInt() == cognitivActionList[i]) {            
                    if (cognitivActionsEnabled[i]){
                    	Edk.INSTANCE.IEE_MentalCommandSetTrainingAction(Variable.userID.getValue(), cognitivAction.ToInt());
                    	Edk.INSTANCE.IEE_MentalCommandSetTrainingControl(Variable.userID.getValue(), Edk.IEE_MentalCommandTrainingControl_t.MC_START.getType());
                    }
                }
            }
        }
    }
    
    public static void StartTrainingExpressiv(EmoState.IEE_FacialExpressionAlgo_t expressivAction){
    	System.out.println("StartTrainingExpressiv");
        if (expressivAction == EmoState.IEE_FacialExpressionAlgo_t.FE_NEUTRAL){
	    	Edk.INSTANCE.IEE_FacialExpressionSetTrainingAction(Variable.userID.getValue(), EmoState.IEE_FacialExpressionAlgo_t.FE_NEUTRAL.ToInt());
	    	Edk.INSTANCE.IEE_FacialExpressionSetTrainingControl(Variable.userID.getValue(), Edk.IEE_FacialExpressionTrainingControl_t.FE_START.toInt());
	    	
        }
        else{
        	for (int i = 1; i < expressivActionList.length; i++){
                if (expressivAction.ToInt() == expressivActionList[i]) {            
                    if (expressivActionsEnabled[i]){
                    	Edk.INSTANCE.IEE_FacialExpressionSetTrainingAction(Variable.userID.getValue(), expressivAction.ToInt()); 
                    	Edk.INSTANCE.IEE_FacialExpressionSetTrainingControl(Variable.userID.getValue(), Edk.IEE_FacialExpressionTrainingControl_t.FE_ERASE.toInt()); 
                    	
            	    	Edk.INSTANCE.IEE_FacialExpressionSetTrainingAction(Variable.userID.getValue(), expressivAction.ToInt());
            	    	Edk.INSTANCE.IEE_FacialExpressionSetTrainingControl(Variable.userID.getValue(), Edk.IEE_FacialExpressionTrainingControl_t.FE_START.toInt());
            	    	
            	    	
            	    	//Edk.INSTANCE.EE_ExpressivSetSignatureType(0, 1);	// Edk.EE_ExpressivSignature_t.EXP_SIG_TRAINED
            	    	//IntByReference trained_neurtal = new IntByReference(1);
            	    	//Edk.INSTANCE.EE_ExpressivGetTrainedSignatureAvailable(0, trained_neurtal);
            	    	//Edk.INSTANCE.EE_ExpressivSetThreshold(0, expressivAction.ToInt(), 0, 0);
            	    	//Edk.INSTANCE.EE_ExpressivSetTrainingAction(0, expressivAction.ToInt());
                      //	Edk.INSTANCE.EE_ExpressivSetTrainingControl(0, Edk.EE_ExpressivTrainingControl_t.EXP_ACCEPT.toInt()); 
                    }
                }
            }
        }
    }
    
    public static void EnableCognitivAction(EmoState.IEE_MentalCommandAction_t cognitivAction, Boolean iBool){
        for (int i = 1; i < cognitivActionList.length; i++){
            if (cognitivAction.ToInt() == cognitivActionList[i]){
                cognitivActionsEnabled[i] = iBool;               
            }
        }
    }
    
    public static void EnableExpressivAction(EmoState.IEE_FacialExpressionAlgo_t expressivAction, Boolean iBool){
    	System.out.println("EnableExpressivAction");
    	for (int i = 1; i < expressivActionList.length; i++){
            if (expressivAction.ToInt() == expressivActionList[i]){
            	expressivActionsEnabled[i] = iBool;        
            	System.out.println("EnableExpressivAction i = " + i);
            }
        }
    }
    
    public static void EnableCognitivActionsList() {
        long cognitivActions = 0x0000;
        for (int i = 1; i < cognitivActionList.length; i++){
            if (cognitivActionsEnabled[i]){
                cognitivActions = cognitivActions | ((long)cognitivActionList[i]);    
            }
        }
        Edk.INSTANCE.IEE_MentalCommandSetActiveActions(Variable.userID.getValue(), cognitivActions);     
    }
    
    public static void EnableExpressivActionsList() {
        int expressivActions = 0x0000;
        for (int i = 1; i < expressivActionList.length; i++){
            if (expressivActionsEnabled[i]){
            	expressivActions = expressivActions | (expressivActionList[i]);    
            }
        }
        Edk.INSTANCE.IEE_FacialExpressionSetTrainingAction(Variable.userID.getValue(), expressivActions);     
    }
}