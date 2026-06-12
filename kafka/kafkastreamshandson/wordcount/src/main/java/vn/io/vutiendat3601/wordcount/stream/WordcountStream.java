package vn.io.vutiendat3601.wordcount.stream;

import java.util.Arrays;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

@Slf4j
public class WordcountStream {
  private static final Properties props = new Properties();

  static {
    var serdes = "org.apache.kafka.common.serialization.Serdes$StringSerde";
    props.setProperty("application.id", "wordcount-application");
    props.setProperty("bootstrap.servers", "localhost:9092");
    props.setProperty("auto.offset.reset", "earliest");
    props.setProperty("default.key.serde", serdes);
    props.setProperty("default.value.serde", serdes);
  }

  public static void main(String[] args) {
    var streams = new KafkaStreams(createTopology(), props);
    streams.start();
    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
  }

  private static Topology createTopology() {
    var builder = new StreamsBuilder();
    var lines = builder.<String, String>stream("wordcount_input");
    var wordCounts =
        lines
            .mapValues(line -> line.toLowerCase())
            .flatMapValues(line -> Arrays.asList(line.split("\\W+")))
            .filter((_, word) -> word.length() > 0)
            .selectKey((_, word) -> word)
            .groupByKey()
            .count(Materialized.as("counts"));
    wordCounts.toStream().to("wordcount_output", Produced.with(Serdes.String(), Serdes.Long()));
    return builder.build();
  }
}
