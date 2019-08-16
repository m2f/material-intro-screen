package agency.tango.materialintroscreen;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SlideFragmentBuilder {
    int backgroundColor;
    int buttonsColor;
    int selectedIndicatorColor;
    int unselectedIndicatorColor;
    int textColor;
    String title;
    String description;
    String[] neededPermissions;
    String[] possiblePermissions;
    int image;

    public SlideFragmentBuilder backgroundColor(@ColorRes int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public SlideFragmentBuilder buttonsColor(@ColorRes int buttonsColor) {
        this.buttonsColor = buttonsColor;
        return this;
    }

    public SlideFragmentBuilder selectedIndicatorColor(@ColorRes int selectedIndicatorColor) {
        this.selectedIndicatorColor = selectedIndicatorColor;
        return this;
    }

    public SlideFragmentBuilder unselectedIndicatorColor(@ColorRes int unselectedIndicatorColor) {
        this.unselectedIndicatorColor = unselectedIndicatorColor;
        return this;
    }

    public SlideFragmentBuilder textColor(@ColorRes int textColor) {
        this.textColor = textColor;
        return this;
    }

    public SlideFragmentBuilder title(String title) {
        this.title = title;
        return this;
    }

    public SlideFragmentBuilder description(String description) {
        this.description = description;
        return this;
    }

    public SlideFragmentBuilder neededPermissions(String[] neededPermissions) {
        this.neededPermissions = neededPermissions;
        return this;
    }

    public SlideFragmentBuilder possiblePermissions(String[] possiblePermissions) {
        this.possiblePermissions = possiblePermissions;
        return this;
    }

    public SlideFragmentBuilder image(@DrawableRes int image) {
        this.image = image;
        return this;
    }

    public SlideFragment build() {
        return SlideFragment.createInstance(this);
    }
}
