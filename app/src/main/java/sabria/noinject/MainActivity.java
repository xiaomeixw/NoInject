package sabria.noinject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.sabria.noinject.InjectView;
import com.sabria.noinject.NoKnife;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tvMy1)
    public TextView textView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        NoKnife.inject(this);

        Log.i("MainActivity","textView="+textView);

        textView.setText("SUCCESS");

    }


}
