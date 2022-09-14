package acktsap.markdown;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.Renderer;
import org.commonmark.renderer.html.HtmlRenderer;

public class CommonMarkTest {
    private static final String text = """
        # Title1

        text1

        ## Title2

        text2
        """.trim();

    public static void main(String[] args) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(text);
        Renderer renderer = HtmlRenderer.builder()
            .build();
        String rendered = renderer.render(document);
        System.out.println(rendered);
    }
}
