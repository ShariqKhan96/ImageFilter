package com.webxert.imagefilter;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.webxert.imagefilter.Interface.AddTextFragmentListener;
import com.webxert.imagefilter.Interface.BrushChangeListener;
import com.webxert.imagefilter.Interface.ColorPickListener;
import com.webxert.imagefilter.Interface.EditPictureListener;
import com.webxert.imagefilter.Interface.EditPressedListener;
import com.webxert.imagefilter.Interface.EmojiListener;
import com.webxert.imagefilter.Interface.FilterPressedListener;
import com.webxert.imagefilter.Interface.FiltersListFragmentListener;
import com.webxert.imagefilter.adapter.ColorAdapter;
import com.webxert.imagefilter.adapter.ViewPagerAdapter;
import com.webxert.imagefilter.fragment.AddTextFragment;
import com.webxert.imagefilter.fragment.BrushFragment;
import com.webxert.imagefilter.fragment.EmojiFragment;
import com.webxert.imagefilter.fragment.FiltersListFragment;
import com.webxert.imagefilter.fragment.ImageEditFragment;
import com.webxert.imagefilter.utils.BitmapUtils;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.io.IOException;
import java.util.List;

import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class MainActivity extends AppCompatActivity implements EditPictureListener,
        FiltersListFragmentListener,
        EditPressedListener,
        FilterPressedListener, BrushChangeListener,
        EmojiListener, AddTextFragmentListener {

    public static String pictureName = "flash.jpg";

    public CardView brushButton, addtextButton, addemojiButton, addImageButton;

    public static final int PERMISSION_PICK_IMAGE = 1000;
    public static final int PERMISSION_INSERT_IMAGE = 1001;
    private PhotoEditorView imageView;
    private PhotoEditor photoEditor;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CoordinatorLayout coordinatorLayout;
    private HorizontalScrollView horizontalScrollView;

    private RelativeLayout relativeLayout;

    Bitmap originalImage, filteredImage, finalImage;

    FiltersListFragment filtersListFragment;
    ImageEditFragment imageEditFragment;

    int brightnessFinal = 0;
    float saturationFinal = 1.0f;
    float constrantFinal = 1.0f;
    RelativeLayout MAINCONTENT;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brushButton = findViewById(R.id.brush_cardview);
        addemojiButton = findViewById(R.id.add_emoji_cardview);
        addImageButton = findViewById(R.id.image_cardview);
        addtextButton = findViewById(R.id.add_text_cardview);
        imageView = findViewById(R.id.image_preview);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.myviewpager);
        relativeLayout = findViewById(R.id.relativeLayout);
        MAINCONTENT = findViewById(R.id.main_content);

        photoEditor = new PhotoEditor.Builder(this, imageView)
                .setPinchTextScalable(true)
                .setDefaultEmojiTypeface(Typeface.createFromAsset(getAssets(), "emojione-android.ttf"))
                .build();

        imageView.getSource().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFromGallery();


            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addImageToPicture();
            }
        });
        brushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoEditor.setBrushDrawingMode(true);
                BrushFragment fragment = BrushFragment.getInstance();
                fragment.setBrushChangeListener(MainActivity.this);
                fragment.show(getSupportFragmentManager(), fragment.getTag());
            }
        });
        addemojiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmojiFragment fragment = EmojiFragment.getInstance();
                fragment.setListener(MainActivity.this);
                fragment.show(getSupportFragmentManager(), fragment.getTag());

            }
        });
        addtextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddTextFragment fragment = AddTextFragment.getInstance();
                fragment.setAddTextFragmentListener(MainActivity.this);
                fragment.show(getSupportFragmentManager(), fragment.getTag());
            }
        });


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filter App");
        horizontalScrollView = findViewById(R.id.horizontalScrollView);

