package cn.sdt.fmanager.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

/**
 * Created by SDT13411 on 2018/1/22.
 */

public abstract class BaseFragment extends Fragment {

    public interface HandleEventListener {
        // TODO: Update argument type and name
        void setHandleFragment(BaseFragment fragment);
    }

    private HandleEventListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HandleEventListener) {
            mListener = (HandleEventListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HandleEventListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public abstract boolean handleBackPressed();
}
