module com.kunle.finanalysistooljavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.prefs;
    requires java.net.http;
    requires commons.math3;
    requires json.simple;

    exports com.kunle.finanalysistooljavafx;
    exports com.kunle.finanalysistooljavafx.Support;
    exports com.kunle.finanalysistooljavafx.Applications;

}