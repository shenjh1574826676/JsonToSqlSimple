package jsontosql;

import java.io.IOException;

import static jsontosql.JsonToSql.toSql;

public class TestToSql {
    public static void main(String args[]) throws IOException {
        toSql("t_layer_tz");
    }
}
