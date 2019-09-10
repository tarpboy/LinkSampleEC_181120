package com.heykyul.linksample;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableRow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import com.heykyul.linksample.MakePrintMessage;

public class MainActivity extends Activity {
	public TableRow tableRow06;
	public TableRow tableRow07;
	public TableRow tableRow09;

	public TableRow tableRow08;
	public TableRow tableRow10;

	public EditText tranno;
	public EditText amount;
	public EditText tax;
	public EditText tip;
	public EditText installment;
	public EditText appr_num;
	public EditText appr_date;
	public EditText barcode;
	public EditText dongleflag;
	public EditText tran_serialno;

	public String tran_type;
	public String result_code;

	public Button button1;

	public Button bt_scan;

	public RadioButton rb1,rb2,rb3;


	private Handler delayhandler;
	private MakePrintMessage printMessage;

	Intent i2;

	int cnt9977 = 0;

	ArrayAdapter<CharSequence>  adspin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		delayhandler = new Handler();
		Spinner spinner = (Spinner) findViewById(R.id.spinner01);
		
		tableRow06 = (TableRow)findViewById(R.id.TableRow06);
		tableRow07 = (TableRow)findViewById(R.id.TableRow07);
		tableRow09 = (TableRow)findViewById(R.id.TableRow09);

		tableRow08 = (TableRow)findViewById(R.id.TableRow08);
		tableRow10 = (TableRow)findViewById(R.id.TableRow10);

		tranno = (EditText)findViewById(R.id.editText0);
		amount = (EditText)findViewById(R.id.editText1);
		tax = (EditText)findViewById(R.id.editText2);
		tip = (EditText)findViewById(R.id.EditText01);
		installment = (EditText)findViewById(R.id.EditText02);
		appr_num = (EditText)findViewById(R.id.EditText03);
		appr_date = (EditText)findViewById(R.id.EditText04);

		dongleflag = (EditText)findViewById(R.id.et_dongleflag);
		barcode = (EditText)findViewById(R.id.et_barcode);
		tran_serialno= (EditText) findViewById(R.id.et_transerialno);

		button1 = (Button) findViewById(R.id.button1);
		bt_scan = (Button) findViewById(R.id.bt_scan);

		adspin = ArrayAdapter.createFromResource(this, R.array.selected, android.R.layout.simple_spinner_item);
		
		adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        	public void onItemSelected( AdapterView<?> parent, View view, int position, long id) {
        		if("D4".equals(adspin.getItem(position).toString())||"B4".equals(adspin.getItem(position).toString())){
					tableRow06.setVisibility(View.VISIBLE);
					tableRow07.setVisibility(View.VISIBLE);
					tableRow09.setVisibility(View.VISIBLE);
				}else {
					tableRow06.setVisibility(View.GONE);
					tableRow07.setVisibility(View.GONE);
					tableRow09.setVisibility(View.GONE);
				}
				tran_type = adspin.getItem(position).toString();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

        });


		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				setTranData(tran_type,"");
			}
		});


		tableRow08.setVisibility(View.GONE);
		tableRow10.setVisibility(View.GONE);

		rb1 = (RadioButton) findViewById(R.id.rb1);
		rb2 = (RadioButton) findViewById(R.id.rb2);
		rb3 = (RadioButton) findViewById(R.id.rb3);

		savebmp("background_kicc.png",R.drawable.background_kicc);
		savebmp("close_kicc.png",R.drawable.close_kicc);
		savebmp("card_kicc.png",R.drawable.card_kicc);

    }

	protected void onResume(){
		super.onResume();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data); 
		if(resultCode==RESULT_OK) {
			if(requestCode==1) {
				printInent(data);

				if("9977".equals(data.getStringExtra("RESULT_CODE")))
					cnt9977++;

                i2 = new Intent(MainActivity.this, ResultActivity.class);
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Set keys = extras.keySet();

                    for (String _key : extras.keySet()) {
                    	if(extras.get(_key) == null)
						{
							i2.putExtra(_key, "null");
						}else
                        	i2.putExtra(_key, extras.get(_key).toString());
                    }
                }
//				delayhandler.postDelayed(
//						new Runnable() {
//							// 1 초 후에 실행
//							@Override
//							public void run() {
//								setTranData(tran_type,"");
//							}
//						}, 5000);

                startActivity(i2);

//				}
			}else{


			}
		}
    }

	
	public void setTranData(String tran_types, String ADD_FIELD){
		
		ComponentName compName = new ComponentName("kr.co.kicc.easycarda","kr.co.kicc.easycarda.CallPopup");

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.putExtra("TRAN_NO", this.tranno.getText().toString());
		intent.putExtra("TRAN_TYPE", tran_types);
		if("M8".equals(tran_types)) {
			intent.putExtra("TERMINAL_TYPE", "CE");
			intent.putExtra("TEXT_DECLINE", "포인트 조회가 거절되었습니다");
		}
		else
			intent.putExtra("TERMINAL_TYPE", "40");
		intent.putExtra("TOTAL_AMOUNT",this.amount.getText().toString());
		intent.putExtra("TAX",this.tax.getText().toString());
		intent.putExtra("TAX_OPTION","A");
		intent.putExtra("TIP",this.tip.getText().toString());
		//intent.putExtra("TIP_OPTION","N");
		if("D4".equals(tran_types)||"B4".equals(tran_types)) {
			intent.putExtra("APPROVAL_NUM", this.appr_num.getText().toString());
			intent.putExtra("APPROVAL_DATE", this.appr_date.getText().toString());
			intent.putExtra("TRAN_SERIALNO", this.tran_serialno.getText().toString());
		}
		if("B1".equals(tran_types) || "B2".equals(tran_types) || "B3".equals(tran_types) || "B4".equals(tran_types)) {
			intent.putExtra("CASHAMOUNT", "00");
		}else {
			intent.putExtra("INSTALLMENT", this.installment.getText().toString());
		}
		if("PT".equals(tran_types)){
			String printmsg = printMessage.receiptPrint("160516120000", "IC신용구매", false, false, 1004, 91, 0, 0, "1234-56**-****-1234", "테스트점", "1234567890", "홍길동", "02-1234-5678", "1234567", "서울 테스트구 테스트동", "발급사", "00001111", "", "", "12345678", "매입사", "", "", true);
			try {
				intent.putExtra("PRINT_DATA", printmsg.getBytes("EUC-KR"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		intent.putExtra("ADD_FIELD",ADD_FIELD);
		intent.putExtra("TIMEOUT","30");

		intent.putExtra("TEXT_PROCESS","결제 진행중입니다");
		intent.putExtra("TEXT_COMPLETE", "결제가 완료되었습니다");
//		intent.putExtra("TEXT_FALLBACK", "카드의 마그네틱부분으로\n읽어주세요");
//
//		intent.putExtra("TEXT_MAIN_SIZE", 60);
//		intent.putExtra("IMG_BG_WIDTH", 1000);
//		intent.putExtra("IMG_CARD_WIDTH", 600);
//		intent.putExtra("IMG_CLOSE_WIDTH", 100);

//		intent.putExtra("FALLBACK_FLAG","N");


		if(rb2.isChecked()) {

			intent.putExtra("IMG_BG_PATH", "/sdcard/kicc/background_gray.png");
			intent.putExtra("TEXT_MAIN_SIZE", 25);
			intent.putExtra("TEXT_MAIN_COLOR", "#FFFFFF");
			intent.putExtra("TEXT_SUB1_SIZE", 15);
			intent.putExtra("TEXT_SUB1_COLOR", "#FFFF00");
			intent.putExtra("TEXT_SUB2_SIZE", 12);
			intent.putExtra("TEXT_SUB2_COLOR", "#00FFFF");
			intent.putExtra("TEXT_SUB3_SIZE", 20);
			intent.putExtra("TEXT_SUB3_COLOR", "#FF00FF");
		}

		if(rb3.isChecked()) {

			intent.putExtra("IMG_BG_PATH", "/sdcard/kicc/background_kicc.png");
			intent.putExtra("IMG_CARD_PATH", "/sdcard/kicc/card_kicc.png");
			intent.putExtra("IMG_CLOSE_PATH", "/sdcard/kicc/close_kicc.png");
			intent.putExtra("TEXT_MAIN_SIZE", 18);
			intent.putExtra("TEXT_MAIN_COLOR", "#303030");
			intent.putExtra("TEXT_SUB1_SIZE", 12);
			intent.putExtra("TEXT_SUB1_COLOR", "#ff752a");
			intent.putExtra("TEXT_SUB2_SIZE", 10);
			intent.putExtra("TEXT_SUB2_COLOR", "#ff752a");
			intent.putExtra("TEXT_SUB3_SIZE", 16);
			intent.putExtra("TEXT_SUB3_COLOR", "#909090");
		}

//		if(barcode.getText().toString().isEmpty()==false)
//		{
//			intent.putExtra("DONGLE_FLAG",this.dongleflag.getText().toString());
//			intent.putExtra("BARCODE",this.barcode.getText().toString());
//
//		}

		intent.setComponent(compName);
		printInent(intent);
		startActivityForResult(intent, 1);
	}

	public static void printInent(Intent i) {
		try {
			//Log.e("KTC","-------------------------------------------------------");
			//util.saveLog("-------------------------------------------------------");
			if (i != null) {
				Bundle extras = i.getExtras();
				if (extras != null) {
					Set keys = extras.keySet();

					for (String _key : extras.keySet()) {
						Log.e("KTC","key=" + _key + " : " + extras.get(_key));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	public void savebmp(String filename, int drawable_id)
	{
		Bitmap bm = BitmapFactory.decodeResource(getApplicationContext().getResources(), drawable_id);
		File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "kicc");

		boolean doSave = true;
		if (!dir.exists()) {
			doSave = dir.mkdirs();
		}

		if (doSave) {
			saveBitmapToFile(dir,filename,bm,Bitmap.CompressFormat.PNG,100);
		}
		else {
			Log.e("app","Couldn't create target directory.");
		}
	}

	public boolean saveBitmapToFile(File dir, String fileName, Bitmap bm,
							 Bitmap.CompressFormat format, int quality) {

		File imageFile = new File(dir,fileName);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(imageFile);

			bm.compress(format,quality,fos);

			fos.close();

			return true;
		}
		catch (IOException e) {
			Log.e("app",e.getMessage());
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return false;
	}
}
