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
	@Order(1) boolean ess;
	
	@Bound
	@Order(2) boolean ibss;
	
	@Bound
	@Order(3) boolean cfPollable;
	
	@Bound
	@Order(4) boolean cfPollRequest;
	
	@Bound
	@Order(5) boolean privacy;
	
	@Bound
	@Order(6) boolean shortPreamble;
	
	@Bound
	@Order(7) boolean pbcc;
	
	@Bound
	@Order(8) boolean channelAgility;
	
	@Bound
	@Order(9) boolean spectrumManagement;
	
	@Bound
	@Order(10) boolean qos;
	
	@Bound
	@Order(11) boolean shortSlotTime;
	
	@Bound
	@Order(12) boolean apsd;
	
	@Bound
	@Order(13) boolean reserved1;
	
	@Bound
	@Order(14) boolean dsssOfdm;
	
	@Bound
	@Order(15) boolean delayedBlockAck;
	
	@Bound
	@Order(16) boolean immediateBlockAck;
	
}
