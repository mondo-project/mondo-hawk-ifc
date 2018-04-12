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

import java.math.BigInteger;
import java.security.SecureRandom;

public final class JobIDGenerator {

	  private SecureRandom random = new SecureRandom();

	  public String nextSessionId() {
	    return new BigInteger(130, random).toString(32);
	  }

}
