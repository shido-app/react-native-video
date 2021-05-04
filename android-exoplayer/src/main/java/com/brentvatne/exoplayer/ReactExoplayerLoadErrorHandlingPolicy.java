package com.brentvatne.exoplayer;

import java.io.IOException;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.C;

public final class ReactExoplayerLoadErrorHandlingPolicy extends DefaultLoadErrorHandlingPolicy {
  private int minLoadRetryCount = Integer.MAX_VALUE;

  public ReactExoplayerLoadErrorHandlingPolicy(int minLoadRetryCount) {
    super(minLoadRetryCount);

    this.minLoadRetryCount = minLoadRetryCount;
  }

  @Override
  public long getRetryDelayMsFor(int dataType, long loadDurationMs, IOException exception, int errorCount) {
    // Capture the error we get when there is no network connectivity and keep retrying it
    if (exception.type == ExoPlaybackException.TYPE_SOURCE) {
      return 5000; // Retry every 5 seconds.
    } else {
      return C.TIME_UNSET; // Do not retry and immediately return the error
    }
  }

  @Override
  public int getMinimumLoadableRetryCount(int dataType) {
    return this.minLoadRetryCount;
  }
}