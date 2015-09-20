import org.junit.Test;

import com.github.andyshao.system.CleanJavadocTask;
import com.github.andyshao.system.Task;


public class CleanJavadocTaskTest {

    @Test
    public void test(){
        Task task = new CleanJavadocTask();
        task.process(new String[]{"-cleanJavadoc", "Z:\\tmp\\com\\fusioncharts\\chart\\Color.java"});
    }
}
