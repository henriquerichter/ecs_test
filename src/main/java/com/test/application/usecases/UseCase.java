package com.test.application.usecases;

public abstract class UseCase<IN, OUT> {

  public abstract OUT execute(IN input);
}