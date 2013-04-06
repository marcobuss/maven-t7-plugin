/**
 * Copyright (C) 2010-2012 Joerg Bellmann <joerg.bellmann@googlemail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.t7mp.configuration;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Joerg Bellmann
 *
 */
public class ChainedArtifactResolverTest {

    @Test(expected = ResolutionException.class)
    public void testChainedArtifactResolver() throws ResolutionException {
        ChainedArtifactResolver resolver = new ChainedArtifactResolver();
        resolver.addPluginArtifactResolver(new PluginArtifactResolver() {
            @Override
            public File resolveArtifact(String coordinates) throws ResolutionException {
                throw new ResolutionException("TEST");
            }
        });
        resolver.resolveArtifact("a:b:jar:1.0.0");
    }

    @Test
    public void testChainedArtifactResolverJunit() throws ResolutionException {
        ChainedArtifactResolver resolver = new ChainedArtifactResolver();
        resolver.addPluginArtifactResolver(new LocalMavenRepositoryArtifactResolver());
        File file = resolver.resolveArtifact("junit:junit:jar:4.8.2");
        Assert.assertNotNull(file);
        Assert.assertTrue(file.exists());
    }
}
