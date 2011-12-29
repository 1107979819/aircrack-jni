package org.tudelft.aircrack.frame.management.field;

import org.codehaus.preon.annotation.Bound;

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
	boolean ess;
	
	@Bound
	boolean ibss;
	
	@Bound
	boolean cfPollable;
	
	@Bound
	boolean cfPollRequest;
	
	@Bound
	boolean privacy;
	
	@Bound
	boolean shortPreamble;
	
	@Bound
	boolean pbcc;
	
	@Bound
	boolean channelAgility;
	
	@Bound
	boolean spectrumManagement;
	
	@Bound
	boolean qos;
	
	@Bound
	boolean shortSlotTime;
	
	@Bound
	boolean apsd;
	
	@Bound
	boolean reserved1;
	
	@Bound
	boolean dsssOfdm;
	
	@Bound
	boolean delayedBlockAck;
	
	@Bound
	boolean immediateBlockAck;
	
}
