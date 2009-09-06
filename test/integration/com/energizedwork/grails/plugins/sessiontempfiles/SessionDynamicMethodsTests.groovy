package com.energizedwork.grails.plugins.sessiontempfiles

import grails.util.GrailsWebUtil
import org.springframework.web.context.request.RequestContextHolder

class SessionDynamicMethodsTests extends GroovyTestCase {

	static transactional = false

	def session

	void setUp() {
		super.setUp()
		GrailsWebUtil.bindMockWebRequest()
		session = RequestContextHolder.requestAttributes.session
	}

	void tearDown() {
		super.tearDown()
		assert SessionDynamicMethods.getTempDir(session, false).deleteDir()
	}

	void testTempDirIsAvailableFromSession() {
		def tmpdir = session.getTempDir()
		assertEquals "SessionTmp_$session.id", tmpdir.name
		assertEquals new File(System.properties."java.io.tmpdir"), tmpdir.parentFile
		assertTrue tmpdir.isDirectory()
	}

	void testCanCreateTempFilesFromSession() {
		def tmpfile = session.createTempFile("foo", ".bar")
		assertEquals session.getTempDir(), tmpfile.parentFile
		assertTrue tmpfile.isFile()
	}

}