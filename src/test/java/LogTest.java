import com.infinitys.logger.Log;
import com.infinitys.logger.LogObj;
import org.junit.jupiter.api.Test;

public class LogTest {
    @Test
    public void TestLogger() {
        Log.init("logs");
        Log.i("Info", "some info");
        Log.e("Error", "some info");
        Log.d("Process", "some info");
        test("gwgew");

        Log.enableMemReporter();

        Log.ce("CriticalError", "some info");
    }

    public void test(String str){
        Log.trace(new LogObj(str){});
    }
}
