package org.tudelft.aircrack.frame.data.field;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;

/**
 * QoS control field.
 * 
 * Section 7.1.3.5
 * 
 * The QoS Control field is a 16-bit field that identifies the TC or TS to which the frame belongs and various
 * other QoS-related information about the frame that varies by frame type and subtype. The QoS Control field
 * is present in all data frames in which the QoS subfield of the Subtype field is set to 1 (see 7.1.3.1.2). Each
 * QoS Control field comprises five subfields, as defined for the particular sender (HC or non-AP STA) and
 * frame type and subtype
 * 
 * @author Niels Brouwers
 *
 */
// TODO fix comments
// TODO add getters/setters for field4 (TXOP limit, AP PS Buffer State, TXOP duration requested, Queue Size)
public class QualityOfServiceControl
{
	@BoundNumber(size="4")
	private int tid;
	
	@Bound
	private boolean eosp;
	
	@BoundNumber(size="2")
	private int ackPolicy;

	@BoundNumber(size="9")
	private int field4;

	// TODO fix comments
	/**
	 * The TID subfield identifies the TC or TS to which the corresponding MSDU, or fragment thereof, in the
	 * Frame Body field belongs. The TID subfield also identifies the TC or TS of traffic for which a TXOP is
	 * being requested, through the setting of TXOP duration requested or queue size. The encoding of the TID
	 * subfield depends on the access policy (see 7.3.2.30)
	 * 
	 * @return
	 */
	public int getTid()
	{
		return tid;
	}
	
	// TODO fix comments
	/**
	 * The TID subfield identifies the TC or TS to which the corresponding MSDU, or fragment thereof, in the
	 * Frame Body field belongs. The TID subfield also identifies the TC or TS of traffic for which a TXOP is
	 * being requested, through the setting of TXOP duration requested or queue size. The encoding of the TID
	 * subfield depends on the access policy (see 7.3.2.30)
	 * 
	 */
	public void setTid(int tid)
	{
		this.tid = tid;
	}
	
	// TODO fix comments
	/**
	 * The Ack Policy subfield is 2 bits in length and identifies the acknowledgment policy that is followed upon
	 * the delivery of the MPDU.
	 *  
	 * @return
	 */
	public int getAckPolicy()
	{
		return ackPolicy;
	}
	
	// TODO fix comments
	/**
	 * The Ack Policy subfield is 2 bits in length and identifies the acknowledgment policy that is followed upon
	 * the delivery of the MPDU.
	 *  
	 * @return
	 */
	public void setAckPolicy(int ackPolicy)
	{
		this.ackPolicy = ackPolicy;
	}

	// TODO fix comments
	/**
	 * The EOSP subfield is 1 bit in length and is used by the HC to indicate the end of the current service period
	 * (SP). The HC sets the EOSP subfield to 1 in its transmission and retransmissions of the SP’s final frame to
	 * end a scheduled/unscheduled SP and sets it to 0 otherwise.
	 * 
	 * @param eosp
	 */
	public void setEosp(boolean eosp)
	{
		this.eosp = eosp;
	}
	
	// TODO fix comments
	/**
	 * The EOSP subfield is 1 bit in length and is used by the HC to indicate the end of the current service period
	 * (SP). The HC sets the EOSP subfield to 1 in its transmission and retransmissions of the SP’s final frame to
	 * end a scheduled/unscheduled SP and sets it to 0 otherwise.
	 * 
	 */
	public boolean getEosp()
	{
		return eosp;
	}

}
