/**
 * 
 */
package com.reporter.document.layout.comparators;

import java.util.Comparator;

import org.apache.poi.hssf.util.CellReference;

import com.reporter.document.layout.Layout;

/**
 * @author Ilya Ovesnov
 *
 * @param <T>
 */
public class TemplateCoordinatesComparator<T extends Layout> implements Comparator<Layout> {
    
    /**
     * inverse value
     */
    private static final int INVERSE = -1;

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Layout o1, Layout o2) {
        
        CellReference cell1From = o1.getReferences().getCellReference();
        CellReference cell2From = o2.getReferences().getCellReference();
        CellReference cell2To = o2.getReferences().getCellReferenceTo();

        /* not work
        //crosstab column after cross row
        DinamicLayout pr1;
        DinamicLayout pr2;
        if((o1.getCommand()==XLSCommands.XLSCommandRow || o1.getCommand()==XLSCommands.XLSCommandColumn) &&
        		(o2.getCommand()==XLSCommands.XLSCommandRow || o2.getCommand()==XLSCommands.XLSCommandColumn) &&
        		(o1.getCommand()!=o2.getCommand())){
        	//sort inside crosstab
            pr1 = o1.getParents().get(0);
            if(pr1!=null && pr1.getCommand()==XLSCommands.XLSCommandCrossTab){
                pr2 = o2.getParents().get(0);
                if(pr2!=null && pr2.getCommand()==XLSCommands.XLSCommandCrossTab && pr1==pr2){
                	if(o1.getCommand()==XLSCommands.XLSCommandRow){
                		return -1;
                	}else{
                		return 1;
                	}
                }
            }
        }
        */
        
        if (cell1From.getCol() > cell2From.getCol()){
            return INVERSE * compare(o2, o1);
        } else if (cell1From.getCol() == cell2From.getCol()){
            if (cell1From.getRow() > cell2From.getRow()){
                return 1;
            } else if (cell1From.getRow() < cell2From.getRow()){
                return -1;
            } else {
                return 0;
            }
        } else {
            if (cell1From.getRow() > cell2To.getRow()){
                return 1;
            } else {
                return -1;
            }
        }
        
    }

}
