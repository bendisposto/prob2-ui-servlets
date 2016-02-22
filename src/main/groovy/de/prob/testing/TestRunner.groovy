package de.prob.testing

import groovy.io.FileType

import javax.script.ScriptEngine

import org.junit.runner.JUnitCore
import org.junit.runner.Result
import org.junit.runner.notification.RunListener

import com.google.inject.Inject
import com.google.inject.Singleton

import de.prob.scripting.ScriptEngineProvider

@Singleton
class TestRunner {
	def ScriptEngine executor
	def ProBTestListener listener

	@Inject
	def TestRunner(ProBTestListener listener) {
		this.executor = de.prob.Main.getInjector().getInstance(ScriptEngineProvider.class).get()
		this.listener = listener
	}

	def Result runTests(pathToDir) {
		def tests = []
		new File(pathToDir).eachFile(FileType.FILES, {
			if (it.getName().endsWith(".groovy")) {
				tests << it
			}
		})
		def classes = []
		def errors = [:]
		tests.each { File f ->
			def clazz = getTestClass(f)
			if(clazz instanceof Throwable) {
				errors[f.getName()] = clazz
			} else {
				classes << clazz.getClass()
			}
		}
		def result = doRun(classes as Class<?>[])
		errors.each { name, error ->
			listener.testRunError(name, error)
		}
		result
	}


	def Result doRun(classes) {
		JUnitCore jUnitCore = new JUnitCore();
		jUnitCore.addListener(listener);
		Result result = jUnitCore.run(classes);
		return result;
	}

	def getTestClass(final File test) {
		def clazz = ""
		try {
			clazz = executor.eval(test.getText())
		} catch (Throwable e) {
			return e
		}
		return clazz
	}
}
