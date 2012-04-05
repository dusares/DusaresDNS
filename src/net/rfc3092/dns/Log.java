/*
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

import java.util.Date;
import java.text.SimpleDateFormat;

public class Log {

	public static final int NONE = 0;
	public static final int NORMAL = 10;
	public static final int VERBOSE = 20;
	public static final int DEBUG = 30;
	
	private static int _level = NORMAL;

	public static void setLogLevel(int level) {
		_level = level;
	}

	public static void write(String msg) {
		write(msg, NORMAL);
	}

	public static void write(String msg, int level) {
		if (level > _level) return;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		String[] lines = msg.split("\n");
		for (int i = 0; i < lines.length; i++)
			System.out.println(df.format(new Date()) + ": " + lines[i]);
	}
}
