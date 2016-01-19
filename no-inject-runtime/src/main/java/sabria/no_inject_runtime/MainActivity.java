package sabria.no_inject_runtime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import sabria.no_inject_runtime.view.InjectView;
import sabria.no_inject_runtime.view.ViewInject;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tvRunId)
    TextView tvRunId;

    public void setTvRunId(TextView tvRunId) {
        Log.i("MainActivity","-----setTvRunId------");
        this.tvRunId = tvRunId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInject.inject(this);
        tvRunId.setText("--------inject--------");
    }


}
