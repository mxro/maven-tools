package de.mxro.maven.tools;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EclipseProject {
	
	public static String getName(final File eclipseProjectFolder) {
		try {
			final byte[] pomBytes = Files.readAllBytes(FileSystems.getDefault().getPath(eclipseProjectFolder.getAbsolutePath()+"/.project"));

			final String pom = new String(pomBytes, "UTF-8");
			
			final Pattern pattern = Pattern.compile("<name>([^<]*)</name>");
			final Matcher matcher = pattern.matcher(pom);
			matcher.find();
			return matcher.group(1);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
