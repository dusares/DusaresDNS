/*
 * (C) 2012 Peter Senft <peter@rfc3092.net>
 *
 * This file is part of DusaresDNS.
 * 
 * DusaresDNS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * DusaresDNS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with DusaresDNS.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.rfc3092.dns;

public class QuestionRecord {

	/* globals */

	/* data */

	// question name
	// - length value encoding in leading byte.
	// - if 0 then end of name
	private byte[] _name;


	/* constructors */
	
	public QuestionRecord() {
	}

	/* getter/setters */

	/* helpers */

	public String toString() {
		StringBuffer buf = new StringBuffer();

		return buf.toString();
	}
}
