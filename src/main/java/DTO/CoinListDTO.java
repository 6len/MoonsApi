package DTO;

import java.util.List;

public class CoinListDTO {
    public List<CoinDTO> coins;

    public CoinListDTO(List<CoinDTO> coins) {
        this.coins = coins;
    }
}
