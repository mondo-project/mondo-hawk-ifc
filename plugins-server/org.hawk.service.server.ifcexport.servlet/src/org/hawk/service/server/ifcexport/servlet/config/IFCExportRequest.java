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
 *    Antonio Garcia-Dominguez - cleanup
 *******************************************************************************/
package org.hawk.service.server.ifcexport.servlet.config;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hawk.service.ifc.api.IFCExportOptions;

public class IFCExportRequest {
	
	protected String hawkInstance;
	protected IFCExportOptions exportOptions;
	
	public IFCExportRequest(String hawkInstance, IFCExportOptions exportOptions) {
		this.hawkInstance = hawkInstance;
		this.exportOptions = exportOptions;
	}
	
	public String getHawkInstance() {
		return hawkInstance;
	}
	
	public IFCExportOptions getExportOptions() {
		return exportOptions;
	}
	
	public String getRepositoryPattern()
	{
		return exportOptions.getRepositoryPattern();
	}
	
	public List<String> getFilePatterns()
	{
		return exportOptions.getFilePatterns();
	}
	
	public Map<String,Map<String,Set<String>>> getIncludeRules()
	{
		return exportOptions.getIncludeRules();
	}
	
	public Map<String,Map<String,Set<String>>> getExcludeRules()
	{
		return exportOptions.getExcludeRules();
	}
}
