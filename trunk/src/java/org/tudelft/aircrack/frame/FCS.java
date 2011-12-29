package org.tudelft.aircrack.frame;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.codehaus.preon.annotation.BoundList;

public class FCS {

	@BoundList(size="4")
	private byte[] crc;
	
//	public FCS(byte[] crc){
//		if (crc.length!=4) throw new IllegalArgumentException("FCS should be 4 bytes long");
//		
//		for (int i=0;i<crc.length;i++)
//			this.crc[i] = crc[i];
//	}
	
//	public byte[] getFCS(){
//		return crc;
//	}
	
	public static byte[] compute(byte[] data){
		
		Checksum checksum = new CRC32();
        checksum.update(data,0,data.length);
        
        return ByteBuffer.allocate(4).putInt((int)checksum.getValue()).array();
        
//        return new byte[] {
//                (byte)(value >>> 24),
//                (byte)(value >>> 16),
//                (byte)(value >>> 8),
//                (byte)value};
	}

	public byte[] getCRC() {
		return crc;
	}
	
}
