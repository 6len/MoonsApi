import DTO.CoinDTO;
import DTO.CoinListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;
import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@NoArgsConstructor
public class MoonRequester {
    private String dbUsername = System.getenv("MONGO_USER");
    private String dbPassword = System.getenv("MONGO_PASSWORD");

    private OkHttpClient client = new OkHttpClient().newBuilder().build();
    private ObjectMapper objectMapper = new ObjectMapper();
    private MongoClient mongoClient = MongoClients.create("mongodb+srv://"+dbUsername+":"+dbPassword+"@waiterdev-mpywr.mongodb.net/test?retryWrites=true&w=majority");
    private MongoDatabase mongoDatabase = mongoClient.getDatabase("coins");
    private MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("coins");

    public CoinListDTO getCoinList() {
        List<CoinDTO> coinList = new ArrayList<>();
        for (Document doc : mongoCollection.find()) {
            coinList.add(new CoinDTO(doc.getString("coin")));
        }
        return new CoinListDTO(coinList);
    }

    public void addCoin(String coin) {
        Document newDocument = new Document("coin", coin);
        mongoCollection.insertOne(newDocument);
    }

    public void removeCoin(String coin) {
        mongoCollection.deleteOne(eq("coin", coin));
    }

}
