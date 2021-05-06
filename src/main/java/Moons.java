import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Filter;

import static spark.Spark.*;

public class Moons {

    public static void main(String[] args) {
        MoonRequester moonRequester = new MoonRequester();
        ObjectMapper objectMapper = new ObjectMapper();


        port(getHerokuAssignedPort()); // define spark port

        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });

        path("/coins", () -> {
            get("/getCoins", (request, response) -> {
                return objectMapper.writeValueAsString(moonRequester.getCoinList());
            });

            post("/addCoin/:coin", (request, response) -> {
                String coin = request.params(":coin");
                moonRequester.addCoin(coin);
                return "done";
            });
            
            post("/removeCoin/:coin", (request, response) -> {
                String coin = request.params(":coin");
                moonRequester.removeCoin(coin);
                return "done";
            });
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 1233;
    }
}