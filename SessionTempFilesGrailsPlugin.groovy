import javax.servlet.http.HttpSession
import com.energizedwork.grails.plugins.sessiontempfiles.SessionDynamicMethods
import com.energizedwork.grails.plugins.sessiontempfiles.SessionListenerImpl

class SessionTempFilesGrailsPlugin {
	// the plugin version
	def version = "1.0"
	// the version or versions of Grails the plugin is designed for
	def grailsVersion = "1.1 > *"
	// the other plugins this plugin depends on
	def dependsOn = [controllers: "1.1 > *"]
	// resources that are excluded from plugin packaging
	def pluginExcludes = [
		"grails-app/views/error.gsp",
		"grails-app/views/index.gsp"
	]

	def author = "Rob Fletcher"
	def authorEmail = "rob@energizedwork.com"
	def title = "Session Temp Files"
	def description = '''\\
Provides a mechanism for creating and accessing temporary files bound to the HTTP
session that are destroyed when the session is destroyed.
'''

	// URL to the plugin's documentation
	def documentation = "http://grails.org/plugin/session-temp-files"

	def doWithWebDescriptor = {xml ->
		def listenerElements = xml."listener"[0]
		listenerElements + {
			"listener" {
				"display-name"("Temp Directory Cleanup")
				"listener-class"(SessionListenerImpl.name)
			}
		}
	}

	def doWithSpring = { }

	def doWithDynamicMethods = {ctx ->
		HttpSession.metaClass.getTempDir = {->
			SessionDynamicMethods.getTempDir(delegate)
		}
		HttpSession.metaClass.createTempFile = {String prefix, String suffix ->
			SessionDynamicMethods.createTempFile(delegate, prefix, suffix)
		}
	}

	def doWithApplicationContext = {applicationContext -> }

	def onChange = {event -> }

	def onConfigChange = {event -> }
}
