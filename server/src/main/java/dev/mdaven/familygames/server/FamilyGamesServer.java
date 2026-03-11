package dev.mdaven.familygames.server;

import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.HandlerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FamilyGamesServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(FamilyGamesServer.class);

    private Javalin app;

    public int start(int port) {
        if(app != null) throw new IllegalStateException("server already started");
        app = Javalin.create(config -> {
            if (getClass().getClassLoader().getResource("frontend") != null) {
                config.staticFiles.add("/frontend");
            } else {
                LOGGER.warn("frontend resource folder not found, not including static files");
            }

            config.routes.apiBuilder(() -> {
                ApiBuilder.path("/api", () -> {
                    ApiBuilder.before("/*", ctx -> {
                        if (ctx.method() != HandlerType.OPTIONS) {

                        }
                    });

                });
            });
            config.routes.exception(Exception.class, haltWithCode(500));
        })

//        .options("/*", )

//        .ws("/ws", )

//        .error(HttpStatus.NOT_FOUND, )
        .start(port);

        return app.port();
    }

    private static <E extends Exception> ExceptionHandler<E> haltWithCode(int statusCode) {
        return (e, ctx) -> {
            ctx.status(statusCode);
            if (e.getMessage() != null) {
                ctx.result(e.getMessage());
            } else {
                ctx.result("An unknown %d error occurred.".formatted(statusCode));
            }
        };
    }

    public void stop() {
        if(app == null) throw new IllegalStateException("server not started");
        app.stop();
        app = null;
    }
}
