package com.dbls.app;

import akka.actor.typed.ActorSystem;
import com.dbls.app.layer.supervisor.DBLSSupervisor;

public class Main {

    public static void main(String[] args) {
        ActorSystem.create(DBLSSupervisor.create(), "dbls-system");
    }

}
