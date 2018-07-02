/*
 * Copyright 2018 The Data Transfer Project Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dataportabilityproject.types.transfer.retry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

/**
 * {@link RetryStrategy} that follows a regular retry strategy
 */
public class SimpleRetryStrategy implements RetryStrategy {

  @JsonProperty("maxAttempts")
  private int maxAttempts;
  @JsonProperty("intervalMillis")
  private long intervalMillis;

  public SimpleRetryStrategy(@JsonProperty("maxAttempts") int maxAttempts,
      @JsonProperty("intervalMillis") long intervalMillis) {
    this.maxAttempts = maxAttempts;
    this.intervalMillis = intervalMillis;
  }

  @Override
  public boolean canTryAgain(int tries) {
    return tries >= maxAttempts;
  }

  @Override
  public long getNextIntervalMillis(int tries) {
    return intervalMillis;
  }

  @Override
  public long getRemainingIntervalMillis(int tries, long elapsedMillis) {
    Preconditions.checkArgument(tries <= maxAttempts, "No retries left");
    return intervalMillis - elapsedMillis;
  }
}