/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.cli.command;

import java.util.List;

import joptsimple.OptionSet;

import org.springframework.boot.cli.Command;
import org.springframework.boot.cli.compiler.GroovyCompiler;
import org.springframework.boot.cli.compiler.GroovyCompilerConfiguration;
import org.springframework.boot.cli.compiler.GroovyCompilerConfigurationAdapter;
import org.springframework.boot.cli.compiler.RepositoryConfigurationFactory;
import org.springframework.boot.cli.compiler.grape.RepositoryConfiguration;

/**
 * {@link Command} to grab the dependencies of one or more Groovy scripts
 * 
 * @author Andy Wilkinson
 */
public class GrabCommand extends OptionParsingCommand {

	public GrabCommand() {
		super("grab", "Download a spring groovy script's dependencies to ./repository",
				new GrabOptionHandler());
	}

	private static final class GrabOptionHandler extends CompilerOptionHandler {

		@Override
		protected void run(OptionSet options) throws Exception {
			SourceOptions fileOptions = new SourceOptions(options);

			List<RepositoryConfiguration> repositoryConfiguration = RepositoryConfigurationFactory
					.createDefaultRepositoryConfiguration();

			GroovyCompilerConfiguration configuration = new GroovyCompilerConfigurationAdapter(
					options, this, repositoryConfiguration);

			if (System.getProperty("grape.root") == null) {
				System.setProperty("grape.root", ".");
			}

			GroovyCompiler groovyCompiler = new GroovyCompiler(configuration);
			groovyCompiler.compile(fileOptions.getSourcesArray());
		}

	}

}
