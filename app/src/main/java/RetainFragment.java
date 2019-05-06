import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class RetainFragment extends Fragment {

    private Object mObject;

    public RetainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }
}
