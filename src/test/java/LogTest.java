import com.infinitys.logger.Log;
import org.junit.jupiter.api.Test;

public class LogTest {
    @Test
    public void TestLogger() {
        Log.init("logs");
        Log.i("Info", "some info");
        Log.e("Error", "some info");
        Log.d("Process", "some info");
        Log.trace(Thread.currentThread(), "Class", "Function", new String[]{"arg1", "testValue"});

        Log.enableMemReporter();

        Log.ce("CriticalError", "some info");
    }
}
