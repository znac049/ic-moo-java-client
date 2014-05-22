package uk.org.wookey.IC.Utils;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileFilter;

public class FileExtensionFilter implements FilenameFilter {
	public String fileExt = "";

	public FileExtensionFilter(String extension) {
		fileExt = extension;
	}

	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(fileExt);
	}
}
