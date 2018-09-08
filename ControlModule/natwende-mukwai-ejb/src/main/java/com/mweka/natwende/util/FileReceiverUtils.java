package com.mweka.natwende.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;


public class FileReceiverUtils {

	public byte[] getBytesFromFile(File f) {
		FileInputStream fin = null;
		FileChannel ch = null;
		byte[] bytes;
		try {
			fin = new FileInputStream(f);
			ch = fin.getChannel();
			int size = (int) ch.size();
			MappedByteBuffer buf = ch.map(MapMode.READ_ONLY, 0, size);
			bytes = new byte[size];
			buf.get(bytes);
			return bytes;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
				if (ch != null) {
					ch.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
