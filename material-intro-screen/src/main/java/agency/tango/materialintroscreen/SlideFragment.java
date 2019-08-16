package agency.tango.materialintroscreen;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agency.tango.materialintroscreen.parallax.ParallaxFragment;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SlideFragment extends ParallaxFragment {
    private final static String BACKGROUND_COLOR = "background_color";
    private static final String BUTTONS_COLOR = "buttons_color";
    private static final String SELECTED_INDICATOR_COLOR = "selected_indicator_color";
    private static final String UNSELECTED_INDICATOR_COLOR = "unselected_indicator_color";
    private static final String TEXT_COLOR = "text_color";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String NEEDED_PERMISSIONS = "needed_permission";
    private static final String POSSIBLE_PERMISSIONS = "possible_permission";
    private static final String IMAGE = "image";
    private static final int PERMISSIONS_REQUEST_CODE = 15621;

    private int backgroundColor;
    private int buttonsColor;
    private int selectedIndicatorColor;
    private int unselectedIndicatorColor;
    private int textColor;
    private int image;
    private String title;
    private String description;
    private String[] neededPermissions;
    private String[] possiblePermissions;

    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView imageView;

    public static SlideFragment createInstance(SlideFragmentBuilder builder) {
        SlideFragment slideFragment = new SlideFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(BACKGROUND_COLOR, builder.backgroundColor);
        bundle.putInt(BUTTONS_COLOR, builder.buttonsColor);
        bundle.putInt(SELECTED_INDICATOR_COLOR, builder.selectedIndicatorColor);
        bundle.putInt(UNSELECTED_INDICATOR_COLOR, builder.unselectedIndicatorColor);
        bundle.putInt(TEXT_COLOR, builder.textColor);
        bundle.putInt(IMAGE, builder.image);
        bundle.putString(TITLE, builder.title);
        bundle.putString(DESCRIPTION, builder.description);
        bundle.putStringArray(NEEDED_PERMISSIONS, builder.neededPermissions);
        bundle.putStringArray(POSSIBLE_PERMISSIONS, builder.possiblePermissions);

        slideFragment.setArguments(bundle);
        return slideFragment;
    }

    public static boolean isNotNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide, container, false);
        titleTextView = view.findViewById(R.id.txt_title_slide);
        descriptionTextView = view.findViewById(R.id.txt_description_slide);
        imageView = view.findViewById(R.id.image_slide);
        initializeView();
        return view;
    }

    private void initializeView() {
        Bundle bundle = getArguments();
        if(null != bundle) {
            backgroundColor = bundle.getInt(BACKGROUND_COLOR);
            buttonsColor = bundle.getInt(BUTTONS_COLOR);
            selectedIndicatorColor = getColor(bundle, SELECTED_INDICATOR_COLOR, R.color.selected_indicator);
            unselectedIndicatorColor = getColor(bundle, UNSELECTED_INDICATOR_COLOR, R.color.unselected_indicator);
            textColor = getColor(bundle, TEXT_COLOR, R.color.black);
            image = bundle.getInt(IMAGE, 0);
            title = bundle.getString(TITLE);
            description = bundle.getString(DESCRIPTION);
            neededPermissions = bundle.getStringArray(NEEDED_PERMISSIONS);
            possiblePermissions = bundle.getStringArray(POSSIBLE_PERMISSIONS);
        }
        updateViewWithValues();
    }

    private int getColor(Bundle bundle, String name, @ColorRes int defaultColor) {
        int colorRes = bundle.getInt(name, 0);
        colorRes = colorRes > 0 ? colorRes : defaultColor;
        return ContextCompat.getColor(requireContext(), colorRes);
    }

    public int backgroundColor() {
        return backgroundColor;
    }

    public int buttonsColor() {
        return buttonsColor;
    }

    public int getSelectedIndicatorColor() {
        return selectedIndicatorColor;
    }

    public int getUnselectedIndicatorColor() {
        return unselectedIndicatorColor;
    }

    public boolean hasAnyPermissionsToGrant() {
        boolean hasPermissionToGrant = hasPermissionsToGrant(neededPermissions);
        if (!hasPermissionToGrant) {
            hasPermissionToGrant = hasPermissionsToGrant(possiblePermissions);
        }
        return hasPermissionToGrant;
    }

    public boolean hasNeededPermissionsToGrant() {
        return hasPermissionsToGrant(neededPermissions);
    }

    public boolean canMoveFurther() {
        return true;
    }

    public String cantMoveFurtherErrorMessage() {
        return getString(R.string.impassable_slide);
    }

    private void updateViewWithValues() {
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        titleTextView.setTextColor(textColor);
        descriptionTextView.setTextColor(textColor);

        if (image != 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), image));
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public void askForPermissions() {
        ArrayList<String> notGrantedPermissions = new ArrayList<>();

        if (neededPermissions != null) {
            for (String permission : neededPermissions) {
                if (isNotNullOrEmpty(permission)) {
                    if (ContextCompat.checkSelfPermission(requireContext(), permission)
                            != PackageManager.PERMISSION_GRANTED) {
                        notGrantedPermissions.add(permission);
                    }
                }
            }
        }
        if (possiblePermissions != null) {
            for (String permission : possiblePermissions) {
                if (isNotNullOrEmpty(permission)) {
                    if (ContextCompat.checkSelfPermission(requireContext(), permission)
                            != PackageManager.PERMISSION_GRANTED) {
                        notGrantedPermissions.add(permission);
                    }
                }
            }
        }

        String[] permissionsToGrant = removeEmptyAndNullStrings(notGrantedPermissions);
        ActivityCompat.requestPermissions(requireActivity(), permissionsToGrant, PERMISSIONS_REQUEST_CODE);
    }

    private boolean hasPermissionsToGrant(String[] permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (isNotNullOrEmpty(permission)) {
                    if (ContextCompat.checkSelfPermission(requireContext(), permission) !=
                            PackageManager.PERMISSION_GRANTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private String[] removeEmptyAndNullStrings(final ArrayList<String> permissions) {
        List<String> list = new ArrayList<>(permissions);
        list.removeAll(Collections.singleton(null));
        return list.toArray(new String[list.size()]);
    }
}
