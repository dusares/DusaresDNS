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

public class MessageHeader {

	/* globals */

	public final static byte TYPE_QUERY = 0;
	public final static byte TYPE_RESPONSE = 1;

	public final static byte OPCODE_QUERY = 0;
	public final static byte OPCODE_IQUERY = 1;
	public final static byte OPCODE_STATUS = 2;
	public final static byte OPCODE_RESERVED = 3;
	public final static byte OPCODE_NOTIFY = 4;
	public final static byte OPCODE_UPDATE = 5;

	public final static byte RCODE_NO_ERROR = 0;
	public final static byte RCODE_FORMAT_ERROR = 1;
	public final static byte RCODE_SERVER_FAILURE = 2;
	public final static byte RCODE_NAME_ERROR = 3;
	public final static byte RCODE_NOT_IMPLEMENTED = 4;
	public final static byte RCODE_REFUSED = 5;
	public final static byte RCODE_YX_DOMAIN = 6;
	public final static byte RCODE_YX_RR_SET = 7;
	public final static byte RCODE_NX_RR_SET = 8;
	public final static byte RCODE_NOT_AUTH = 9;
	public final static byte RCODE_NOT_ZONE = 10;

	public final static int HEADER_LENGTH = 12;
	
	/* data */

	// id counter
	private static short ID = 0;

	// identifier
	private short _id;

	// query/response flag
	// false - query
	// true  - response
	private boolean _qr;

	// opcode
	private byte _opcode;

	// authoratitive answer flag
	// false - non-authoritative
	// true  - authoritative
	private boolean _aa;

	// truncation flag (when bigger than 512 bytes):
	// false - message not truncated
	// true  - message truncated (TCP might be needed)
	private boolean _tc;

	// recursion desired:
	// false - no recursion desired
	// true  - recursion desired
	private boolean _rd;

	// recursion available:
	// false - no recursion available
	// true  - recursion available
	private boolean _ra;

	// response code
	private byte _rcode;

	// question count
	private short _qdcount;

	// answer count
	private short _ancount;

	// authority record count
	private short _nscount;

	// additional record count
	private short _arcount;

	/* constructors */

	public MessageHeader() {
		_id = ID++;
	}

	/* getters/setters */

	public short getID() {
		return _id;
	}

	public void setID(short id) {
		_id = id;
	}

	public boolean getQR() {
		return _qr;
	}

	public void setQR(boolean qr) {
		_qr = qr;
	}

	public byte getOpcode() {
		return _opcode;
	}

	public void setOpcode(byte opcode) {
		_opcode = opcode;
	}

	public String getOpcodeName() {
		return MessageHeader.getOpcodeName(_opcode);
	}

	public static String getOpcodeName(byte opcode) {
		
		switch (opcode) {
			case OPCODE_QUERY:
				return "QUERY";
			case OPCODE_IQUERY:
				return "IQUERY";
			case OPCODE_STATUS:
				return "STATUS";
			case OPCODE_RESERVED:
				return "RESERVED";
			case OPCODE_NOTIFY:
				return "NOTIFY";
			case OPCODE_UPDATE:
				return "UPDATE";
			default:
				return "UNKNOWN ("+opcode+")";
		}
	}

	public boolean getAA() {
		return _aa;
	}

	public void setAA(boolean aa) {
		_aa = aa;
	}

	public boolean getTC() {
		return _tc;
	}

	public void setTC(boolean tc) {
		_tc = tc;
	}

	public boolean getRD() {
		return _rd;
	}

	public void setRD(boolean rd) {
		_rd = rd;
	}

	public boolean getRA() {
		return _ra;
	}

	public void setRA(boolean ra) {
		_ra = ra;
	}

	public byte getRcode() {
		return _rcode;
	}

	public void setRcode(byte rcode) {
		_rcode = rcode;
	}

	public String getRcodeName() {
		return MessageHeader.getRcodeName(_rcode);
	}

