package xhs.com.activity.fragment.outlayer.loginlayer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xhs.com.activity.R;


/**
 * 登录
 */
public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_login, null);
        TextView tv_down = (TextView)view.findViewById(R.id.tv_down);
        tv_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRankMe();
            }
        });
        return view;
    }

    /**
     * 下载正式版小红书
     */
    private void gotoRankMe() {
        try {
            Uri uri = Uri.parse("market://details?id=com.xingin.xhs");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }
}
