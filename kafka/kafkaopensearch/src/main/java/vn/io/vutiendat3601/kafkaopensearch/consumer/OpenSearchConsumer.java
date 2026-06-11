package vn.io.vutiendat3601.kafkaopensearch.consumer;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpHost;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.common.xcontent.XContentType;

@Slf4j
public class OpenSearchConsumer {
  private static final String WIKIMEDIA_INDEX = "wikimedia";
  private static final URI openSearchUri = URI.create("http://localhost:9200");

  public static void main(String[] args) throws IOException {
    // Create OpenSearch client
    var openSearchClient = createOpenSearchClient();
    try (openSearchClient) {
      var getIndexReq = new GetIndexRequest(WIKIMEDIA_INDEX);
      if (openSearchClient.indices().exists(getIndexReq, RequestOptions.DEFAULT)) {
        log.info("The {} index has already been created.", WIKIMEDIA_INDEX);
      } else {
        var createIndexReq = new CreateIndexRequest(WIKIMEDIA_INDEX);
        openSearchClient.indices().create(createIndexReq, RequestOptions.DEFAULT);
        log.info("The wikimedia OpenSearch index has been created.");
      }
    }

    // Create Kafka client
    var kafkaConsumer = createKafkaConsumer();
    kafkaConsumer.subscribe("wikie");
    while (true) {
      var records = kafkaConsumer.poll(Duration.ofSeconds(1));
      var numOfRecords = records.count();
      log.info("### Received number of records: {}", numOfRecords);

      for (var record : records) {
        var indexReq = new IndexRequest(WIKIMEDIA_INDEX).source(record.value(), XContentType.JSON);
        openSearchClient.index(indexReq, RequestOptions.DEFAULT);
        log.info("Inserted document into OpenSearch");
      }
    }

    // main code logic

    // close things
  }

  private static RestHighLevelClient createOpenSearchClient() {
    var restClient =
        RestClient.builder(
            new HttpHost(
                openSearchUri.getScheme(), openSearchUri.getHost(), openSearchUri.getPort()));
    return new RestHighLevelClient(restClient);
  }

  private static KafkaConsumer<String, String> createKafkaConsumer() {
    var groupId = "kafkaopensearch";

    var props = new Properties();
    props.setProperty("bootstrap.servers", "localhost:9092");
    props.setProperty("key.deserializer", StringDeserializer.class.getName());
    props.setProperty("value.deserializer", StringDeserializer.class.getName());
    props.setProperty("group.id", groupId);
    props.setProperty("auto.offset.reset", "latest");
    props.setProperty("enable.auto.commit", "false");

    return new KafkaConsumer<>(props);
  }
}
