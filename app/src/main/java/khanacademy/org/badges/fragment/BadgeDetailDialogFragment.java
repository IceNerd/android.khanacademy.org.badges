package khanacademy.org.badges.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import khanacademy.org.badges.R;
import khanacademy.org.badges.data.model.BadgeModel;

public class BadgeDetailDialogFragment extends DialogFragment {

    public BadgeModel mModel;
    public ImageLoader mImageLoader;

    public static BadgeDetailDialogFragment newInstance( BadgeModel model, ImageLoader imgLoader ) {
        BadgeDetailDialogFragment frag = new BadgeDetailDialogFragment();
        frag.mModel = model;
        frag.mImageLoader = imgLoader;
        return frag;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.dialog_badge, container, false);
        getDialog().setTitle(mModel.description);

        return view;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        ((TextView) view.findViewById(R.id.description)).setText(mModel.description);
        ((NetworkImageView) view.findViewById(R.id.thumbnail)).setImageUrl(mModel.icon_large, mImageLoader);
        ((TextView) view.findViewById(R.id.safe_extended_description)).setText(mModel.safe_extended_description);
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

}
