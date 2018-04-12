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
 *    Antonio Garcia-Dominguez - cleanup, use shared scheduling rule instance
 *******************************************************************************/
package org.hawk.service.server.ifcexport.servlet.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.hawk.service.ifc.api.IFCExportJob;
import org.hawk.service.ifc.api.IFCExportStatus;

public class IFCExportManager {

	private static IFCExportManager instance = new IFCExportManager();

	private final ISchedulingRule schedulingRule;
	
	private IFCExportManager(){
		schedulingRule = new ISchedulingRule() {
			@Override
			public boolean isConflicting(ISchedulingRule rule) {
				return this == rule;
			}

			@Override
			public boolean contains(ISchedulingRule rule) {
				return this == rule;
			}
		};
	}

	public static IFCExportManager getInstance()
	{
		if(instance == null)
		{
			instance = new IFCExportManager();
			return instance;
		}
		return instance;
	}

	protected JobIDGenerator jobIDGen = new JobIDGenerator();

	// TODO: meet with Will - these maps probably need to be flipped/reorganized
	protected Map<IFCExportJob, IFCExportRequest> requestMap = new HashMap<>();
	protected Map<IFCExportJob, IFCExportJobExecutor> executorMap = new HashMap<>();
	
	public IFCExportJob postRequest(IFCExportRequest request)
	{
		if (requestMap.values().contains(request)) {
			for(IFCExportJob job: requestMap.keySet())
			{
				if(requestMap.get(job).equals(request))
				{
					return job;
				}
			}
			return null;
		}
		else {
			String jobID = jobIDGen.nextSessionId();
			IFCExportJob job = new IFCExportJob(jobID, IFCExportStatus.SCHEDULED, "added to queue");
			IFCExportJobExecutor exe_thread = new IFCExportJobExecutor(job, request);
			exe_thread.setRule(schedulingRule);
			exe_thread.addJobChangeListener(new IFCExportJobChangeListener(job));
			requestMap.put(job, request);
			exe_thread.schedule();
			executorMap.put(job, exe_thread);
			return job;
		}
	}
	
	public Set<IFCExportJob> getJobs()
	{
		return requestMap.keySet();
	}
	
	public IFCExportJob getJob(String jobID)
	{
		for(IFCExportJob job: requestMap.keySet())
		{
			if(job.getJobID().equals(jobID))
			{
				return job;
			}
		}
		return null;
	}
	
	public IFCExportJob getJobStatus(String jobID)
	{
		IFCExportJob job = getJob(jobID);
		if(job != null)
		{
			return job;
		}
		return null;
	}
	
	public boolean killJob(String jobID)
	{
		IFCExportJob job = getJob(jobID);
		if(job == null)
		{
			return false;
		}
		else
		{
			IFCExportJobExecutor executor = executorMap.get(job);
			if(executor != null)
			{
				executor.cancel();
				job.setStatus(IFCExportStatus.CANCELLED);
				return true;
			}
			else {
				return false;
			}
		}
	}

}
