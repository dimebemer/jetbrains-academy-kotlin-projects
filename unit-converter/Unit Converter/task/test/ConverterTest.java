import converter.MainKt;
import org.hyperskill.hstest.stage.StageTest;


abstract public class ConverterTest<T> extends StageTest<T> {
    public ConverterTest() {
        super(MainKt.class);
    }
}
