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
 *    Antonio Garcia-Dominguez - initial API and implementation
 *******************************************************************************/
package org.hawk.service.server.ifcexport.servlet.servlets;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServlet;
import org.hawk.service.ifc.api.IFCExport;
import org.hawk.service.ifc.api.IFCExport.Iface;
import org.hawk.service.ifc.api.IFCExportJob;
import org.hawk.service.ifc.api.IFCExportOptions;
import org.hawk.service.server.ifcexport.servlet.config.IFCExportManager;
import org.hawk.service.server.ifcexport.servlet.config.IFCExportRequest;

public class IFCExportServlet extends TServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9L;

	private static class IFCImportIface implements Iface {

		@Override
		public IFCExportJob exportAsSTEP(String hawkInstance, IFCExportOptions options) throws TException {
			IFCExportManager export_manager = IFCExportManager.getInstance();
			IFCExportJob job = export_manager.postRequest(new IFCExportRequest(hawkInstance, options));
			return job;
		}

		@Override
		public List<IFCExportJob> getJobs() throws TException {
			ArrayList<IFCExportJob> result = new ArrayList<>();
			result.addAll(IFCExportManager.getInstance().getJobs());
			return result;
		}

		@Override
		public IFCExportJob getJobStatus(String jobID) throws TException {
			return IFCExportManager.getInstance().getJobStatus(jobID);
		}

		@Override
		public boolean killJob(String jobID) throws TException {
			return IFCExportManager.getInstance().killJob(jobID);
		}

	}
	
	public IFCExportServlet() throws Exception{
		super(new IFCExport.Processor<IFCExport.Iface>(new IFCImportIface()), new TCompactProtocol.Factory());
	}
}
