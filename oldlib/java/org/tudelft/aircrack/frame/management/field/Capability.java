package org.tudelft.aircrack.frame.management.field;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.Order;

/**
 * 
 * The Capability Information field contains a number of subfields that are used to indicate requested or advertised optional capabilities.
 * 
 * As described in Section 7.3.1.4 of IEEE Std 802.11-2007 
 * 
 * @author Niels Brouwers
 *
 */
public class Capability
{
	
	@Bound
	@Order(1) public boolean ess;
	
	@Bound
	@Order(2) public boolean ibss;
	
	@Bound
	@Order(3) public boolean cfPollable;
	
	@Bound
	@Order(4) public boolean cfPollRequest;
	
	@Bound
	@Order(5) public boolean privacy;
	
	@Bound
	@Order(6) public boolean shortPreamble;
	
	@Bound
	@Order(7) public boolean pbcc;
	
	@Bound
	@Order(8) public boolean channelAgility;
	
	@Bound
	@Order(9) public boolean spectrumManagement;
	
	@Bound
	@Order(10) public boolean qos;
	
	@Bound
	@Order(11) public boolean shortSlotTime;
	
	@Bound
	@Order(12) public boolean apsd;
	
	@Bound
	@Order(13) public boolean reserved1;
	
	@Bound
	@Order(14) public boolean dsssOfdm;
	
	@Bound
	@Order(15) public boolean delayedBlockAck;
	
	@Bound
	@Order(16) public boolean immediateBlockAck;
	
	@Override
	public String toString()
	{
		String ret = "";
		
		if (ess) ret += "ess ";
		if (ibss) ret += "ibss ";
		if (cfPollable) ret += "cfPollable ";
		if (cfPollRequest) ret += "cfPollRequest ";
		if (privacy) ret += "privacy ";
		if (shortPreamble) ret += "shortPreamble ";
		if (pbcc) ret += "pbcc ";
		if (channelAgility) ret += "channelAgility ";
		if (spectrumManagement) ret += "spectrumManagement ";
		if (qos) ret += "qos ";
		if (shortSlotTime) ret += "shortSlotTime ";
		if (apsd) ret += "apsd ";
		if (reserved1) ret += "reserved1 ";
		if (dsssOfdm) ret += "dsssOfdm ";
		if (delayedBlockAck) ret += "delayedBlockAck ";
		if (immediateBlockAck) ret += "immediateBlockAck ";
		
		return ret.trim();
	}
	
}
