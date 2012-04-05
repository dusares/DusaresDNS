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

import java.util.ArrayList;

public class ResourceRecord {

	/* globals */

	private static final short TYPE_A          =  1; // a host address                               [RFC1035]
	private static final short TYPE_NS         =  2; // an authoritative name server                 [RFC1035]
	private static final short TYPE_MD         =  3; // a mail destination (OBSOLETE - use MX)       [RFC1035]
	private static final short TYPE_MF         =  4; // a mail forwarder (OBSOLETE - use MX)         [RFC1035]
	private static final short TYPE_CNAME      =  5; // the canonical name for an alias              [RFC1035]
	private static final short TYPE_SOA        =  6; // marks the start of a zone of authority       [RFC1035]
	private static final short TYPE_MB         =  7; // a mailbox domain name (EXPERIMENTAL)         [RFC1035]
	private static final short TYPE_MG         =  8; // a mail group member (EXPERIMENTAL)           [RFC1035]
	private static final short TYPE_MR         =  9; // a mail rename domain name (EXPERIMENTAL)     [RFC1035]
	private static final short TYPE_NULL       =  10; // a null RR (EXPERIMENTAL)                    [RFC1035]
	private static final short TYPE_WKS        =  11; // a well known service description            [RFC1035]
	private static final short TYPE_PTR        =  12; // a domain name pointer                       [RFC1035]
	private static final short TYPE_HINFO      =  13; // host information                            [RFC1035]
	private static final short TYPE_MINFO      =  14; // mailbox or mail list information            [RFC1035]
	private static final short TYPE_MX         =  15; // mail exchange                               [RFC1035]
	private static final short TYPE_TXT        =  16; // text strings                                [RFC1035]
	private static final short TYPE_RP         =  17; // for Responsible Person                      [RFC1183]
	private static final short TYPE_AFSDB      =  18; // for AFS Data Base location                  [RFC1183][RFC5864]
	private static final short TYPE_X25        =  19; // for X.25 PSDN address                       [RFC1183]
	private static final short TYPE_ISDN       =  20; // for ISDN address                            [RFC1183]
	private static final short TYPE_RT         =  21; // for Route Through                           [RFC1183]
	private static final short TYPE_NSAP       =  22; // for NSAP address, NSAP style A record       [RFC1706]
	private static final short TYPE_NSAP-PTR   =  23; // for domain name pointer, NSAP style         [RFC1348][RFC1637][RFC1706]
	private static final short TYPE_SIG        =  24; // for security signature                      [RFC4034][RFC3755][RFC2535][RFC2536][RFC2537][RFC2931][RFC3110][RFC3008]
	private static final short TYPE_KEY        =  25; // for security key                            [RFC4034][RFC3755][RFC2535][RFC2536][RFC2537][RFC2539][RFC3008][RFC3110]
	private static final short TYPE_PX         =  26; // X.400 mail mapping information              [RFC2163]
	private static final short TYPE_GPOS       =  27; // Geographical Position                       [RFC1712]
	private static final short TYPE_AAAA       =  28; // IP6 Address                                 [RFC3596]
	private static final short TYPE_LOC        =  29; // Location Information                        [RFC1876]
	private static final short TYPE_NXT        =  30; // Next Domain (OBSOLETE)                      [RFC3755][RFC2535]
	private static final short TYPE_EID        =  31; // Endpoint Identifier                         [Patton][Patton1995]
	private static final short TYPE_NIMLOC     =  32; // Nimrod Locator                              [Patton][Patton1995]
	private static final short TYPE_SRV        =  33; // Server Selection                            [RFC2782]
	private static final short TYPE_ATMA       =  34; // ATM Address                                 [ATMDOC]
	private static final short TYPE_NAPTR      =  35; // Naming Authority Pointer                    [RFC2915][RFC2168][RFC3403]
	private static final short TYPE_KX         =  36; // Key Exchanger                               [RFC2230]
	private static final short TYPE_CERT       =  37; // CERT                                        [RFC4398]
	private static final short TYPE_A6         =  38; // A6 (OBSOLETE - use AAAA)                    [RFC3226][RFC2874][RFC-jiang-a6-to-historic-00.txt]
	private static final short TYPE_DNAME      =  39; // DNAME                                       [RFC2672]
	private static final short TYPE_SINK       =  40; // SINK                                        [Eastlake][Eastlake2002]
	private static final short TYPE_OPT        =  41; // OPT                                         [RFC2671][RFC3225]
	private static final short TYPE_APL        =  42; // APL                                         [RFC3123]
	private static final short TYPE_DS         =  43; // Delegation Signer                           [RFC4034][RFC3658]
	private static final short TYPE_SSHFP      =  44; // SSH Key Fingerprint                         [RFC4255]
	private static final short TYPE_IPSECKEY   =  45; // IPSECKEY                                    [RFC4025]
	private static final short TYPE_RRSIG      =  46; // RRSIG                                       [RFC4034][RFC3755]
	private static final short TYPE_NSEC       =  47; // NSEC                                        [RFC4034][RFC3755]
	private static final short TYPE_DNSKEY     =  48; // DNSKEY                                      [RFC4034][RFC3755]
	private static final short TYPE_DHCID      =  49; // DHCID                                       [RFC4701]
	private static final short TYPE_NSEC3      =  50; // NSEC3                                       [RFC5155]
	private static final short TYPE_NSEC3PARAM =  51; // NSEC3PARAM                                  [RFC5155]
	//private static final short TYPE_Unassigned =  52-54
	private static final short TYPE_HIP        =  55; // Host Identity Protocol                      [RFC5205]
	private static final short TYPE_NINFO      =  56; // NINFO                                       [Reid]
	private static final short TYPE_RKEY       =  57; // RKEY                                        [Reid]
	private static final short TYPE_TALINK     =  58; // Trust Anchor LINK                           [Wijngaards]
	private static final short TYPE_CDS        =  59; // Child DS                                    [Barwood]
	//private static final short TYPE_Unassigned =  60-98
	private static final short TYPE_SPF        =  99; //                                             [RFC4408]
	private static final short TYPE_UINFO      =  100; //                                            [IANA-Reserved]
	private static final short TYPE_UID        =  101; //                                            [IANA-Reserved]
	private static final short TYPE_GID        =  102; //                                            [IANA-Reserved]
	private static final short TYPE_UNSPEC     =  103; //                                            [IANA-Reserved]
	//private static final short TYPE_Unassigned =  104-248
	private static final short TYPE_TKEY       =  249; // Transaction Key                            [RFC2930]
	private static final short TYPE_TSIG       =  250; // Transaction Signature                      [RFC2845]
	private static final short TYPE_IXFR       =  251; // incremental transfer                       [RFC1995]
	private static final short TYPE_AXFR       =  252; // transfer of an entire zone                 [RFC1035][RFC5936]
	private static final short TYPE_MAILB      =  253; // mailbox-related RRs (MB, MG or MR)         [RFC1035]
	private static final short TYPE_MAILA      =  254; // mail agent RRs (OBSOLETE - see MX)         [RFC1035]
	private static final short TYPE_*          =  255; // A request for all records                  [RFC1035]
	private static final short TYPE_URI        =  256; // URI                                        [Faltstrom]
	private static final short TYPE_CAA        =  257; // Certification Authority Authorization      [Hallam-Baker]
	//private static final short TYPE_Unassigned =  258-32767
	private static final short TYPE_TA         =  32768; //   DNSSEC Trust Authorities               [Weiler]           2005-12-13
	private static final short TYPE_DLV        =  32769; //   DNSSEC Lookaside Validation            [RFC4431]
	//private static final short TYPE_Unassigned =  32770-65279  
	//private static final short TYPE_Private use=  65280-65534
	private static final short TYPE_Reserved   =  65535; // 


	/* data */

	private MessageHeader _header;
	private ArrayList _questions;
	private ArrayList _answers;
	private ArrayList _authorities;
	private ArrayList _additionals;

	/* constructors */

	public ResourceRecord() {
	}

	/* getters / setters */

}
