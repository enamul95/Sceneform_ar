package era.com.sceneform_ar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity  {

    ArFragment arFragment;
    ModelRenderable lionRenderable;

    ImageView  lion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        lion = findViewById(R.id.lion);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        setModel();

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                //when user tab to palain , who will add model



                if (lionRenderable == null) {
                    return;
                }
                Log.e("Check Null", "");

                // Create the Anchor.
                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                // Create the transformable andy and add it to the anchor.
                TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(lionRenderable);
                transformableNode.select();

            }
        });


    }


    private void setModel() {

        ModelRenderable.builder()
                .setSource(this,R.raw.lion)
                .build().thenAccept(rendarable -> lionRenderable=rendarable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Unable to load Lion Model", Toast.LENGTH_LONG).show();
                            return null;
                        }
                );

    }


}
