package com.hcsk.rddnss.pub;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import static com.hcsk.rddnss.pub.pubclass.printerrormsg;

public class htmlpar {
	private static String ENCODE = "GBK";

	/**
	 * 复制文件
	 * 
	 * @param fromFile
	 * @param toFile   <br/>
	 *                 2016�?2�?9�? 下午3:31:50
	 * @throws IOException
	 */
	public void copyFile(File fromFile, File toFile) throws IOException {
		FileInputStream ins = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);
		byte[] b = new byte[1024];
		int n = 0;
		while ((n = ins.read(b)) != -1) {
			out.write(b, 0, n);
		}

		ins.close();
		out.close();
	}

	private static void message(String szMsg) {
		try {
			System.out.println(new String(szMsg.getBytes(ENCODE), System.getProperty("file.encoding")));
		} catch (Exception e) {
		}
	}


	public String readTxtFile(String filePath) {
		String readstr = "";
		try {
			// String encoding="gb2312";
			String encoding = "utf8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));// 考虑到编码格�?encoding
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					readstr = readstr + lineTxt;
				}
				read.close();
			} else {
				printerrormsg("找不到指定的文件");
			}
		} catch (Exception e) {
			printerrormsg("读取文件内容出错:"+e.getMessage());
		}
		return readstr;
	}

	public String readbybyte(String filePath) {
		String readstr = "";
		File file = new File(filePath);
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			Reader reader = null;
			try {

				// �?��读一个字�?
				reader = new InputStreamReader(new FileInputStream(file), "utf-8");
				int tempchar;
				while ((tempchar = reader.read()) != -1) {
					// 对于windows下，\r\n这两个字符在�?��时，表示�?��换行�?
					// 但如果这两个字符分开显示时，会换两次行�?
					// 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行�?
					if (((char) tempchar) != '\r') {
						// System.out.print((char) tempchar);
						readstr = readstr + (char) tempchar;
					}
				}
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			printerrormsg("找不到指定的文件");
		}
		return readstr;
	}

	public String readTxtFileautoencode(String filePath) {
		String readstr = "";
		String fileEncode = EncodingDetect.getJavaEncode(filePath);
		try {
			// String encoding="gb2312";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), fileEncode);// 考虑到编码格�?encoding
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					readstr = readstr + "\r" + lineTxt;
				}
				read.close();
			} else {
				printerrormsg("找不到指定的文件");
			}
		} catch (Exception e) {
			printerrormsg("读取文件内容出错:"+e.getMessage());
		}
		return readstr;
	}

	public String readTxtFilebyencode(String filePath,String fileEncode) {
		String readstr = "";
		//String fileEncode = EncodingDetect.getJavaEncode(filePath);
		try {
			// String encoding="gb2312";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), fileEncode);// 考虑到编码格�?encoding
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					readstr = (readstr.length()>0?readstr+"\n":"") + lineTxt;
				}
				read.close();
			} else {
				printerrormsg("找不到指定的文件");
			}
		} catch (Exception e) {
			printerrormsg("读取文件内容出错:"+e.getMessage());
		}
		return readstr;
	}

	public ArrayList readTxtFileincluden(String filePath,String fileEncode) {
		ArrayList<String> readstr = new ArrayList();
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), fileEncode);// 考虑到编码格�?encoding
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					readstr.add(lineTxt);
				}
				read.close();
			} else {
				printerrormsg("找不到指定的文件");
			}
		} catch (Exception e) {
			printerrormsg("读取文件内容出错:"+e.getMessage());
		}
		return readstr;
	}

	public boolean writeTxtFile(String content, String fileName) throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			File wf = new File(fileName);
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("GB2312"));
			o.close();
			// mm=new RandomAccessFile(fileName,"rw");
			// mm.writeBytes(content);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}
	
	public boolean writeTxtFileutf8(String content, String fileName){
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			File wf = new File(fileName);
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("UTF-8"));
			o.close();
			// mm=new RandomAccessFile(fileName,"rw");
			// mm.writeBytes(content);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (mm != null) {
				try {
					mm.close();
				}catch (Exception ee){
					printerrormsg("写入文件，解除占用出错："+ee.getMessage());
				}
			}
		}
		return flag;
	}

	/**
	 * 
	 * 使用文件通道的方式复制文�?
	 * 
	 * 
	 * 
	 * @param s
	 * 
	 *          源文�?
	 * 
	 * @param t
	 * 
	 *          复制到的新文�?
	 * 
	 */

	public void fileChannelCopy(File s, File t) {

		FileInputStream fi = null;

		FileOutputStream fo = null;

		FileChannel in = null;

		FileChannel out = null;

		try {

			fi = new FileInputStream(s);

			fo = new FileOutputStream(t);

			in = fi.getChannel();// 得到对应的文件�?�?

			out = fo.getChannel();// 得到对应的文件�?�?

			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				fi.close();

				in.close();

				fo.close();

				out.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

	public String openFile(String szFileName) {
		try {
			BufferedReader bis = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(szFileName)), ENCODE));
			String szContent = "";
			String szTemp;

			while ((szTemp = bis.readLine()) != null) {
				szContent += szTemp + "\n";
			}
			bis.close();
			return szContent;
		} catch (Exception e) {
			return "";
		}
	}

}
