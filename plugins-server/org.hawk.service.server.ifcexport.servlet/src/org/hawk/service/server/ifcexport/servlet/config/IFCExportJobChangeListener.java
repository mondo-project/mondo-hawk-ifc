/*******************************************************************************
 * Copyright (c) 2015-2016 University of York.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * Contributors:
 *    Ran Wei - initial API and implementation
 *******************************************************************************/
package org.hawk.service.server.ifcexport.servlet.config;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.hawk.service.ifc.api.IFCExportJob;
import org.hawk.service.ifc.api.IFCExportStatus;

public class IFCExportJobChangeListener extends JobChangeAdapter {

	protected IFCExportJob job;
	
	public IFCExportJobChangeListener(IFCExportJob job) {
		this.job = job;
	}

	public void setJob(IFCExportJob job) {
		this.job = job;
	}

	@Override
	public void done(IJobChangeEvent event) {
		job.setStatus(IFCExportStatus.DONE);
	}

	@Override
	public void running(IJobChangeEvent event) {
		job.setStatus(IFCExportStatus.RUNNING);
	}

	@Override
	public void scheduled(IJobChangeEvent event) {
		job.setStatus(IFCExportStatus.SCHEDULED);
	}
}
