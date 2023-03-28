// Generated by view binder compiler. Do not edit!
package fr.isen.bonnefond.androidsmartdevice.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import fr.isen.bonnefond.androidsmartdevice.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDeviceDetailsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView affichageTextView;

  @NonNull
  public final ImageView bulb1;

  @NonNull
  public final ImageView bulb2;

  @NonNull
  public final ImageView bulb3;

  @NonNull
  public final TextView deviceName;

  @NonNull
  public final Group group;

  @NonNull
  public final TextView nombreTextView1;

  @NonNull
  public final TextView nombreTextView2;

  private ActivityDeviceDetailsBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView affichageTextView, @NonNull ImageView bulb1, @NonNull ImageView bulb2,
      @NonNull ImageView bulb3, @NonNull TextView deviceName, @NonNull Group group,
      @NonNull TextView nombreTextView1, @NonNull TextView nombreTextView2) {
    this.rootView = rootView;
    this.affichageTextView = affichageTextView;
    this.bulb1 = bulb1;
    this.bulb2 = bulb2;
    this.bulb3 = bulb3;
    this.deviceName = deviceName;
    this.group = group;
    this.nombreTextView1 = nombreTextView1;
    this.nombreTextView2 = nombreTextView2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDeviceDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDeviceDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_device_details, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDeviceDetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.affichageTextView;
      TextView affichageTextView = ViewBindings.findChildViewById(rootView, id);
      if (affichageTextView == null) {
        break missingId;
      }

      id = R.id.bulb1;
      ImageView bulb1 = ViewBindings.findChildViewById(rootView, id);
      if (bulb1 == null) {
        break missingId;
      }

      id = R.id.bulb2;
      ImageView bulb2 = ViewBindings.findChildViewById(rootView, id);
      if (bulb2 == null) {
        break missingId;
      }

      id = R.id.bulb3;
      ImageView bulb3 = ViewBindings.findChildViewById(rootView, id);
      if (bulb3 == null) {
        break missingId;
      }

      id = R.id.deviceName;
      TextView deviceName = ViewBindings.findChildViewById(rootView, id);
      if (deviceName == null) {
        break missingId;
      }

      id = R.id.group;
      Group group = ViewBindings.findChildViewById(rootView, id);
      if (group == null) {
        break missingId;
      }

      id = R.id.nombreTextView1;
      TextView nombreTextView1 = ViewBindings.findChildViewById(rootView, id);
      if (nombreTextView1 == null) {
        break missingId;
      }

      id = R.id.nombreTextView2;
      TextView nombreTextView2 = ViewBindings.findChildViewById(rootView, id);
      if (nombreTextView2 == null) {
        break missingId;
      }

      return new ActivityDeviceDetailsBinding((ConstraintLayout) rootView, affichageTextView, bulb1,
          bulb2, bulb3, deviceName, group, nombreTextView1, nombreTextView2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
