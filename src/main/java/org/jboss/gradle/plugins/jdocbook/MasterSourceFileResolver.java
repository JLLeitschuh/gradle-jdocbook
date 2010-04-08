package org.jboss.gradle.plugins.jdocbook;

import java.io.File;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.jboss.jdocbook.util.XIncludeHelper;

/**
 * Delegate used to help cache master source file resolution
 *
 * @author Steve Ebersole
 */
public class MasterSourceFileResolver {
	private final JDocBookPlugin plugin;

	MasterSourceFileResolver(JDocBookPlugin plugin) {
		this.plugin = plugin;
	}

	private File mainMasterFile;

	/**
	 * Retrieve the {@link File} reference to the main master document.
	 *
	 * @return The main master document file.
	 */
	public File getMainMasterFile() {
		if ( mainMasterFile == null ) {
			mainMasterFile = new File(
					plugin.getDirectoryLayout().getMasterSourceDirectory(),
					plugin.getConfiguration().getMasterSourceDocumentName()
			);
		}
		return mainMasterFile;
	}

	private Set<File> masterFiles;

	/**
	 * Retrieve the collection of all master language files.
	 *
	 * @return All the master language files.
	 */
	public Set<File> getFiles() {
		if ( masterFiles == null ) {
			File mainMasterFile = getMainMasterFile();
			final Set<File> files = new TreeSet<File>();
			files.add( mainMasterFile );
			XIncludeHelper.findAllInclusionFiles( mainMasterFile, files );
			this.masterFiles = Collections.unmodifiableSet( files );
		}
		return masterFiles;
	}
}
