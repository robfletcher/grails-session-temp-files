package com.energizedwork.grails.plugins.sessiontempfiles

import javax.servlet.http.HttpSession
import org.slf4j.LoggerFactory

class SessionDynamicMethods {

	private static final LOG = LoggerFactory.getLogger(SessionDynamicMethods)

	static File getTempDir(HttpSession session, boolean create = true) {
		def tmpDir = new File(System.properties."java.io.tmpdir", "SessionTmp_$session.id")
		if (create && !tmpDir.isDirectory()) {
			LOG.debug "Creating temp directory $tmpDir"
			tmpDir.mkdirs()
		}
		return tmpDir
	}

	static File createTempFile(HttpSession session, String prefix, String suffix) {
		def tmpDir = getTempDir(session)
		File.createTempFile(prefix, suffix, tmpDir)
	}

	private SessionDynamicMethods() {}

}