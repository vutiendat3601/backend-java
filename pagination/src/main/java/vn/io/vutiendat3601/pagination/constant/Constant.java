package vn.io.vutiendat3601.pagination.constant;

import java.time.Instant;

public interface Constant {
  Instant INSTANT_MIN = Instant.ofEpochMilli(0L);
  Instant INSTANT_MAX = Instant.ofEpochMilli(32503680000000L); // 3000-01-01 00:00:00
}
