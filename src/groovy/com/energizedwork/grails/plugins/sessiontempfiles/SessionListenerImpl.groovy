package com.energizedwork.grails.plugins.sessiontempfiles

import javax.servlet.http.HttpSessionListener
import javax.servlet.http.HttpSessionEvent
import org.slf4j.LoggerFactory

class SessionListenerImpl implements HttpSessionListener {

	private static final LOG = LoggerFactory.getLogger(SessionListenerImpl)

	void sessionCreated(HttpSessionEvent event) { }

	void sessionDestroyed(HttpSessionEvent event) {
		def tmpdir = SessionDynamicMethods.getTempDir(event.session, false)
		if (tmpdir.isDirectory()) {
			if (!tmpdir.deleteDir()) {
				LOG.error "Unable to delete directory $tmpdir.absolutePath"
			}
		}
	}

}