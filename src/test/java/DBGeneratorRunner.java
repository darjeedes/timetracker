import org.junit.Ignore;
import org.junit.Test;

import com.darjeedes.timetracker.DBGenerator;

public class DBGeneratorRunner {

    @Test
    @Ignore
    public void run() {
        DBGenerator.createDatabase();
    }

}
