package quinn.xmpp.activity.laucher;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.XMPP.R;

public class LearnMoreFragment_C extends Fragment {
//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.learnmore_a_fragment, container, false);

        return rootView;
    }
}