	public static String getRcodeName(byte rcode) {
		switch (rcode) {
			case RCODE_NO_ERROR:
				return "NO ERROR";
			case RCODE_FORMAT_ERROR:
				return "FORMAT ERROR";
			case RCODE_SERVER_FAILURE:
				return "SERVER FAILURE";
			case RCODE_NAME_ERROR:
				return "NAME ERROR";
			case RCODE_NOT_IMPLEMENTED:
				return "NOT IMPLEMENTED";
			case RCODE_REFUSED:
				return "REFUSED";
			case RCODE_YX_DOMAIN:
				return "YX DOMAIN";
			case RCODE_YX_RR_SET:
				return "YX RR SET";
			case RCODE_NX_RR_SET:
				return "NX RR SET";
			case RCODE_NOT_AUTH:
				return "NOT AUTH";
			case RCODE_NOT_ZONE:
				return "NOT ZONE";
			default:
				return "UNKNOWN ("+rcode+")";
		}
	}

	public short getQDcount() {
		return _qdcount;
	}

	public void setQDcount(short qdcount) {
		_qdcount = qdcount;
	}

	public short getANcount() {	
		return _ancount;
	}

	public void setANcount(short ancount) {
		_ancount = ancount;
	}

	public short getNScount() {
		return _nscount;
	}

	public void setNScount(short nscount) {
		_nscount = nscount;
	}

	public short getARcount() {
		return _arcount;
	}

	public void setARcount(short arcount) {
		_arcount = arcount;
	}

	/* helpers */

	public byte[] toByteArray() {
		byte[] data = new byte[12];

		// TODO

		return data;
	}

	public int readFromByteArray(byte[] data, int start) {

		if (start + HEADER_LENGTH > data.length) {
			Log.write("MessageHeader.readFromByteArray: not enough data");
			return data.length;
		}
	
		// set start pointer
		int pos = start;
		byte tmp;

		// id
		_id = 0;
		_id |= data[0] & 0xff;
		_id <<= 8;
		_id |= data[1] & 0xff;

		// qr
		tmp = data[2];
		tmp >>= 7;
		tmp &= 0x01;
		_qr = (tmp == 1);

		// opcode
		_opcode = (byte)(data[2] & 0xff);
		_opcode >>= 3;
		_opcode &= 0x0f;

		// aa
		tmp = (byte)(data[2] & 0xff);
		tmp >>= 2;
		tmp &= 0x01;
		_aa = (tmp == 1);

		// tc
		tmp = (byte)(data[2] & 0xff);
		tmp >>= 1;
		tmp &= 0x01;
		_tc = (tmp == 1);

		// rd
		tmp = (byte)(data[2] & 0x01);
		_rd = (tmp == 1);

		// ra
		tmp = (byte)(data[3] & 0xff);
		tmp >>= 7;
		tmp &= 0x01;
		_ra = (tmp == 1);

		// skip 3 bits of reserved zeroes

		// rcode
		_rcode = (byte)(data[3] & 0xff);
		_rcode &= 0x0f;

		// question count
		_qdcount = 0;
		_qdcount |= data[4] & 0xff;
		_qdcount <<= 8;
		_qdcount |= data[5] & 0xff;

		// anser count
		_ancount = 0;
		_ancount |= data[6] & 0xff;
		_ancount <<= 8;
		_ancount |= data[7] & 0xff;

		// authority count
		_nscount = 0;
		_nscount |= data[8] & 0xff;
		_nscount <<= 8;
		_nscount |= data[9] & 0xff;

		// additional count
		_arcount = 0;
		_arcount |= data[10] & 0xff;
		_arcount <<= 8;
		_arcount |= data[11] & 0xff;

		// debug
		Log.write(this.toString());
		
		// return new position
		return (start + 12);
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append("MessageHeader\n");
		buf.append("- ID      : " + _id + "\n");
		buf.append("- QR      : " + _qr + "\n");
		buf.append("- OPCODE  : " + _opcode + " (" + getOpcodeName() + ")\n");
		buf.append("- AA      : " + _aa + "\n");
		buf.append("- TC      : " + _tc + "\n");
		buf.append("- RD      : " + _rd + "\n");
		buf.append("- RA      : " + _ra + "\n");
		buf.append("- RCODE   : " + _rcode + " (" + getRcodeName() + ")\n");
		buf.append("- QDCOUNT : " + _qdcount + "\n");
		buf.append("- ANCOUNT : " + _ancount + "\n");
		buf.append("- NSCOUNT : " + _nscount + "\n");
		buf.append("- ARCOUNT : " + _arcount + "\n");

		return buf.toString();
	}
}
