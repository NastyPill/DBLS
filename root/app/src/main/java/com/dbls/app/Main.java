package com.dbls.app;

import akka.actor.typed.ActorSystem;
import com.dbls.app.layer.supervisor.DBLSSupervisor;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ActorSystem.create(DBLSSupervisor.create(), "dbls-system");
    }

}
