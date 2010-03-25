package cl.continuum.mqtoolkit.model;

import javax.swing.table.DefaultTableModel;

public class MQTableModel extends DefaultTableModel {
	
	public MQTableModel(String[][] tableModel, String[] strings) {
		super(tableModel, strings);
	}

	public boolean isCellEditable (int row, int column)
	   {
	       // Aqu� devolvemos true o false seg�n queramos que una celda
	       // identificada por fila,columna (row,column), sea o no editable
	       if (column == 3)
	          return true;
	       return false;
	   }


}
