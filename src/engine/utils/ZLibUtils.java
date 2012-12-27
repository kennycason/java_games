package engine.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZLibUtils {

	public static String CHAR_SET = "UTF-8";

	public static String CHAR_SET_UTF8 = "UTF-8";

	public static String CHAR_SET_ISO_8859_1 = "ISO-8859-1";

	private ZLibUtils() {

	}
	
	/**
	 * usese zlib
	 * 
	 * @param str
	 * @return
	 * @throws DataFormatException
	 * @throws IOException
	 */
	public static byte[] inflate(byte[] compressedData) {
		try {
			Inflater decompressor = new Inflater();
			decompressor.setInput(compressedData);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			while (!decompressor.finished()) {
				int count = decompressor.inflate(buf);
				bos.write(buf, 0, count);
			}
			decompressor.end();
			bos.close();
			return bos.toByteArray();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * deflate using zlib
	 * 
	 * @param str
	 * @return
	 * @throws DataFormatException
	 * @throws IOException
	 */
	public static byte[] deflate(byte[] uncompressedData) {
		try {
			Deflater compressor = new Deflater();
			compressor.setLevel(Deflater.BEST_COMPRESSION);
			compressor.setInput(uncompressedData);
			compressor.finish();
			ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressedData.length);
			byte[] buf = new byte[1024];
			while (!compressor.finished()) {
				int count = compressor.deflate(buf);
				bos.write(buf, 0, count);
			}
			bos.close();
			return bos.toByteArray();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
