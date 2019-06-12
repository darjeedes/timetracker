import org.junit.Test;

import com.darjeedes.timetracker.DBGenerator;

public class DBGeneratorRunner {

    @Test
    public void run() {
        DBGenerator.createDatabase();
    }

}
