package com.quinn.xmpp.ui.drawer;

import static com.quinn.xmpp.RequestCodes.CAMERA_REQUEST;
import static com.quinn.xmpp.RequestCodes.GALLERY_REQUEST;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.packet.VCard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.quinn.xmpp.Intents.Builder;
import com.quinn.xmpp.R;
import com.quinn.xmpp.core.drawer.UploadAvatarTask;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.launch.NetWorkSettingActivity;
import com.quinn.xmpp.ui.widget.InputDialog;
import com.quinn.xmpp.ui.widget.InputDialog.InputDialogCallback;
import com.quinn.xmpp.ui.widget.RecycleItemClickListener;
import com.quinn.xmpp.ui.widget.RecycleItemLongClickListener;
import com.quinn.xmpp.ui.widget.SimpleDividerItemDecoration;
import com.quinn.xmpp.ui.widget.SpinnerDialog;
import com.quinn.xmpp.util.FileUtils;
import com.quinn.xmpp.util.ImageCompressUtils;
import com.quinn.xmpp.util.ImageFormatUtils;
import com.quinn.xmpp.util.ToastUtils;

public class UserInfoActivity extends BaseActivity implements
		RecycleItemClickListener, RecycleItemLongClickListener,
		InputDialogCallback {

	private final static int PROPERTY_AVATAR = 0;
	private final static int PROPERTY_NICKNAME = 1;

	@InjectView(R.id.toolbar)
	Toolbar toolbar;
	@InjectView(R.id.userinfo_recycle_view)
	RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private UserInfoAdapter adapter;
	private UserVCard userVCard;
	private int dividerHeight;
	private int dividerColor;
	private SpinnerDialog spinnerDialog;
	private InputDialog inputDialog;
	private String pathOfImageFromCamera;

	private byte[] newAvatarbytes;

	//
	private boolean dialogShown = false;

	private final static int GET_IMAGE_FROM_CAMERA = 0;
	private final static int GET_IMAGE_FROM_GALLERY = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		ButterKnife.inject(this);
		// init data
		dividerHeight = getResources().getDimensionPixelSize(
				R.dimen.recyclerView_small_divider);
		dividerColor = getResources().getColor(R.color.color_gray);
		userVCard = smack.getUserVCard();
		toolbar.setTitle("个人信息");
		setSupportActionBar(toolbar);
		// 以下三行代码使activity有向上返回的按钮
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		layoutManager = new LinearLayoutManager(this);
		adapter = new UserInfoAdapter(userVCard);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
				getApplicationContext(), dividerColor, dividerHeight));
		adapter.setOnItemClickListener(this);
		adapter.setOnItemLongClickListener(this);
		spinnerDialog = new SpinnerDialog(this, "uploading");
		inputDialog = new InputDialog(this);
		inputDialog.setCallback(this);
		pathOfImageFromCamera = FileUtils.generateImagePath(this);

	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		if (dialogShown == false)
			return;
		dialogShown = false;
		uploadAvatar(newAvatarbytes);

	}

	@Override
	public void onItemClick(View view, int position) {
		switch (position) {
		case PROPERTY_AVATAR:
			popupBitmapFromWhereDialog();
			break;
		case PROPERTY_NICKNAME:
			popupPropertySettingDialog(PROPERTY_NICKNAME);
		default:
			break;
		}
	}

	@Override
	public void onItemLongClick(View view, int position) {

	}

	public void popupPropertySettingDialog(int property) {
		switch (property) {
		case 1:
			inputDialog.setTitle("昵称");
			break;
		case 2:
			inputDialog.setTitle("email");
		default:
			break;
		}
		
		inputDialog.show(this.getSupportFragmentManager(), "tag");

	}

	public void popupBitmapFromWhereDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this,
				AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		builder.setItems(R.array.bitmapFromWhere,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							sendCameraIntent();
							break;
						case 1:
							sendGalleryIntent();
							break;
						default:
							break;
						}
					}

				});

		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void sendCameraIntent() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(pathOfImageFromCamera)));
		startActivityForResult(intent, CAMERA_REQUEST);
	}

	public void sendGalleryIntent() {
		Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
		openAlbumIntent.setType("image/*");
		startActivityForResult(openAlbumIntent, GALLERY_REQUEST);
	}

	private Uri uri;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		dialogShown = true;
		switch (requestCode) {
		case GALLERY_REQUEST:
			uri = data.getData();
			try {
				Bitmap bitmapFromGallery = MediaStore.Images.Media.getBitmap(
						getContentResolver(), uri);
				// 压缩算法后续再改善。
				bitmapFromGallery = Bitmap.createScaledBitmap(
						bitmapFromGallery, 100, 100, true);
				newAvatarbytes = ImageFormatUtils
						.Bitmap2Bytes(bitmapFromGallery);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		case CAMERA_REQUEST:
			Bitmap bitmapFromCamera = ImageCompressUtils
					.compressImageFromPath(pathOfImageFromCamera);
			int rotateDegree = ImageFormatUtils
					.readPictureDegree(pathOfImageFromCamera);
			bitmapFromCamera = ImageFormatUtils.rotateBitmap(bitmapFromCamera,
					rotateDegree);
			// bitmapFromCamera = Bitmap.createScaledBitmap(bitmapFromCamera,
			// 100, 100, true);
			newAvatarbytes = ImageFormatUtils.Bitmap2Bytes(bitmapFromCamera);
			break;
		}
	}

	public void uploadAvatar(final byte[] bytes) {
		spinnerDialog.show(this.getSupportFragmentManager(), "tag");
		new UploadAvatarTask(smack) {

			@Override
			protected void onPostExecute(Boolean result) {
				spinnerDialog.dismissAllowingStateLoss();
				if (result) {
					userVCard.setAvatarBytes(bytes);
					adapter.notifyDataSetChanged();
					ToastUtils.showMsg(UserInfoActivity.this, "success");
				} else
					ToastUtils.showMsg(UserInfoActivity.this, "failure");
			}

		}.execute(bytes);
	}

	public static Intent createIntent(UserVCard vcard) {
		Builder builder = new Builder("UserInfo.View").addVcard(vcard);
		return builder.toIntent();
	}

	@Override
	public void cancel() {
		inputDialog.dismissAllowingStateLoss();
	}


	@Override
	public void confirm(String string) {
		VCard vcard = new VCard();
		try {
			vcard.load(smack.getConnection());
			vcard.setAvatar(vcard.getAvatar());
			vcard.setNickName(string);
			vcard.save(smack.getConnection());
		} catch (XMPPException e1) {
			e1.printStackTrace();
		}

		userVCard.setNickname(string);
		
		adapter.notifyDataSetChanged();
	}
}