//
//        loadImage();


        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals("FILTER")) {
//
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 400);
                    params.addRule(RelativeLayout.BELOW, R.id.image_preview);
                    viewPager.setLayoutParams(params);
                    horizontalScrollView.setVisibility(View.VISIBLE);
                } else

                {
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ABOVE, R.id.tabs);
                    params.addRule(RelativeLayout.BELOW, R.id.image_preview);
                    viewPager.setLayoutParams(params);
                    horizontalScrollView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void addImageToPicture() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, PERMISSION_INSERT_IMAGE);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                    }
                }).check();
    }


    private void loadImage() {

        originalImage = BitmapUtils.getBitmapFromAsset(this, pictureName, 300, 300);
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);

        imageView.getSource().setImageBitmap(finalImage);

    }

    private void setUpViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        filtersListFragment = new FiltersListFragment();
        filtersListFragment.setListFragmentListener(this);
        filtersListFragment.setFilterPressedListener(this);

        imageEditFragment = new ImageEditFragment();
        imageEditFragment.setEditListener(this);
        imageEditFragment.setEditPressedListener(this);

        adapter.addFragment(filtersListFragment, "FILTER");
        adapter.addFragment(imageEditFragment, "EDIT");

        viewPager.setAdapter(adapter);

    }

    @Override
    public void onBrightnessChanged(int brightness) {

        brightnessFinal = brightness;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightness));
        imageView.getSource().setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onConstrainedChanged(float constrained) {

        constrantFinal = constrained;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ContrastSubFilter(constrained));
        imageView.getSource().setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onSaturationChanged(float saturation) {
        saturationFinal = saturation;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new SaturationSubfilter(saturation));
        imageView.getSource().setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onEditStarted() {

    }

    @Override
    public void onEditCompleted() {

        Bitmap bitmap = filteredImage.copy(Bitmap.Config.ARGB_8888, true);

        Filter myfilter = new Filter();
        myfilter.addSubFilter(new ContrastSubFilter(constrantFinal));
        myfilter.addSubFilter(new BrightnessSubFilter(brightnessFinal));
        myfilter.addSubFilter(new SaturationSubfilter(saturationFinal));

        finalImage = myfilter.processFilter(bitmap);

    }

    @Override
    public void onFilterSelected(Filter filter) {

        resetControls();
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        imageView.getSource().setImageBitmap(filter.processFilter(filteredImage));
        finalImage = filteredImage.copy(Bitmap.Config.ARGB_8888, true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())

        {

            case R.id.action_save: {
                saveImage();
                break;

            }
            case R.id.action_pick: {
                openFromGallery();
                break;
            }


        }
        return true;
    }

    private void saveImage() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            photoEditor.saveAsBitmap(new OnSaveBitmap() {
                                @Override
                                public void onBitmapReady(Bitmap saveBitmap) {
                                    try {
                                        imageView.getSource().setImageBitmap(saveBitmap);
                                        final String path = BitmapUtils.insertImage(getContentResolver(),
                                                saveBitmap, System.currentTimeMillis() + "_profile.jpg", null);

                                        if (!TextUtils.isEmpty(path))

                                        {
                                            Snackbar snackbar = Snackbar.make(relativeLayout, "Image Saved To Gallery!", Snackbar.LENGTH_SHORT)
                                                    .setAction("Open", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            openImage(path);
                                                        }
                                                    });
                                            snackbar.show();
                                        } else {
                                            Snackbar snackbar = Snackbar.make(relativeLayout, "Unable to save image!", Snackbar.LENGTH_SHORT);
                                            snackbar.show();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Exception e) {

                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.cancelPermissionRequest();
                    }
                }).check();
    }

    private void openImage(String path) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(path), "image/*");
        startActivity(intent);


    }

    private void openFromGallery() {

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, PERMISSION_PICK_IMAGE);
                        } else {
                            Toast.makeText(MainActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();
    }

    private void resetControls() {
        if (imageEditFragment != null)
            imageEditFragment.restControls();

        constrantFinal = 1.0f;
        saturationFinal = 1.0f;
        brightnessFinal = 0;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PERMISSION_PICK_IMAGE) {
                MAINCONTENT.setVisibility(View.VISIBLE);
                Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this, data.getData(), 800, 800);
                originalImage = bitmap;
                finalImage = bitmap;
                filteredImage = bitmap;


                originalImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
                filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);

                imageView.getSource().setImageBitmap(filteredImage);
                bitmap.recycle();

                filtersListFragment.displayThumbnail(originalImage);

            } else if (requestCode == PERMISSION_INSERT_IMAGE) {
                Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this, data.getData(), 300, 300);
                photoEditor.addImage(bitmap);
            }

        }


    }

    @Override
    public void onEditPressed() {
        horizontalScrollView.setVisibility(View.GONE);
    }

    @Override
    public void onFilterPressed() {
        horizontalScrollView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBrushOpacityChanged(int opacity) {
        photoEditor.setOpacity(opacity);
    }

    @Override
    public void onBrushSizeChanged(int size) {
        photoEditor.setBrushSize(size);

    }

    @Override
    public void onBrushColorChanged(int color) {
        photoEditor.setBrushColor(color);
    }

    @Override
    public void onBrushStateChanged(boolean isEraser) {
        if (isEraser)
            photoEditor.brushEraser();
        else photoEditor.setBrushDrawingMode(true);

    }

    @Override
    public void onEmojiSelected(String emoji) {
        photoEditor.addEmoji(emoji);

    }

    @Override
    public void onAddTextButtonClick(String text, int color) {
        photoEditor.addText(text, color);
    }
}
