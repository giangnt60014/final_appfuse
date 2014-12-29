package com.giangnt.webapp.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class UploadDrive {

	public static void main(String[] args) throws IOException {
		URL website = new URL("https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xfa1/v/t1.0-9/10882338_484618718343509_4427203943746860088_n.jpg?oh=caa8ddc30be99c6b6ab2d04356b35433&oe=5541E36F&__gda__=1430479449_49fb99a08013ff8b117d10bad5cd8d6e");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream("abc.jpg");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}
}
