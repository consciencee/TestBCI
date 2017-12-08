package Commands;

import InterfaceVariable.InterfaceVariables;

public class Commands{
	int indexCommandName = -1;	// None
	
	int INDEX_MIND = 0;
	float POWER_MIND = 0;
	
	int INDEX_EXPRESSIV = 0;
	float POWER_EXPRESSIV = 0;
	
	public Commands(int indexStringName, int index_mind, int index_expressiv, int percentPower_mind, int percentPower_expressiv){
		indexCommandName = indexStringName;
		INDEX_MIND = index_mind;
		INDEX_EXPRESSIV = index_expressiv;

		POWER_MIND = (float)(percentPower_mind) / 100f;
		POWER_EXPRESSIV = (float)(percentPower_expressiv) / 100f;
	}
	
	public String getData(){
		return "Name: " + InterfaceVariables.optionsCommand[indexCommandName] + ";   MIND = " + InterfaceVariables.optionsMindTrained[INDEX_MIND] + ", min_pow = "  +  POWER_MIND + " ;   EXPRESSIV = " + InterfaceVariables.optionsExpressions[INDEX_EXPRESSIV] + ", min_pow = "  +  POWER_EXPRESSIV;
	}
	
	public int getIndexMind(){
		return INDEX_MIND;
	}
	
	public int getIndexExpression(){
		return INDEX_EXPRESSIV;
	}
	
	public float getPowerMind(){
		return POWER_MIND;
	}
	
	public float getPowerExpression(){
		return POWER_EXPRESSIV;
	}
	
	public String getCommandName(){
		return InterfaceVariables.optionsCommand[indexCommandName];
	}
	
	public int getIndexCommandName(){
		return indexCommandName;
	}
}