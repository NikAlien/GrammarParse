import org.example.Grammar;
import org.example.ParserRecursiveDescendent;
import org.junit.Test;

public class RecursiveDescendentParserTests {

    private Grammar gr = new Grammar("src/main/resources/g1.txt");
    private ParserRecursiveDescendent parser = new ParserRecursiveDescendent(gr);

    @Test
    public void test() {
        // like this
    }
}
