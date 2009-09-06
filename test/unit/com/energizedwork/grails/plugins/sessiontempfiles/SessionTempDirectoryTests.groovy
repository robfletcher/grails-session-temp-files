package com.energizedwork.grails.plugins.sessiontempfiles

import com.energizedwork.grails.plugins.sessiontempfiles.SessionDynamicMethods
import com.energizedwork.grails.plugins.sessiontempfiles.SessionListenerImpl
import grails.test.GrailsUnitTestCase
import javax.servlet.http.HttpSessionEvent
import org.springframework.mock.web.MockHttpSession

class SessionTempDirectoryTests extends GrailsUnitTestCase {

	def tmpdir
	def session

	void setUp() {
		super.setUp()

		tmpdir = new File(System.properties."java.io.tmpdir")

		session = new MockHttpSession()
	}

	void tearDown() {
		super.tearDown()
		assert new File(tmpdir, "SessionTmp_$session.id").deleteDir()
	}

	void testCreateTempFileCreatesNewFileInSessionSubDir() {
		def tmpFile = SessionDynamicMethods.createTempFile(session, "foo", ".bar")
		assertTrue tmpFile.isFile()
		assertEquals "SessionTmp_$session.id", tmpFile.parentFile.name
		assertEquals tmpdir, tmpFile.parentFile.parentFile
	}

	void testGetTempDirDoesNotCreateIfPassedFalse() {
		def tmpDir = SessionDynamicMethods.getTempDir(session, false)
		assertEquals "SessionTmp_$session.id", tmpDir.name
		assertFalse tmpDir.exists()
	}

	void testGetTempDirCreatesDirectoryByDefault() {
		def tmpDir = SessionDynamicMethods.getTempDir(session)
		assertEquals "SessionTmp_$session.id", tmpDir.name
		assertTrue tmpDir.isDirectory()
	}

	void testTempDirIsDestroyedAtEndOfSession() {
		def tmpdir = SessionDynamicMethods.getTempDir(session)
		assertTrue tmpdir.isDirectory()
		new SessionListenerImpl().sessionDestroyed(new HttpSessionEvent(session))
		assertFalse tmpdir.exists()
	}

}