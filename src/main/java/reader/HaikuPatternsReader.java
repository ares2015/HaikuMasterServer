package reader;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oliver on 3/13/2017.
 */
public interface HaikuPatternsReader {

    List<String> read() throws IOException;

}
