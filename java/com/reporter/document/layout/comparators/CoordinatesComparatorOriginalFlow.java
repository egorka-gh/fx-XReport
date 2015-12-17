package com.reporter.document.layout.comparators;

import java.util.Comparator;

import com.reporter.document.layout.Layout;
import com.reporter.document.layout.XlsReference;

public class CoordinatesComparatorOriginalFlow<T extends Layout> implements Comparator<Layout> {

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Layout o1, Layout o2) {
        XlsReference ref1 = o1.getReferences();
        XlsReference ref2 = o2.getReferences();
        
        /*
        if(o1.getCommand()==XLSCommands.XLSCommandRow){
        	//sort row first inside crosstab, col - very last
            DinamicLayout pr1 = o1.getParents().get(0);
            if(pr1!=null && pr1.getCommand()==XLSCommands.XLSCommandCrossTab){
            	//cross tab row - first (to add before cross column in TemplateCoordinatesComparator) 
            	return -1;
            }
        }
        if(o1.getCommand()==XLSCommands.XLSCommandColumn){
        	//sort row first inside crosstab, col - very last
            DinamicLayout pr1 = o1.getParents().get(0);
            if(pr1!=null && pr1.getCommand()==XLSCommands.XLSCommandCrossTab){
            	//cross tab col - last (to add before cross column in TemplateCoordinatesComparator) 
            	return 1;
            }
        }
        */

        
        if ((ref1.getColIndex() == ref2.getColIndex()) && (ref1.getRowIndex() == ref2.getRowIndex())){
            //same cell
            return 0;
        } else if (ref1.getRowIndex() < ref2.getRowIndex()){
            //over
            return -1;
        } else if (ref1.getRowIndex() > ref2.getRowIndex()){
            //udner
            return 1;
        } else {
            //same row
            if (ref1.getColIndex() < ref2.getColIndex()){
                //is before
                return -1;
            } else {
                //is after
                return 1;
            }
        }
    }
}
