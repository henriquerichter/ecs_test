package com.test.application.usecases.game;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.test.application.services.game.GameService;
import com.test.application.usecases.UseCase;

@Component
public class GetGamesByPriceUseCase extends UseCase<GetGamesByPriceUseCase.In, List<GetGamesByPriceUseCase.Out>> {

  private final GameService gameService;

  public GetGamesByPriceUseCase(GameService gameService) {
    this.gameService = gameService;
  }

  @Override
  public List<Out> execute(In input) {
    return gameService.gamesOfPrice(input.price())
        .stream()
        .map(game -> new Out(
            game.getId().value(),
            game.getName().value(),
            game.getReleaseDate().value(),
            game.getPrice().value()))
        .toList();
  }

  public record In(BigDecimal price) {
  }

  public record Out(Long id, String name, LocalDate releaseDate, BigDecimal price) {
  }
}