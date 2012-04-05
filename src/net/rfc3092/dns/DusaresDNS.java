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

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

public class DusaresDNS {

	// TODO: make this a thread

	public static void main(String args[]) {
		try {
			InetAddress address = InetAddress.getByName("localhost");
			InetSocketAddress socketAddress = new InetSocketAddress(address, 8888);
			DatagramSocket socket = new DatagramSocket(socketAddress);

			while (socket.isBound()) {
				DatagramPacket packet = new DatagramPacket(new byte[512], 512);
				socket.receive(packet);
				byte[] data = packet.getData();
				int pos = 0;

				MessageHeader mh = new MessageHeader();
				pos += mh.readFromByteArray(data, 0);

				// question
				int qdcount = mh.getQDcount();
				StringBuffer qnameBuf = null;
				String qname[] = new String[qdcount];
				int qtype[] = new int[qdcount];
				int qclass[] = new int[qdcount];

				for (int q = 0; q < qdcount; q++) {

					// qname
					qnameBuf = new StringBuffer();

					while (data[pos] != 0) {
						int len = (int)(data[pos] & 0xff);
						pos++;
						for (int p = 0; p < len; p++) {
							qnameBuf.append((char)data[pos]);
							pos++;
						}
						if (data[pos] != 0) {
							qnameBuf.append(".");
						}
					}

					qname[q] = qnameBuf.toString();
					pos++;

					// type / class
					qtype[q] = 0;
					qtype[q] |= data[pos] & 0xff;
					qtype[q] <<= 8;
					qtype[q] |= data[pos+1] & 0xff;
					pos += 2;

					qclass[q] = 0;
					qclass[q] |= data[pos+2] & 0xff;
					qclass[q] <<= 8;
					qclass[q] |= data[pos+3] & 0xff;
					pos += 2;

					Log.write("Question " + q + ": QNAME=" + qname[q] + " QTYPE=" + qtype[q] + " QCLASS=" + qclass[q]);
				}

				// answer / authority / additional format
				int alen = mh.getANcount() + mh.getNScount() + mh.getARcount();
				StringBuffer anameBuf = null;
				StringBuffer ardataBuf = null;
				String[] aname = new String[alen];
				int[] atype = new int[alen];
				int[] aclass = new int[alen];
				int[] attl = new int[alen];
				int[] ardlength = new int[alen];
				String[] ardata = new String[alen];

				for (int a = 0; a < alen; a++) {
					
					// aname
					anameBuf = new StringBuffer();

					while (data[pos] != 0) {	
						int len = (int)(data[pos] & 0xff);
						pos++;
						for (int p = 0; p < len; p++) {
							anameBuf.append((char)data[pos]);
							pos++;
						}
						if (data[pos] != 0) {
							anameBuf.append(".");
						}
					}

					aname[a] = anameBuf.toString();
					pos++;

					// type / class / ttl / rdlength 
					atype[a] = 0;
					atype[a] |= data[pos] & 0xff;
					atype[a] <<= 8;
					atype[a] |= data[pos] & 0xff;
					pos += 2;

					aclass[a] = 0;
					aclass[a] |= data[pos] & 0xff;
					aclass[a] <<= 8;
					aclass[a] |= data[pos] & 0xff;
					pos += 2;

					attl[a] = 0;
					attl[a] |= data[pos] & 0xff;
					attl[a] <<= 8;
					attl[a] |= data[pos] & 0xff;
					attl[a] <<= 8;
					attl[a] |= data[pos] & 0xff;
					attl[a] <<= 8;
					attl[a] |= data[pos] & 0xff;
					pos += 4;

					ardlength[a] = 0;
					ardlength[a] |= data[pos] & 0xff;
					ardlength[a] <<= 8;
					ardlength[a] |= data[pos] & 0xff;
					pos += 2;

					// rdata
					ardataBuf = new StringBuffer();
					for (int d = 0; d < ardlength[a]; d++) {
						ardataBuf.append((char)data[pos]);
						pos++;
					}
					ardata[a] = ardataBuf.toString();
				}

				// DEBUG	
				/*
				StringBuffer sbufData = new StringBuffer();
				sbufData.append("> ");
				for (int i = 0; i < data.length; i++) {
					sbufData.append((data[i] & 0xff) + " ");
				}
				Log.write(sbufData.toString());
				*/
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		catch (Error e) {
			e.printStackTrace();
		}
	}
}
