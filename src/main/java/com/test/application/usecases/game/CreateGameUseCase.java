package com.test.application.usecases.game;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.test.application.domain.game.Game;
import com.test.application.services.game.GameService;
import com.test.application.usecases.UseCase;

@Component
public class CreateGameUseCase extends UseCase<CreateGameUseCase.In, CreateGameUseCase.Out> {

  private final GameService gameService;

  public CreateGameUseCase(GameService gameService) {
    this.gameService = gameService;
  }

  @Override
  public Out execute(In input) {
    if (gameService.gameOfName(input.name()).isPresent()) {
      throw new IllegalArgumentException("Game with name " + input.name() + " already exists");
    }

    Game createdGame = gameService.create(new Game(input.name(), input.releaseDate(), input.price()));

    return new Out(
        createdGame.getId().value(),
        createdGame.getName().value(),
        createdGame.getReleaseDate().value(),
        createdGame.getPrice().value());
  }

  public record In(String name, LocalDate releaseDate, BigDecimal price) {
  }

  public record Out(Long id, String name, LocalDate releaseDate, BigDecimal price) {
  }
}