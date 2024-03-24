package com.test.application.game;

import com.test.application.UseCase;
import com.test.domain.game.Game;
import com.test.ports.game.GameDatabase;
import com.test.ports.game.GameStorage;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CreateGameUseCase extends UseCase<CreateGameUseCase.In, CreateGameUseCase.Out> {

    private final GameDatabase gameDatabase;
    private final GameStorage gameStorage;

    public CreateGameUseCase(GameDatabase gameDatabase, GameStorage gameStorage) {
        this.gameDatabase = gameDatabase;
        this.gameStorage = gameStorage;
    }

    @Override
    public Out execute(In input) {
        if (this.gameDatabase.gameOfName(input.name()).isPresent()) {
            throw new IllegalArgumentException("Game with name " + input.name() + " already exists");
        }

        Game createdGame = save(input.name(), input.releaseDate(), input.price());

        return new Out(
                createdGame.getId().value(),
                createdGame.getName().value(),
                createdGame.getReleaseDate().value(),
                createdGame.getPrice().value());
    }

    @Transactional
    private Game save(String name, LocalDate releaseDate, BigDecimal price) {
        Game createdGame = this.gameDatabase.save(new Game(name, releaseDate, price));

        String buketName = "composter";
        String fileName = createdGame.getId().value() + ".txt";
        String content = createdGame.toJson();

        this.gameStorage.save(buketName, fileName, content);

        return createdGame;
    }

    public record In(String name, LocalDate releaseDate, BigDecimal price) {
    }

    public record Out(Long id, String name, LocalDate releaseDate, BigDecimal price) {
    }
}
