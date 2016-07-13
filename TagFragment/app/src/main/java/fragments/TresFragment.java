package fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.insta.andre.tagfragment.R;

/**
 * Created by andre on 12/7/2016.
 */
public class TresFragment extends Fragment {

    private FragmentActivity myContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tres, container, false);

        Button cambiar = (Button) view.findViewById(R.id.cambiar);

        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DosFragment dosFragment = new DosFragment();
                FragmentManager fragmentManager = myContext.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.tab2, dosFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
}
