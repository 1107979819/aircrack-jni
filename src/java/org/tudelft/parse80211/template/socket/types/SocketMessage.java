package org.tudelft.parse80211.template.socket.types;

import org.tudelft.parse80211.annotations.Mapped;
import org.tudelft.parse80211.annotations.Template;
import org.tudelft.parse80211.annotations.U16;
import org.tudelft.parse80211.annotations.U32;
import org.tudelft.parse80211.annotations.U8;
import org.tudelft.parse80211.types.BufferBacked;

@Template
public class SocketMessage
{

	@U8(offset=0) int methodId;

	@U32(offset=1) int argument;
	
	@U16(offset=5) int payloadLength;
	
	@Mapped(offset=7) BufferBacked payload;

	@Mapped(offset=7) ReceiveInfo receiveInfo;

	@Mapped(offset=7+32) BufferBacked frame;

}
