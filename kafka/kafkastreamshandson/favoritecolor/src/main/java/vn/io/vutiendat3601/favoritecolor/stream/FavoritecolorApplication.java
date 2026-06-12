package vn.io.vutiendat3601.favoritecolor.stream;

import java.util.Properties;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

@Slf4j
public class FavoritecolorApplication {
  private static final String TOPIC_INPUT = "favoritecolor.input";
  private static final String TOPIC_OUTPUT = "favoritecolor.output";
  private static final String TOPIC_USER_LIKES_COLOR = "favoritecolor.user_likes_color";
  private static final Properties props = new Properties();
  private static final Set<String> COLORS = Set.of("read", "green", "blue");

  static {
    var serdes = "org.apache.kafka.common.serialization.Serdes$StringSerde";
    props.setProperty("application.id", "favoritecolor_application");
    props.setProperty("bootstrap.servers", "localhost:9092");
    props.setProperty("auto.offset.reset", "earliest");
    props.setProperty("default.key.serde", serdes);
    props.setProperty("default.value.serde", serdes);
  }

  public static void main(String[] args) {
    var topology = createTopology();
    log.info("topology={}", topology);
    var streams = new KafkaStreams(topology, props);
    streams.start();
    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
  }

  private static Topology createTopology() {
    var builder = new StreamsBuilder();
    var lineStream = builder.<String, String>stream(TOPIC_INPUT);
    lineStream
        .filter((_, value) -> value.contains(","))
        .selectKey((_, value) -> value.split(",")[0].toLowerCase())
        .mapValues(value -> value.split(",")[1].toLowerCase())
        .filter((user, color) -> COLORS.contains(color))
        .to(TOPIC_USER_LIKES_COLOR);

    var userLikeColorTable = builder.<String, String>table(TOPIC_USER_LIKES_COLOR);
    var colorLikesCountTable =
        userLikeColorTable
            .groupBy((user, color) -> new KeyValue<>(color, color))
            .count(
                Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("color_likes_count")
                    .withKeySerde(Serdes.String())
                    .withValueSerde(Serdes.Long()));
    colorLikesCountTable.toStream().to(TOPIC_OUTPUT, Produced.with(Serdes.String(), Serdes.Long()));
    return builder.build();
  }
}
