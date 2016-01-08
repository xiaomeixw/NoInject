package sabria.noinject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sabria.noinject.InjectView;
import com.sabria.noinject.LittleKnife;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tvMy1)
    TextView textView;

    @InjectView(R.id.tvMy2)
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LittleKnife.inject(this);

        textView.setText("SUCCESS");
        textView1.setText("SUCCESS 1");
    }


}
