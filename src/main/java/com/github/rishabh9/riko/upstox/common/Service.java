/*
 * MIT License
 *
 * Copyright (c) 2018 Rishabh Joshi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.rishabh9.riko.upstox.common;

import com.github.rishabh9.riko.upstox.common.models.ApiCredentials;
import com.github.rishabh9.riko.upstox.common.models.AuthHeaders;
import com.github.rishabh9.riko.upstox.login.models.AccessToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Parent class for every Service class. Holds common methods.
 */
public abstract class Service {

    private static final Logger log = LogManager.getLogger(Service.class);

    protected final UpstoxAuthService upstoxAuthService;

    /**
     * @param upstoxAuthService The service to retrieve authentication details
     */
    public Service(@Nonnull final UpstoxAuthService upstoxAuthService) {

        this.upstoxAuthService = Objects.requireNonNull(upstoxAuthService);
    }

    protected <T> T prepareServiceApi(@Nonnull final Class<T> type) {

        log.debug("Preparing service API: {}", type.getName());
        final AccessToken accessToken = upstoxAuthService.getAccessToken();
        final ApiCredentials credentials = upstoxAuthService.getApiCredentials();

        final String token = accessToken.getType() + " " + accessToken.getToken();
        return ServiceGenerator.getInstance()
                .createService(type, new AuthHeaders(token, credentials.getApiKey()));
    }
}
