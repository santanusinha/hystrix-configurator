/*
 * Copyright 2016 Phaneesh Nagaraja <phaneesh.n@gmail.com>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.hystrix.configutator;

import com.hystrix.configurator.config.HystrixCommandConfig;
import com.hystrix.configurator.config.HystrixConfig;
import com.hystrix.configurator.config.HystrixDefaultConfig;
import com.hystrix.configurator.core.HystrixConfigurationFactory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.ExecutionException;


/**
 * @author phaneesh
 */
public class HystrixConfigurationFactorySingleCommandTest {

    @Before
    public void setup() {
        HystrixConfigurationFactory.init(
        HystrixConfig.builder()
                .defaultConfig(new HystrixDefaultConfig())
                .commands(Collections.singletonList(HystrixCommandConfig.builder().name("test").build()))
                .build());
    }

    @Test
    public void testCommand() throws ExecutionException, InterruptedException {
        SimpleTestCommand command = new SimpleTestCommand();
        String result = command.queue().get();
        Assert.assertEquals("Simple Test", result);
    }

    public static class SimpleTestCommand extends HystrixCommand<String> {

        public SimpleTestCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("test"));
        }

        @Override
        protected String run() throws Exception {
            return "Simple Test";
        }
    }
}
