package com.ericsson.ibp.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ejyoban
 * 
 */

public class IBPProdConsLinkCollection {
	private List<IBPProdConsLinkInsert> newLinksForInsert;
	private List<IBPProdConsLinkInsert> newLinksForUpdate;
	private List<IBPProdConsLinkInsert> newLinksForDelete;
	
	public IBPProdConsLinkCollection()
	{
		newLinksForInsert = new ArrayList<IBPProdConsLinkInsert>();
		newLinksForUpdate = new ArrayList<IBPProdConsLinkInsert>();
		newLinksForDelete = new ArrayList<IBPProdConsLinkInsert>();
	}

	public List<IBPProdConsLinkInsert> getNewLinksForInsert() {
		return newLinksForInsert;
	}

	public void setNewLinksForInsert(List<IBPProdConsLinkInsert> newLinksForInsert) {
		this.newLinksForInsert = newLinksForInsert;
	}

	public List<IBPProdConsLinkInsert> getNewLinksForUpdate() {
		return newLinksForUpdate;
	}

	public void setNewLinksForUpdate(List<IBPProdConsLinkInsert> newLinksForUpdate) {
		this.newLinksForUpdate = newLinksForUpdate;
	}

	public List<IBPProdConsLinkInsert> getNewLinksForDelete() {
		return newLinksForDelete;
	}

	public void setNewLinksForDelete(List<IBPProdConsLinkInsert> newLinksForDelete) {
		this.newLinksForDelete = newLinksForDelete;
	}
	
}


