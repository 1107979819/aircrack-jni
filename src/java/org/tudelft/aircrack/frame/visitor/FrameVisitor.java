package org.tudelft.aircrack.frame.visitor;

import org.tudelft.aircrack.frame.Frame;
import org.tudelft.aircrack.frame.control.AckFrame;
import org.tudelft.aircrack.frame.control.BlockAckFrame;
import org.tudelft.aircrack.frame.control.BlockAckOrRequestFrame;
import org.tudelft.aircrack.frame.control.BlockAckRequestFrame;
import org.tudelft.aircrack.frame.control.CfEndCfAckFrame;
import org.tudelft.aircrack.frame.control.CfEndFrame;
import org.tudelft.aircrack.frame.control.ControlFrame;
import org.tudelft.aircrack.frame.control.CtsFrame;
import org.tudelft.aircrack.frame.control.PsPollFrame;
import org.tudelft.aircrack.frame.control.RaFrame;
import org.tudelft.aircrack.frame.control.RaTaFrame;
import org.tudelft.aircrack.frame.control.RtsFrame;
import org.tudelft.aircrack.frame.data.DataFrame;
import org.tudelft.aircrack.frame.management.Beacon;
import org.tudelft.aircrack.frame.management.BeaconOrProbeResponse;
import org.tudelft.aircrack.frame.management.ManagementFrame;
import org.tudelft.aircrack.frame.management.ProbeRequest;
import org.tudelft.aircrack.frame.management.ProbeResponse;

public interface FrameVisitor
{
	
	public void visit(Frame frame);

	public void visit(ControlFrame frame);
	public void visit(DataFrame frame);
	public void visit(ManagementFrame frame);

	public void visit(RaFrame frame);

	public void visit(AckFrame frame);
	public void visit(CtsFrame frame);
	public void visit(RaTaFrame frame);

	public void visit(BlockAckOrRequestFrame frame);
	public void visit(CfEndFrame frame);
	public void visit(PsPollFrame frame);
	public void visit(RtsFrame frame);

	public void visit(BlockAckFrame frame);
	public void visit(BlockAckRequestFrame frame);
	
	public void visit(CfEndCfAckFrame frame);
	
	public void visit(BeaconOrProbeResponse frame);
	public void visit(ProbeRequest frame);
	public void visit(Beacon frame);
	public void visit(ProbeResponse frame);
	
}
