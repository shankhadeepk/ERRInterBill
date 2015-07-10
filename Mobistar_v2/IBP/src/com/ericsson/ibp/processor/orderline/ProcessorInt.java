package com.ericsson.ibp.processor.orderline;

import java.util.List;

import com.ericsson.ibp.dao.IBPOutputEntryInt;
import com.ericsson.ibp.data.model.IBPInputBean;

/**
 * @author ejyoban
 * 
 */

public interface ProcessorInt {
	///executes the entire logic for a workflow action
	/// for example Install Subscriber. Please note all the actions depicted within 
	/// ACTION_SUBTASK shall be contained within this method.
	/// The method returns a IBPOutputEntryInt object, this object shall contain all the relevant 
	/// database values for the orderline table. The objects are then persisted to the table all at one go
	public List<IBPOutputEntryInt> executeProcess(IBPInputBean input);
}


